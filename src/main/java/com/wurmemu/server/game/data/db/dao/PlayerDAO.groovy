package com.wurmemu.server.game.data.db.dao

import com.wurmemu.server.game.data.Player
import com.wurmemu.server.game.data.Tile
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

    Player load(String username) {
        try {
            def query = em.createQuery("SELECT p FROM Player p WHERE username = :username", Player.class)
            query.setParameter("username", username)
            return query.singleResult
        } catch (all) {
            return null
        }
    }

}
