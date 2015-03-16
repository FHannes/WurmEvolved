package net.wurmevolved.server.game.cli.commands;

import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.cli.AbstractCommand;
import net.wurmevolved.server.game.cli.Command;
import net.wurmevolved.server.game.cli.CommandCaller;
import net.wurmevolved.server.game.data.Player;

@Command(
        commands = {"disconnect", "dc"},
        type = PlayerType.REGULAR,
        description = "Disconnects a player from the server."
)
public class DisconnectCommand extends AbstractCommand {

    @Override
    public void process(CommandCaller caller, String args) {
        if (caller.getPlayer() == null || caller.getType().equals(PlayerType.DEV) && !args.equals("")) {
            Player player = caller.getWorld().getPlayers().get(args);
            if (player == null) {
                caller.writeLine("Player not found");
            } else {
                player.getChannel().close();
            }
        } else {
            caller.getPlayer().getChannel().close();
        }
    }

}
