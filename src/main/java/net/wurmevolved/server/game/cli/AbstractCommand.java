package net.wurmevolved.server.game.cli;

import java.lang.annotation.Annotation;

public abstract class AbstractCommand {

    public abstract void process(CLIProcessor caller, String args);

    public static String[] getCommands(Class<?> clazz) {
        Annotation annotation = clazz.getAnnotation(Command.class);
        return ((Command) annotation).commands();
    }

    public static void missingArgs(CLIProcessor caller) {
        caller.writeLine("More arguments needed");
    }

}
