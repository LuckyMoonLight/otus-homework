package ru.otus.java.pro.luckymoonlight;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadWorker extends Thread {
    private final LinkedList<Runnable> tasks;
    private AtomicBoolean isShutdown;
    private final Logger logger = LoggerFactory.getLogger(ThreadWorker.class);

    public ThreadWorker (String threadName, LinkedList<Runnable> tasks, AtomicBoolean isShutdown) {
        this.setName(threadName);
        this.tasks = tasks;
        this.isShutdown = isShutdown;
    }

    @Override
    public void run() {
        while (true){
            Runnable currentTask;
            synchronized (tasks) {
                if (tasks.isEmpty()) {
                    if (isShutdown.get()) {
                        logger.info("Thread: " + this.getName() + " shutdown");
                        break;
                    }
                    try {
                        tasks.wait();
                        logger.info("Thread: " + this.getName() + " wait task");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                }
                if (!tasks.isEmpty()) {
                    currentTask = tasks.removeFirst();
                    try {
                        logger.info("Thread: " + this.getName() + " run task");
                        currentTask.run();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
