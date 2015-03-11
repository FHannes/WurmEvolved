package com.wurmemu.server.game.data.db.dao;

import com.wurmemu.server.game.data.Item;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public class ItemDAO {

    @PersistenceContext
    private EntityManager em;

    public Item save(Item item) {
        em.merge(item);
        return item;
    }

    public List<Item> list() {
        return em.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

}
