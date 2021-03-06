package net.wurmevolved.server.game.data.db.dao;

import net.wurmevolved.server.game.data.Player;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class PlayerDAO extends AbstractDAO<Player> {

    @PersistenceContext
    private EntityManager em;

    public Player save(Player player) {
        em.merge(player);
        return player;
    }

    public Player load(String username) {
        try {
            TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p WHERE username = :username", Player.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Player> list() {
        return em.createQuery("SELECT p FROM Player p", Player.class).getResultList();
    }

}
