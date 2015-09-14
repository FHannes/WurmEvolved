package net.wurmevolved.server.game.logic.actions;

import net.wurmevolved.server.game.net.packets.AbstractPacket;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class ActionRegistry {

    private static ActionRegistry instance;

    private Map<Integer, Class<? extends AbstractAction>> actions = new HashMap<>();

    ActionRegistry() {
        Reflections reflections = new Reflections(getClass().getPackage().getName() + ".client");
        for (Class<?> classType : reflections.getTypesAnnotatedWith(Action.class)) {
            if (AbstractPacket.class.isAssignableFrom(classType)) {
                int packetID = AbstractPacket.getPacketID(classType);
                if (packetID != 0) {
                    register(packetID, (Class<? extends AbstractAction>) classType);
                }
            }
        }
    }

    public static ActionRegistry getInstance() {
        return instance != null ? instance : (instance = new ActionRegistry());
    }

    private void register(int type, Class<? extends AbstractAction> action) {
        actions.put(type, action);
    }

}
