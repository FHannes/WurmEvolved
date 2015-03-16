package net.wurmevolved.server.game.logic;

import net.wurmevolved.server.game.data.Position;

public interface GameEntity {

    public long getId();

    public Position getPos();
    public void setPos(Position pos);

}