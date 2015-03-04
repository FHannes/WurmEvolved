package com.wurmemu.server.game.data.factory

import com.wurmemu.server.game.data.IDBase
import com.wurmemu.server.game.data.db.DB
import com.wurmemu.server.game.data.db.dao.IDBaseDAO

import java.util.concurrent.atomic.AtomicLong

class IDFactory {

    private IDBaseDAO dao
    int type
    private IDBase base

    IDFactory(String name, int type) {
        dao = (IDBaseDAO) DB.instance.getDAO("idBaseDAO")
        base = dao.load(name)
        if (base == null) {
            base = new IDBase(name: name, base: new AtomicLong())
        }
        this.type = type
    }

    long makeID() {
        long id = base.next() + type
        dao.save(base)
        id
    }

}
