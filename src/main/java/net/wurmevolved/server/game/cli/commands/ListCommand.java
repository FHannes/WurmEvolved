package net.wurmevolved.server.game.cli.commands;

import net.wurmevolved.server.game.cli.AbstractCommand;
import net.wurmevolved.server.game.cli.Command;
import net.wurmevolved.server.game.cli.CommandCaller;
import net.wurmevolved.server.game.data.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Command(
        commands = {"list"},
        description = "List online players."
)
public class ListCommand extends AbstractCommand {

    @Override
    public void process(CommandCaller caller, String args) {
        Collection<Player> players = caller.getWorld().getPlayers().all();
        caller.writeLine(String.format("Currently %d player(s) online.", players.size()));
        List<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getUsername());
        }
        String playerList = String.join(", ", names);
        if (!playerList.equals("")) {
            caller.writeLine(playerList);
        }
    }

}
