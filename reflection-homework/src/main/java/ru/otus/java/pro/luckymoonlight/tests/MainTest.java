package ru.otus.java.pro.luckymoonlight.tests;

import ru.otus.java.pro.luckymoonlight.annotation.*;
import ru.otus.java.pro.luckymoonlight.exception.TestException;

import java.util.logging.Logger;

public class MainTest {

    @BeforeSuite
    public void beforeAll() {
        System.out.println("BeforeAll завершен!");
    }

    @AfterSuite
    public void afterAll() {
        System.out.println("afterAll завершен!");
    }

    @Test(priority = 10)
    public void test1() {
        System.out.println("test_1 завершен");
    }

    @Test(priority = 10)
    @Before
    public void test2() {
        System.out.println("test_2 завершен");
    }

    @Test(priority = 5)
    @Before
    public void test3() {
        System.out.println("test_3 завершен");
    }

    @Test(priority = 9)
    public void test4() {
        System.out.println("test_4 завершен");
    }

    @Test(priority = 2)
    public void test5() {
        System.out.println("test_5 завершен");
    }

    @Test
    public void test6() {
        System.out.println("test_6 завершен");
    }
    @Test(priority = 8)
    public void test7() {
        System.out.println("test_7 завершен");
    }

    @Test(priority = 8)
    @After
    public void test8() {
        System.out.println("test_8 завершен");
    }

    @Test
    public void test9() {
        throw new TestException("test_9 error");
    }
}
