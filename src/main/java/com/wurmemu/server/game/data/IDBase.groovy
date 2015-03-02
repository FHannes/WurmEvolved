package com.wurmemu.server.game.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import java.util.concurrent.atomic.AtomicLong

@Entity
@Table(name = "id_bases")
class IDBase {

    @Id
    @Column(name = "name", nullable = false)
    String name

    @Column(name = "base", nullable = false)
    AtomicLong base

    long next() {
        base.getAndAdd(16)
    }

}
