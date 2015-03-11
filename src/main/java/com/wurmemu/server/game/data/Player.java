package com.wurmemu.server.game.data;

import com.wurmemu.server.game.logic.entities.GameEntity;
import com.wurmemu.server.game.net.packets.AbstractPacket;
import io.netty.channel.Channel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    private Position pos;

    private transient Channel channel;

    private transient boolean developer;

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

    @Override
    public Position getPos() {
        return pos;
    }

    @Override
    public void setPos(Position pos) {
        this.pos = pos;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public boolean isDeveloper() {
        return developer;
    }

    public void setDeveloper(boolean developer) {
        this.developer = developer;
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

}
