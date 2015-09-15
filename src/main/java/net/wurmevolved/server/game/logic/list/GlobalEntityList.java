package net.wurmevolved.server.game.logic.list;

import net.wurmevolved.server.game.data.AbstractItem;
import net.wurmevolved.server.game.logic.GameEntity;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class GlobalEntityList<T extends GameEntity> {

    public static GlobalEntityList<AbstractItem> items = new GlobalEntityList<>();

    private Queue<T> initialEntities = new LinkedList<>();
    private Map<Long, T> entities = new HashMap<>();

    private void update() {
        int count = initialEntities.size();
        while (count-- != 0) {
            T entity = initialEntities.poll();
            if (entity.getId() != 0) {
                entities.put(entity.getId(), entity);
            } else {
                initialEntities.add(entity);
            }
        }
    }

    public synchronized T get(long id) {
        update();
        return entities.get(id);
    }

    public synchronized void add(T entity) {
        initialEntities.add(entity);
    }

    public synchronized void remove(long id) {
        update();
        entities.remove(id);
    }

    public synchronized void remove(T entity) {
        update();
        remove(entity.getId());
    }

}
