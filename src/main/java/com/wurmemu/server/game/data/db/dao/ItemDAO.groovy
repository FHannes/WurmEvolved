package com.wurmemu.server.game.data.db.dao

import com.wurmemu.server.game.data.Item
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Transactional
class ItemDAO {

    @PersistenceContext
    private EntityManager em

    Item save(Item item) {
        em.merge(item)
        item
    }

    List<Item> list() {
        em.createQuery("SELECT i FROM Item i", Item.class).getResultList()
    }

}
