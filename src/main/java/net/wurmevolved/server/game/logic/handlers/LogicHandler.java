package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.server.game.net.packets.AbstractPacket;

public abstract class LogicHandler {

    public abstract void join();
    public abstract void handle(AbstractPacket packet);
    public abstract void leave();

}
