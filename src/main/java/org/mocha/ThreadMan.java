package org.mocha;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadMan {
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public static void execute(Runnable task) {
        threadPool.execute(task);
    } 

    public static Future<?> submit(Runnable task) {
        return threadPool.submit(task);
    }

    public static <T> Future<T> submit(Callable<T> task) {
        return threadPool.submit(task);
    }
}
