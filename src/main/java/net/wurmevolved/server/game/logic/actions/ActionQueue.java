package net.wurmevolved.server.game.logic.actions;

import java.util.LinkedList;
import java.util.Queue;

public class ActionQueue {

    private static ActionQueue instance;

    private Queue<AbstractAction> actions = new LinkedList<>();

    public static ActionQueue getInstance() {
        return instance != null ? instance : (instance = new ActionQueue());
    }

    public synchronized void queue(AbstractAction action) {
        if (action != null) {
            actions.add(action);
        }
    }

    public synchronized void process() {
        int count = actions.size();
        while (count-- != 0) {
            AbstractAction action = actions.poll();
            if (!action.process()) {
                actions.add(action);
            }
        }
    }

}
