package org.mocha.util.listener;

import java.util.UUID;

import lombok.Getter;

public abstract class CustomEvent<E> {
    public final E TYPE;
    private @Getter UUID listenerId;

    public CustomEvent(E type) {
        TYPE = type;
    }

    public CustomEvent<E> setListenerId(UUID listenerId) {
        this.listenerId = listenerId;
        return this;
    }
}
