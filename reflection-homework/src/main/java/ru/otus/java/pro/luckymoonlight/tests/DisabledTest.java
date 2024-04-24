package ru.otus.java.pro.luckymoonlight.tests;

import ru.otus.java.pro.luckymoonlight.annotation.Disabled;
import ru.otus.java.pro.luckymoonlight.annotation.Test;

@Disabled
public class DisabledTest {
    @Test
    public void disabledTest1() {
        System.out.println("Мы этого не увидим");
    }

    @Test
    public void disabledTest2() {
        System.out.println("и это тоже не увидим");
    }
}
