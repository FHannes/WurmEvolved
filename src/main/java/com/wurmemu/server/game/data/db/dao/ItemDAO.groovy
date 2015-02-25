package com.wurmemu.server.game.data.db.dao

import com.wurmemu.server.game.data.Item
import com.wurmemu.server.game.data.Player
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Transactional
class ItemDAO {

    @PersistenceContext
    private EntityManager em

    Player save(Item item) {
        em.merge(item)
        item
    }

    List<Player> list() {
        em.createQuery("SELECT i FROM Item i", Item.class).getResultList()
    }

}
