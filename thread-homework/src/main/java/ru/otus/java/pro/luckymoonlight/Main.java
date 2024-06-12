package ru.otus.java.pro.luckymoonlight;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(Main.class);
        ThreadPool threadPool = new ThreadPool(5);

        for (int i = 0; i < 50; i++) {
            int counter = i;
            threadPool.execute(() -> {
                logger.info("execute Runner id :" + counter);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        threadPool.shutdown();
    }
}