package net.wurmevolved.server.game.cli.commands;

import net.wurmevolved.server.game.cli.AbstractCommand;
import net.wurmevolved.server.game.cli.CLIProcessor;
import net.wurmevolved.server.game.cli.Command;

@Command(
        commands = {"quit", "exit"},
        description = "Terminate the server."
)
public class QuitCommand extends AbstractCommand {

    @Override
    public void process(CLIProcessor caller, String args) {

    }

}
