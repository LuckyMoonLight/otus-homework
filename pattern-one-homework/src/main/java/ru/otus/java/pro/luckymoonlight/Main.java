package ru.otus.java.pro.luckymoonlight;

import dto.Box;
import dto.Product;

import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger log = Logger.getLogger(Main.class.getName());
        Product product1 = Product.bulder()
            .id(1L)
            .title("title")
            .description("desc")
            .build();
        Product product2 = Product.bulder()
                .id(2L)
                .title("title2")
                .description("desc2")
                .cost(99999L)
                .weight(10L)
                .wight(10L)
                .height(10L)
                .length(1L)
                .build();
        log.info(product1.toString());
        log.info(product2.toString());
        Box testBox = new Box();
        testBox.forEach(b -> log.info(b));
    }
}