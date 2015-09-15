package net.wurmevolved.server.game.data.db.dao;

import java.util.List;

public abstract class AbstractDAO<T> {

    public abstract List<T> list();

}
