package org.mocha.util;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public  class Counter {
    private int initialSize = 0;
    private int i = 0;

    public Counter(int initialSize) {
        this.initialSize = initialSize;
        i = initialSize;
    }

    public int get() {
        return i;
    }

    public synchronized void  increment() {
        i++;
    }

    public synchronized void decrement() {
        i--;
    }

    public synchronized void reset() {
        i = initialSize;
    }
}
