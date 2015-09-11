package net.wurmevolved.server.game.logic.handlers;

import net.wurmevolved.server.game.net.packets.AbstractPacket;
import net.wurmevolved.server.game.net.packets.client.ToggleButtonPacket;

public class GUIHandler extends LogicHandler {

    @Override
    public void join() {

    }

    @Override
    public void handle(AbstractPacket packet) {
        if (packet instanceof ToggleButtonPacket) {
            ToggleButtonPacket msgToggle = (ToggleButtonPacket) packet;
            System.out.println(String.format("Toggle button #%d: %s", msgToggle.getButtonID(), Boolean.toString(msgToggle.isToggleOn())));
        }
    }

    @Override
    public void leave() {

    }

}
