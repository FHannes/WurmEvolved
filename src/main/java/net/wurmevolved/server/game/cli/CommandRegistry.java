package net.wurmevolved.server.game.cli;

import net.wurmevolved.common.constants.PlayerType;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistry {

    private static CommandRegistry instance;

    private Map<String, Class<? extends AbstractCommand>> commands = new HashMap<>();

    CommandRegistry() {
        Reflections reflections = new Reflections(getClass().getPackage().getName() + ".commands");
        for (Class<?> classType : reflections.getTypesAnnotatedWith(Command.class)) {
            if (AbstractCommand.class.isAssignableFrom(classType)) {
                for (String command : AbstractCommand.getCommands(classType)) {
                    commands.put(command.toLowerCase(), (Class<? extends AbstractCommand>) classType);
                }
            }
        }
    }

    public static CommandRegistry getInstance() {
        return instance != null ? instance : (instance = new CommandRegistry());
    }

    public AbstractCommand get(String command, PlayerType type) {
        command = command.toLowerCase();
        if (commands.containsKey(command)) {
            try {
                Class<? extends AbstractCommand> cmdType = commands.get(command);
                Command cmd = cmdType.getAnnotation(Command.class);
                switch (type) {
                    case ARCH:
                        switch (cmd.type()) {
                            case DEV:
                                return null;
                        }
                        break;
                    case GM:
                        switch (cmd.type()) {
                            case DEV:
                            case ARCH:
                                return null;
                        }
                        break;
                    case REGULAR:
                        switch (cmd.type()) {
                            case DEV:
                            case ARCH:
                            case GM:
                                return null;
                        }
                        break;
                }
                return cmdType.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
