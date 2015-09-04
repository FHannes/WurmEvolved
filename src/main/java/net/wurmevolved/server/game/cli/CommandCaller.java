package net.wurmevolved.server.game.cli;

import net.wurmevolved.common.constants.PlayerType;
import net.wurmevolved.server.game.World;
import net.wurmevolved.server.game.data.Player;

public interface CommandCaller {

    World getWorld();
    Player getPlayer();
    PlayerType getType();
    void writeLine(String line);

}
