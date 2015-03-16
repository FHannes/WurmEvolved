package net.wurmevolved.server.game.cli;

import net.wurmevolved.common.constants.PlayerType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Command {

    String[] commands();
    PlayerType type() default PlayerType.DEV;
    String description();

}
