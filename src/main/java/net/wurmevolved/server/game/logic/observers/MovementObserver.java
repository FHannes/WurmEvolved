package net.wurmevolved.server.game.logic.observers;

import net.wurmevolved.server.game.data.Position;

public interface MovementObserver extends AbstractObserver {

    void onPlayerMovedTile(Position pos, int xOffset, int yOffset);

}
