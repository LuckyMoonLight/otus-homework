package ru.otus.java.pro.luckymoonlight;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import ru.otus.java.pro.luckymoonlight.annotation.Disabled;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Reflections reflections = new Reflections("ru.otus.java.pro.luckymoonlight.tests", new SubTypesScanner(false));
        for (Class testClass : reflections.getSubTypesOf(Object.class)) {
            if(!testClass.isAnnotationPresent(Disabled.class)) {
                new TestRunner().run(testClass);
            }
        }
    }
}