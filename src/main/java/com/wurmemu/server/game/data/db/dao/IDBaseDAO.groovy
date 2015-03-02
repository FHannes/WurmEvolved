package com.wurmemu.server.game.data.db.dao

import com.wurmemu.server.game.data.IDBase
import com.wurmemu.server.game.data.Player
import org.springframework.transaction.annotation.Transactional

import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Transactional
class IDBaseDAO {

    @PersistenceContext
    private EntityManager em

    IDBase save(IDBase idBase) {
        em.merge(idBase)
        idBase
    }

    List<IDBase> list() {
        em.createQuery("SELECT idb FROM id_bases idb", IDBase.class).getResultList()
    }

    IDBase load(String name) {
        try {
            def query = em.createQuery("SELECT idb FROM IDBase idb WHERE name = :name", IDBase.class)
            query.setParameter("name", name)
            return query.singleResult
        } catch (all) {
            return null
        }
    }

}
