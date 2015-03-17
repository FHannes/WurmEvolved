package net.wurmevolved.server.game.data.db.dao;

import net.wurmevolved.server.game.data.AbstractItem;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

@Transactional
public class ItemDAO {

    @PersistenceContext
    private EntityManager em;

    public void save(AbstractItem item) {
        Queue<AbstractItem> saveItems = new LinkedList<>();
        saveItems.add(item);
        while (!saveItems.isEmpty()) {
            AbstractItem currentItem = saveItems.poll();
            em.merge(currentItem);
            saveItems.addAll(currentItem.getItems());
        }
    }

    public void save(List<AbstractItem> items) {
        Queue<AbstractItem> saveItems = new LinkedList<>();
        saveItems.addAll(items);
        while (!saveItems.isEmpty()) {
            AbstractItem currentItem = saveItems.poll();
            em.merge(currentItem);
            saveItems.addAll(currentItem.getItems());
        }
    }

    public List<AbstractItem> list() {
        return em.createQuery("SELECT i FROM AbstractItem i", AbstractItem.class).getResultList();
    }

}
