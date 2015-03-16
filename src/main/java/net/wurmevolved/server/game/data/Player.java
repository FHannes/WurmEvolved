package net.wurmevolved.server.game.data;

import io.netty.channel.Channel;
import net.wurmevolved.common.constants.Kingdom;
import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.logic.entities.GameEntity;
import net.wurmevolved.server.game.net.packets.AbstractPacket;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "players")
public class Player implements GameEntity {

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
    @JoinColumn(name = "inventory_id", referencedColumnName = "item_id")
    private AbstractItem inventory;

    private transient Channel channel;

    private transient Map<Long, GameEntity> local = new HashMap<>();

    @Override
    public long getId() {
        return id;
    }

    @Override
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
        local.put(entity.getId(), entity);
    }

    public boolean hasLocal(GameEntity entity) {
        return local.containsKey(entity.getId());
    }

    public void removeLocal(GameEntity entity) {
        local.remove(entity.getId());
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

}
