package ru.otus.java.pro.luckymoonlight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class ThreadPool {
    private final List<ThreadWorker> threadWorkers = new ArrayList<>();
    private final LinkedList<Runnable> tasks = new LinkedList<>();
    private AtomicBoolean isShutdown = new AtomicBoolean(false);
    private final Logger logger = LoggerFactory.getLogger(ThreadPool.class);

    public ThreadPool(int threadCount) {
        for (int i = 0; i < threadCount; i++) {
            ThreadWorker worker = new ThreadWorker("Id = " + i,tasks, isShutdown);
            worker.start();
            threadWorkers.add(worker);
        }
    }

    public void execute(Runnable task) {
        if (isShutdown.get()) {
            logger.info("Not add task, thread pool shutdown");
            return;
        }
        synchronized (tasks) {
            tasks.addLast(task);
            tasks.notify();
        }
        logger.info("Thread pools: add task");
    }

    public void shutdown() {
        logger.info("Thread pools: shutdown");
        isShutdown.set(true);
        synchronized (tasks) {
            tasks.notifyAll();
        }
    }

}
