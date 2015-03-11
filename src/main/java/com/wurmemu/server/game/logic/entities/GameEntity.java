package com.wurmemu.server.game.logic.entities;

import com.wurmemu.server.game.data.Position;

public interface GameEntity {

    public long getId();
    public void setId(long id);

    public Position getPos();
    public void setPos(Position pos);

}