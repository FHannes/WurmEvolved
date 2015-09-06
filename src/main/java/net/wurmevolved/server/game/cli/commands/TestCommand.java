package net.wurmevolved.server.game.cli.commands;

import net.wurmevolved.common.constants.ItemType;
import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.cli.AbstractCommand;
import net.wurmevolved.server.game.cli.Command;
import net.wurmevolved.server.game.cli.CommandCaller;
import net.wurmevolved.server.game.data.Item;
import net.wurmevolved.server.game.data.Player;
import net.wurmevolved.server.game.data.factory.ItemFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Command(
        commands = {"test"},
        description = "Test some feature.",
        type = PlayerType.REGULAR
)
public class TestCommand extends AbstractCommand {

    private ItemFactory itemFactory = new ItemFactory();

    @Override
    public void process(CommandCaller caller, String args) {
        World world = caller.getWorld();
        Item item = itemFactory.makeItem(ItemType.HATCHET, 35.26F);
        item.setPos(caller.getPlayer().getPos());
        world.getTerrainBuffer().getTile(caller.getPlayer().getPos()).addItem(item);
        // send add item
    }

}
