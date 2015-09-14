package net.wurmevolved.server.game.logic.actions;

import net.wurmevolved.common.constants.ActionType;

import java.lang.annotation.Annotation;

public abstract class AbstractAction {

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

}
