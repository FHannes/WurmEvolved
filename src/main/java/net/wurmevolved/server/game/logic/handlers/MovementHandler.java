package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.Position;
import net.wurmevolved.server.game.logic.observers.MovementObserver;
import net.wurmevolved.server.game.logic.observers.ObserverList;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.client.MovementPacket;
import net.wurmevolved.server.game.net.packets.server.Movement3DPacket;

public class MovementHandler extends LogicHandler {

    private Player player;

    private ObserverList<MovementObserver> observers = new ObserverList<>();

    private float xOffset = 0;
    private float yOffset = 0;
    private boolean zChanged = false;
    private boolean rotChanged = false;

    public MovementHandler(Player player) {
        this.player = player;
    }

    public ObserverList<MovementObserver> getObservers() {
        return observers;
    }

    private void update(Position pos) {
        xOffset += pos.getX() - player.getPos().getX();
        yOffset += pos.getY() - player.getPos().getY();
        zChanged = pos.getZ() != player.getPos().getZ();
        rotChanged = pos.getRot() != player.getPos().getRot();

        int tileXOffset = pos.getTileX() - player.getPos().getTileX();
        int tileYOffset = pos.getTileY() - player.getPos().getTileY();

        player.setPos(pos);

        if (tileXOffset != 0 || tileYOffset != 0) {
            observers.getAll().forEach(o -> o.onPlayerMovedTile(pos, tileXOffset, tileYOffset));
        }

        reply();
    }

    private void reply() {
        float sendXOffset = Math.round(xOffset * 40F) / 40F;
        float sendYOffset = Math.round(yOffset * 40F) / 40F;
        if (sendXOffset != 0 || sendYOffset != 0 || zChanged || rotChanged) {
            xOffset -= sendXOffset;
            yOffset -= sendYOffset;
            zChanged = false;
            rotChanged = false;
            Movement3DPacket packet = new Movement3DPacket(
                    player.getId(), sendXOffset, sendYOffset, player.getPos().getZ(), player.getPos().getRot());
            for (Player localPlayer : player.getLocal(Player.class)) {
                if (player.equals(localPlayer)) {
                    continue;
                }
                localPlayer.send(packet);
            }
        }
    }

    @Override
    public void join() {

    }

    @Override
    public void handle(AbstractPacket packet) {
        if (packet instanceof MovementPacket) {
            update(((MovementPacket) packet).getPos());
        }
    }

    @Override
    public void leave() {

    }

}
