package net.wurmevolved.server.game.data.db.dao;

import net.wurmevolved.server.game.data.IDBase;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
public class IDBaseDAO extends AbstractDAO<IDBase> {

    @PersistenceContext
    private EntityManager em;

    public IDBase save(IDBase idBase) {
        em.merge(idBase);
        return idBase;
    }

    public IDBase load(String name) {
        try {
            TypedQuery<IDBase> query = em.createQuery("SELECT idb FROM IDBase idb WHERE name = :name", IDBase.class);
            query.setParameter("name", name);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<IDBase> list() {
        return em.createQuery("SELECT idb FROM id_bases idb", IDBase.class).getResultList();
    }

}
