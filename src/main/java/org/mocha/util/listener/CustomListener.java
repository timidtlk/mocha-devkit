package org.mocha.util.listener;

import java.util.TreeSet;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.mocha.util.listener.PriorityListener.PriorityConsumer;

/**
 * A general purpose listener class that dispatches {@link CustomEvent} subclasses.
 * @param <E> Trigger class used in {@link CustomEvent} children and listeners.
 */
public class CustomListener<E> {
    ConcurrentHashMap<E, ConcurrentHashMap<UUID, PriorityListener<E>>> manager = new ConcurrentHashMap<>();

    public void addListener(E trigger, UUID id, int priority, PriorityConsumer<E> consumer) {
        var syn = manager.get(trigger);
        if (syn == null) {
            syn = new ConcurrentHashMap<UUID, PriorityListener<E>>();
            manager.put(trigger, syn);
        }

        synchronized(syn) {
            syn.put(id, consumer.build(id, priority));
        }
    }

    public UUID addListener(E trigger, int priority, PriorityConsumer<E> consumer) {
        var id = UUID.randomUUID();
        addListener(trigger, id, priority, consumer);
        return id;
    }

    public void removeListener(E trigger, UUID id) {
        var syn = manager.get(trigger);
        if (syn == null) return;

        synchronized(syn) {
            syn.remove(id);
        }
    }

    public void dispatch(CustomEvent<E> e) {
        if (manager.isEmpty()) return;

        var syn = manager.get(e.TYPE);
        if (syn == null) return;

        synchronized(syn) {
            var sorted = new TreeSet<>(syn.values());
            sorted.forEach(listener -> listener.accept(e));
        }
    }

    public void clear() {
        manager.clear();
    }
}
