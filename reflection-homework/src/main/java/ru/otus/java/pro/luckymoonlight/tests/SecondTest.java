package ru.otus.java.pro.luckymoonlight.tests;

import ru.otus.java.pro.luckymoonlight.annotation.*;
import ru.otus.java.pro.luckymoonlight.exception.TestException;

public class SecondTest {

    @BeforeSuite
    public void beforeSecond() {
        System.out.println("beforeSecond завершен");
    }

    @Test(priority = 10)
    public void secondTest1() {
        System.out.println("secondTest_1 завершен");
    }

    @Test(priority = 10)
    public void secondTest2() {
        System.out.println("secondTest_2 завершен");
    }

    @Test(priority = 10)
    @Disabled
    public void secondTest3() {
        System.out.println("secondTest_3 не должны видеть!");
    }

    @Test(priority = 4)
    @After
    public void secondTest4() {
        System.out.println("secondTest_4 завершен");
    }

    @Test
    @Disabled
    public void secondTest5() {
        System.out.println("secondTest_5 не должны видеть!");
    }

    @Test(priority = 3)
    @ThrowsException(exception = TestException.class)
    public void secondTest6() {
        throw new TestException("secondTest_6 error");
    }

    @Test
    public void secondTest7() {
        throw new TestException("secondTest_7 error");
    }

    @Test
    public void secondTest8() {
        throw new TestException("secondTest_8 error");
    }

}
