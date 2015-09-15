package net.wurmevolved.server.game.logic.actions;

import net.wurmevolved.common.constants.ActionType;
import net.wurmevolved.server.game.World;

import java.lang.annotation.Annotation;

public abstract class AbstractAction {

    private World world;
    private long duration;
    private long startTime;

    public AbstractAction(World world, long duration) {
        this.duration = duration;
        this.startTime = System.currentTimeMillis();
    }

    public ActionType getType() {
        return getType(getClass());
    }

    public static ActionType getType(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(Action.class);
        if (annotation != null) {
            return ((Action) annotation).value();
        }
        return ActionType.UNKNOWN;
    }

    public long getTimeRemaining() {
        return Math.max(0, duration - (System.currentTimeMillis() - startTime));
    }

    protected abstract void execute();

    public boolean process() {
        if (getTimeRemaining() == 0) {
            execute();
            return true;
        } else {
            return false;
        }
    }

    public World getWorld() {
        return world;
    }

    /**
     * Returns the duration of the action in milliseconds.
     */
    public long getDuration() {
        return duration;
    }

}
