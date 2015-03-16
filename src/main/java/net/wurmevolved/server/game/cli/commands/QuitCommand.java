package net.wurmevolved.server.game.cli.commands;

import net.wurmevolved.server.game.cli.AbstractCommand;
import net.wurmevolved.server.game.cli.Command;
import net.wurmevolved.server.game.cli.CommandCaller;

@Command(
        commands = {"quit", "exit"},
        description = "Terminate the server."
)
public class QuitCommand extends AbstractCommand {

    @Override
    public void process(CommandCaller caller, String args) {
        caller.writeLine("This command can not be called from within the game.");
        caller.writeLine("Please use the CLI interface to terminate the server.");
    }

}
