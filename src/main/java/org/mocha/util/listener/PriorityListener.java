package org.mocha.util.listener;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

public final class PriorityListener<E> implements Comparable<PriorityListener<E>> {
    public final UUID ID;
    private @Getter @Setter int priority;
    private PriorityConsumer<E> consumer;

    public PriorityListener(UUID id, int priority, PriorityConsumer<E> consumer) {
        ID = id;
        this.priority = priority;
        this.consumer = consumer;
    }

    public void accept(CustomEvent<E> t) {
        consumer.accept(t.setListenerId(ID));
    }

    @Override
    public int compareTo(PriorityListener<E> pc) {
        return this.priority - pc.priority;
    }

    @FunctionalInterface
    public interface PriorityConsumer<E> {

        public void accept(CustomEvent<E> event);

        public default PriorityListener<E> build(UUID id, int priority) {
            return new PriorityListener<E>(id, priority, this);
        }
    }
}