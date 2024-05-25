package ru.otus.java.pro.luckymoonlight;

import dto.Box;
import dto.Product;

public class Main {
    public static void main(String[] args) {
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
        System.out.println(product1);
        System.out.println(product2);
        Box testBox = new Box();
        testBox.forEach(System.out::println);
    }
}