package net.wurmevolved.server.game.cli.commands;

import net.wurmevolved.common.constants.ItemType;
import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.cli.AbstractCommand;
import net.wurmevolved.server.game.cli.Command;
import net.wurmevolved.server.game.cli.CommandCaller;
import net.wurmevolved.server.game.data.factory.ItemFactory;

@Command(
        commands = {"test"},
        description = "Test some feature.",
        type = PlayerType.REGULAR
)
public class TestCommand extends AbstractCommand {

    private ItemFactory itemFactory = new ItemFactory();

    @Override
    public void process(CommandCaller caller, String args) {
        caller.getWorld().getObjectHandler().drop(itemFactory.makeItem(ItemType.HATCHET, 35.26F), caller.getPlayer().getPos());
    }

}
