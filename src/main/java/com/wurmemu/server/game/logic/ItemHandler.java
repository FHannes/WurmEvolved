package com.wurmemu.server.game.logic;

import com.wurmemu.server.game.World;
import com.wurmemu.server.game.data.Player;

public class ItemHandler {

    private World world;
    private Player player;

    public ItemHandler(World world, Player player) {
        this.world = world;
        this.player = player;
    }

}
