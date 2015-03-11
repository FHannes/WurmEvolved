package com.wurmemu.server.game.data;

import com.wurmemu.server.game.logic.entities.GameEntity;

import javax.persistence.*;

@Entity
@Table(name = "items")
public class Item implements GameEntity {

    @Id
    @Column(name = "item_id", nullable = false)
    private long id;

    private Position pos;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
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
