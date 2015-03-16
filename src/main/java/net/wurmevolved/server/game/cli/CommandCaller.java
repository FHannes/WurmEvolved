package net.wurmevolved.server.game.cli;

import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;

public interface CommandCaller {

    public World getWorld();
    public Player getPlayer();
    public PlayerType getType();
    public void writeLine(String line);

}
