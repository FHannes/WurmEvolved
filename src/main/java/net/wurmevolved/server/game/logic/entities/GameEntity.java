package net.wurmevolved.server.game.logic.entities;

import net.wurmevolved.server.game.data.Position;

public interface GameEntity {

    public long getId();
    public void setId(long id);

    public Position getPos();
    public void setPos(Position pos);

}