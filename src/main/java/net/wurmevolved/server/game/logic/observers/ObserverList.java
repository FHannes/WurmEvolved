package net.wurmevolved.server.game.logic.observers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * A class which stores a set of observers. It is meant to be used to store observers which target specific classes.
 *
 * @param <T>
 *        The type of the observer which will be stored in the object. The type has to be a subclass of the interface
 *        {@link AbstractObserver}.
 */
public class ObserverList<T extends AbstractObserver> {

    private Set<T> observers = new HashSet<>();

    /**
     * Add an observer to the set.
     *
     * @param observer
     *        The given observer object.
     */
    public void add(T observer) {
        assert observer != null;

        observers.add(observer);
    }

    /**
     * Remove an observer from the set.
     *
     * @param observer
     *        The given observer object.
     */
    public void remove(T observer) {
        assert observer != null;

        observers.remove(observer);
    }

    /**
     * Returns a collection of all stored observers.
     *
     * @return A new {@link ArrayList} object containing all stored observers.
     */
    public Collection<T> getAll() {
        return new ArrayList<>(observers);
    }

}
