package net.wurmevolved.server.game.logic.observers;

import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.data.Position;

public interface ItemObserver extends AbstractObserver {

    void onAddedToWorld(AbstractItem item, Position pos);
    void onRemovedFromWorld(AbstractItem item);
    void onChangedWorldPosition(AbstractItem item, Position posOld, Position posNew);

}
