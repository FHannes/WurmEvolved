package com.wurmemu.server.game.data.db

import org.springframework.context.support.ClassPathXmlApplicationContext

class DB {

    private static instance
    private context

    DB() {
        context = new ClassPathXmlApplicationContext("spring-config.xml")
    }

    static getInstance() {
        instance != null ? instance : (instance = new DB())
    }

    def getDAO(name) {
        context.getBean(name)
    }

    def close() {
        context.close()
    }

}
