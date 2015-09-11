package net.wurmevolved.server.game.data;

import io.netty.channel.Channel;
import net.wurmevolved.common.constants.Kingdom;
import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.logic.GameEntity;
import net.wurmevolved.server.game.logic.observers.MovementObserver;
import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.server.RemoveCreaturePacket;
import net.wurmevolved.server.game.net.packets.server.RemoveObjectPacket;
import net.wurmevolved.server.game.net.packets.server.RemoveUserPacket;
import net.wurmevolved.server.game.util.PlayerHelper;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "players")
public class Player implements GameEntity, MovementObserver {

    @Id
    @Column(name = "player_id", nullable = false)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Enumerated
    @Column(name = "type", nullable = false)
    private PlayerType type;

    private Position pos;

    @Column(name = "male", nullable = false)
    private boolean male;

    private FaceStyle faceStyle;

    @Enumerated
    @Column(name = "kingdom", nullable = false)
    private Kingdom kingdom;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "body_id", referencedColumnName = "item_id")
    private AbstractItem body;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "inventory_id", referencedColumnName = "item_id")
    private AbstractItem inventory;

    private transient Channel channel;

    private transient Map<Class<? extends GameEntity>, Map<Long, GameEntity>> local = new HashMap<>();

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public PlayerType getType() {
        return type;
    }

    public void setType(PlayerType type) {
        this.type = type;
    }

    @Override
    public Position getPos() {
        return pos;
    }

    @Override
    public void setPos(Position pos) {
        this.pos = pos;
    }

    public boolean isMale() {
        return male;
    }

    public FaceStyle getFaceStyle() {
        return faceStyle;
    }

    public void setFaceStyle(FaceStyle faceStyle) {
        this.faceStyle = faceStyle;
    }

    public void setMale(boolean male) {
        this.male = male;

    }

    public Kingdom getKingdom() {
        return kingdom;
    }

    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }

    public AbstractItem getBody() {
        return body;
    }

    public void setBody(AbstractItem body) {
        this.body = body;
    }

    public AbstractItem getInventory() {
        return inventory;
    }

    public void setInventory(AbstractItem inventory) {
        this.inventory = inventory;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public void send(AbstractPacket packet) {
        if (channel != null) {
            channel.writeAndFlush(packet);
        }
    }

    public void addLocal(GameEntity entity) {
        Map<Long, GameEntity> localEntities;
        if (!local.containsKey(entity.getClass())) {
            localEntities = new HashMap<>();
            local.put(entity.getClass(), localEntities);
        } else {
            localEntities = local.get(entity.getClass());
        }
        localEntities.put(entity.getId(), entity);
    }

    public boolean hasLocal(GameEntity entity) {
        return local.containsKey(entity.getClass()) && local.get(entity.getClass()).containsKey(entity.getId());
    }

    public void removeLocal(GameEntity entity) {
        if (local.containsKey(entity.getClass())) {
            local.get(entity.getClass()).remove(entity.getId());
        }
    }

    public <T extends GameEntity> List<T> getLocal(Class<T> clazz) {
        if (local.containsKey(clazz)) {
            return new ArrayList<>(((Map<Long, T>) local.get(clazz)).values());
        } else {
            return new ArrayList<>();
        }
    }

    public String getModel() {
        switch (getType()) {
            case DEV:
                return "model.creature.gmdark";
            case ARCH:
            case GM:
                return "model.creature.humanoid.human.skeleton";
            default:
                return String.format("model.creature.humanoid.human.player.%s.%s",
                        isMale() ? "male" : "female", getKingdom().getResName());
        }
    }

    public String getFullName() {
        switch (getType()) {
            case REGULAR:
                return getUsername();
            default:
                return String.format("%s (%s)", getUsername(), getType().toString());
        }
    }

    @Override
    public void onPlayerMovedTile(Position pos, int xOffset, int yOffset) {
        List<GameEntity> removeLocals = new ArrayList<>();

        // Remove items no longer in local
        PlayerHelper.getLocalItems(this).forEach(i -> {
            if (!pos.isLocal(i.getPos())) {
                send(new RemoveObjectPacket(i.getId()));
                removeLocals.add(i);
            }
        });

        removeLocals.forEach(this::removeLocal);
    }

}
