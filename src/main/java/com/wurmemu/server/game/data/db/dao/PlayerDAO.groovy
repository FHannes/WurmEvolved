package com.wurmemu.server.game.data.db.dao

import com.wurmemu.server.game.data.Player
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Transactional
class PlayerDAO {

    @PersistenceContext
    private EntityManager em

    Player save(Player player) {
        em.merge(player)
        player
    }

    List<Player> list() {
        em.createQuery("SELECT p FROM Player p", Player.class).getResultList()
    }

}
