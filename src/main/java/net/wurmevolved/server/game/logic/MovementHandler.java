package net.wurmevolved.server.game.logic;

import net.wurmevolved.common.constants.ChatColor;
import net.wurmevolved.common.constants.CreatureType;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Tile;
import net.wurmevolved.server.game.map.Chunk;
import net.wurmevolved.server.game.map.TerrainBuffer;
import net.wurmevolved.server.game.net.packets.client.MovementPacket;
import net.wurmevolved.server.game.net.packets.server.*;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovementHandler {

    private World world;
    private Player player;

    // Terrain data
    private Set<Chunk> chunks = new HashSet<>();
    private Point terrainPos;

    // Local data
    private Point localPos;

    // Movement data
    private float xOffset = 0;
    private float yOffset = 0;
    private boolean zChanged = false;
    private boolean rotChanged = false;

    public MovementHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

    public void updateTerrain() {
        List<Chunk> chunks = world.getTerrainBuffer().getChunksFromCoords(
                player.getPos().getTileX(), player.getPos().getTileY(), (short) (192 / Chunk.CHUNK_SIZE));
        for (Chunk chunk : chunks) {
            if (!this.chunks.contains(chunk)) {
                sendChunk(chunk);
            }
        }
        this.chunks = new HashSet<>(chunks);
    }

    public void sendChunk(Chunk chunk) {
        player.send(new TerrainPacket(chunk.toArray()));
    }

    public void updateDistantTerrain() {
        if (terrainPos == null || terrainPos.distance(player.getPos().getTileX(), player.getPos().getTileY()) > 128) {
            terrainPos = new Point(player.getPos().getTileX(), player.getPos().getTileY());
            short x1 = (short) (Math.max(0, player.getPos().getTileX() - 1024) / 16);
            short y1 = (short) (Math.max(0, player.getPos().getTileY() - 1024) / 16);
            short map_size = Chunk.CHUNK_SIZE * TerrainBuffer.CHUNK_COUNT;
            short x2 = (short) (Math.min(map_size - 1, player.getPos().getTileX() + 1024) / 16);
            short y2 = (short) (Math.min(map_size - 1, player.getPos().getTileY() + 1024) / 16);
            Tile[][] tiles = new Tile[y2 - y1 + 1][x2 - x1 + 1];
            for (short x = x1; x <= x2; x++) {
                for (short y = y1; y <= y2; y++) {
                    tiles[y - y1][x - x1] = world.getTerrainBuffer().getTile((short) (x * 16), (short) (y * 16));
                }
            }
            player.send(new DistantTerrainPacket(tiles));
        }
    }

    /**
     * Initializes the player's own local environment. This method is to be called after login. All further updates to
     * local will only be performed as needed.
     */
    public void initLocal() {
        for (Player localPlayer : world.getPlayers().getLocal(player.getPos())) {
            if (!player.equals(localPlayer)) {
                player.addLocal(localPlayer);
                player.send(new AddCreaturePacket(
                        localPlayer.getId(), localPlayer.getModel(), localPlayer.getPos(), localPlayer.getFullName(),
                        CreatureType.HUMAN, localPlayer.getKingdom(), localPlayer.getFaceStyle()));
            }
            player.send(new AddUserPacket(":Local", localPlayer.getUsername(), localPlayer.getId()));
        }
    }

    /**
     * Updates all other players if this player has moved in or out of their local. This will also send the movements of
     * the player to all other players in local.
     */
    public void updatePlayerLocals() {
        Set<Player> localPlayers = null;

        // Add or remove players from local
        if (localPos == null || terrainPos.distance(player.getPos().getTileX(), player.getPos().getTileY()) > 0) {
            localPlayers = world.getPlayers().getLocal(player.getPos());
            localPos = new Point(player.getPos().getTileX(), player.getPos().getTileY());
            AddCreaturePacket packetAdd = new AddCreaturePacket(
                    player.getId(), player.getModel(), player.getPos(), player.getFullName(), CreatureType.HUMAN,
                    player.getKingdom(), player.getFaceStyle());
            RemoveCreaturePacket packetRemove = new RemoveCreaturePacket(player.getId());
            AddUserPacket packetAddUser = new AddUserPacket(":Local", player.getUsername(), player.getId());
            RemoveUserPacket packetRemoveUser = new RemoveUserPacket(":Local", player.getUsername());
            for (Player worldPlayer : world.getPlayers().all()) {
                if (worldPlayer.equals(player)) {
                    continue;
                }
                if (localPlayers.contains(worldPlayer)) {
                    boolean hasPlayer = worldPlayer.hasLocal(player);
                    if (!hasPlayer) {
                        worldPlayer.addLocal(player);
                        worldPlayer.send(packetAdd);
                        worldPlayer.send(packetAddUser);
                        player.send(new AddCreaturePacket(
                                worldPlayer.getId(), worldPlayer.getModel(), worldPlayer.getPos(),
                                worldPlayer.getUsername(), CreatureType.HUMAN, worldPlayer.getKingdom(),
                                worldPlayer.getFaceStyle()));
                        player.send(new AddUserPacket(":Local", worldPlayer.getUsername(), worldPlayer.getId()));
                    }
                } else {
                    boolean hasPlayer = worldPlayer.hasLocal(player);
                    if (hasPlayer) {
                        worldPlayer.removeLocal(player);
                        worldPlayer.send(packetRemoveUser);
                        worldPlayer.send(packetRemove);
                        player.send(new RemoveCreaturePacket(worldPlayer.getId()));
                        player.send(new RemoveUserPacket(":Local", worldPlayer.getUsername()));
                    }
                }
            }
        }

        // Send movement to other clients
        float sendXOffset = Math.round(xOffset * 40F) / 40F;
        float sendYOffset = Math.round(yOffset * 40F) / 40F;
        if (sendXOffset != 0 || sendYOffset != 0 || zChanged || rotChanged) {
            xOffset -= sendXOffset;
            yOffset -= sendYOffset;
            zChanged = false;
            rotChanged = false;
            Movement3DPacket packet = new Movement3DPacket(
                    player.getId(), sendXOffset, sendYOffset, player.getPos().getZ(), player.getPos().getRot());
            for (Player localPlayer : (localPlayers != null ? localPlayers : world.getPlayers().getLocal(player.getPos()))) {
                if (player.equals(localPlayer)) {
                    continue;
                }
                localPlayer.send(packet);
            }
        }
    }

    public void leaveWorld() {
        world.getPlayers().remove(player);
        RemoveCreaturePacket packetRemove = new RemoveCreaturePacket(player.getId());
        for (Player worldPlayer : world.getPlayers().all()) {
            if (worldPlayer.hasLocal(player)) {
                worldPlayer.removeLocal(player);
                worldPlayer.send(packetRemove);
            }
        }
        RemoveUserPacket packetRemoveUser = new RemoveUserPacket(":Local", player.getUsername());
        ServerMessagePacket packetMessage = new ServerMessagePacket(
                ":Event", String.format("%s has left the world!", player.getUsername()), ChatColor.GREEN);
        for (Player localPlayer : world.getPlayers().getLocal(player.getPos())) {
            localPlayer.send(packetRemoveUser);
            localPlayer.send(packetMessage);
        }
        world.getPlayers().save(player);
    }

    public void update() {
        updateTerrain();
        updateDistantTerrain();
        updatePlayerLocals();
    }

    public void handle(MovementPacket packet) {
        xOffset += packet.getPos().getX() - player.getPos().getX();
        yOffset += packet.getPos().getY() - player.getPos().getY();
        zChanged = packet.getPos().getZ() != player.getPos().getZ();
        rotChanged = packet.getPos().getRot() != player.getPos().getRot();
        player.setPos(packet.getPos());
        update();
    }

}
