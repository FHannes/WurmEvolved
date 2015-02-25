package com.wurmemu.server.game.data

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "items")
class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id", nullable = false)
    long id

}
