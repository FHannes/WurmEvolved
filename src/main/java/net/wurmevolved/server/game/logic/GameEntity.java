package net.wurmevolved.server.game.logic;

import net.wurmevolved.server.game.data.Position;

public interface GameEntity {

    long getId();

    Position getPos();
    void setPos(Position pos);

}