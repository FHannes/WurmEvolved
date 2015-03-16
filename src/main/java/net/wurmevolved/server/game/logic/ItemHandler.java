package net.wurmevolved.server.game.logic;

import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;

public class ItemHandler {

    private World world;
    private Player player;

    public ItemHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

}
