package com.wurmemu.server.game.data.db.dao;

import com.wurmemu.server.game.data.Tile;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class TileDAO {

    @PersistenceContext
    private EntityManager em;

    public Tile save(Tile tile) {
        em.merge(tile);
        return tile;
    }

    public void save(Tile[][] tiles) {
        for (Tile[] tileRow : tiles) {
            for (Tile tile : tileRow) {
                em.merge(tile);
            }
        }
    }

    public List<Tile> list() {
        return em.createQuery("SELECT t FROM Tile t", Tile.class).getResultList();
    }

    public List<Tile> list(int xOffset, int yOffset, int range) {
        TypedQuery<Tile> query = em.createQuery("SELECT t FROM Tile t WHERE x >= :x_min AND y >= :y_min AND " +
                "x < :x_max AND y < :y_max", Tile.class);
        query.setParameter("x_min", xOffset);
        query.setParameter("y_min", yOffset);
        query.setParameter("x_max", xOffset + range);
        query.setParameter("y_max", yOffset + range);
        return query.getResultList();
    }

}
