package net.wurmevolved.server.game.data.db;

import net.wurmevolved.server.game.data.db.dao.AbstractDAO;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DB {

    private static DB instance;
    private ClassPathXmlApplicationContext context;

    public DB() {
        context = new ClassPathXmlApplicationContext("spring-config.xml");
    }

    public static DB getInstance() {
        return instance != null ? instance : (instance = new DB());
    }

    public AbstractDAO getDAO(String name) {
        return (AbstractDAO) context.getBean(name);
    }

    public void close() {
        context.close();
    }

}
