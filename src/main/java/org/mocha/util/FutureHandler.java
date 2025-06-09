package org.mocha.util;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

public final class FutureHandler<T> {
    private Callable<T> callable;
    private Consumer<T> afterHandler;

    public FutureHandler(Callable<T> callable) {
        this.callable = callable;
    }

    public void call() {
        try {
            var res = callable.call();
            if (afterHandler != null) afterHandler.accept(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void then(Consumer<T> consumer) {
        this.afterHandler = consumer;
    }

    public void accept(T value) {
        afterHandler.accept(value);
    }
}
