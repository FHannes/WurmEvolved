package net.wurmevolved.server.game.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicLong;

@Entity
@Table(name = "id_bases")
public class IDBase {

    @Id
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "base", nullable = false)
    private AtomicLong base;

    public IDBase() {

    }

    public IDBase(String name, AtomicLong base) {
        this.name = name;
        this.base = base;
    }

    public String getName() {
        return name;
    }

    public long next() {
        return base.getAndAdd(16);
    }

}
