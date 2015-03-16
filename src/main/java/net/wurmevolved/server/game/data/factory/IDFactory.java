package net.wurmevolved.server.game.data.factory;

import net.wurmevolved.server.game.data.IDBase;
import net.wurmevolved.server.game.data.db.DB;
import net.wurmevolved.server.game.data.db.dao.IDBaseDAO;

import java.util.concurrent.atomic.AtomicLong;

public class IDFactory {

    private IDBaseDAO dao;
    private int type;
    private IDBase base;

    public IDFactory(String name, int type) {
        dao = (IDBaseDAO) DB.getInstance().getDAO("idBaseDAO");
        base = dao.load(name);
        if (base == null) {
            base = new IDBase(name, new AtomicLong());
        }
        this.type = type;
    }

    public long makeID() {
        long id = base.next() + type;
        dao.save(base);
        return id;
    }

}
