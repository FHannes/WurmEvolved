package com.wurmemu.server.game.data.db;

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

    public Object getDAO(String name) {
        return context.getBean(name);
    }

    public void close() {
        context.close();
    }

}
