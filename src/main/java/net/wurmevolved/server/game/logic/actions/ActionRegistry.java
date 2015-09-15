package net.wurmevolved.server.game.logic.actions;

import net.wurmevolved.common.constants.ActionType;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.net.packets.client.SendActionPacket;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class ActionRegistry {

    private static ActionRegistry instance;

    private Map<Short, Class<? extends AbstractAction>> actions = new HashMap<>();

    ActionRegistry() {
        Reflections reflections = new Reflections(getClass().getPackage().getName() + ".impl");
        for (Class<?> classType : reflections.getTypesAnnotatedWith(Action.class)) {
            if (AbstractAction.class.isAssignableFrom(classType)) {
                ActionType type = AbstractAction.getType(classType);
                if (type != ActionType.UNKNOWN) {
                    register(type, (Class<? extends AbstractAction>) classType);
                }
            }
        }
    }

    public static ActionRegistry getInstance() {
        return instance != null ? instance : (instance = new ActionRegistry());
    }

    private void register(ActionType type, Class<? extends AbstractAction> action) {
        actions.put(type.getId(), action);
    }

    public AbstractAction makeAction(World world, Player player, SendActionPacket packet) {
        Class<? extends AbstractAction> actionClass = actions.get(packet.getActionType().getId());
        if (actionClass != null) {
            try {
                return actionClass.getConstructor(World.class, Player.class, SendActionPacket.class).newInstance(world, player, packet);
            } catch (Exception e) { e.printStackTrace(); }
        }
        return null;
    }

}
