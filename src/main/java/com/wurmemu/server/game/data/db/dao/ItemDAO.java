package com.wurmemu.server.game.data.db.dao;

import com.wurmemu.server.game.data.AbstractItem;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public class ItemDAO {

    @PersistenceContext
    private EntityManager em;

    public AbstractItem save(AbstractItem item) {
        em.merge(item);
        return item;
    }

    public List<AbstractItem> list() {
        return em.createQuery("SELECT i FROM AbstractItem i", AbstractItem.class).getResultList();
    }

}
