package com.wurmemu.server.game.data;

import com.wurmemu.server.game.logic.entities.GameEntity;

import javax.persistence.*;

@Entity
@Table(name = "players")
public class Player implements GameEntity {

    @Id
    @Column(name = "player_id", nullable = false)
    private long id;

    @Column(name = "username", nullable = false)
    private String username;

    private Position pos;

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

}
