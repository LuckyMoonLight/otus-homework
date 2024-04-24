package ru.otus.java.pro.luckymoonlight;

import ru.otus.java.pro.luckymoonlight.annotation.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    private List<Method> methods = new ArrayList<>();
    private List<Method> beforeMethods = new ArrayList<>();
    private List<Method> afterMethods = new ArrayList<>();
    private Method beforeAll = null;
    private Method afterAll = null;
    
    public void valid(List<Method> methods) {
        if(methods.stream().filter(m -> m.isAnnotationPresent(AfterSuite.class)).count() > 1) {
            throw new RuntimeException("Несколько AfterSuite методов!");
        }

        if(methods.stream().filter(m -> m.isAnnotationPresent(BeforeSuite.class)).count() > 1) {
            throw new RuntimeException("Несколько BeforeSuite методов!");
        }

        if (methods.stream().anyMatch(m -> (m.isAnnotationPresent(Test.class)
                && (m.getAnnotation(Test.class).priority() < 1 || m.getAnnotation(Test.class).priority() > 10)))) {
            throw new RuntimeException("Приоритет методов может быть в 1-10");
        }

        if (methods.stream().anyMatch(m -> ((m.isAnnotationPresent(After.class) || m.isAnnotationPresent(Before.class)
                || m.isAnnotationPresent(ThrowsException.class)) && !m.isAnnotationPresent(Test.class)))) {
            throw new RuntimeException("Аннотации Before, After, ThrowsException можно использовать только с аннотацией Test");
        }
    }

    public void start (Class runClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Object testObject = runClass.getDeclaredConstructor().newInstance();
        valid(Arrays.asList(runClass.getDeclaredMethods()));

        clear();
        setUpMethods(runClass.getDeclaredMethods());

        executeMethod(testObject, beforeAll);
        int errorCount = runTest(testObject, beforeMethods, methods.stream()
                .sorted(Comparator.comparing((Method m) ->  m.getAnnotation(Test.class).priority()).reversed())
                .toList(), afterMethods);
        executeMethod(testObject, afterAll);
        System.out.println("Выполненно успешно: " + (methods.size() - errorCount) + " провалено: " + errorCount
                + " всего: " + methods.size());
    }

    private void setUpMethods(Method[] classMethods) {
        for (Method method : classMethods) {
            if (method.isAnnotationPresent(Disabled.class)) {
                continue;
            }
            if(method.isAnnotationPresent(Test.class)) {
                methods.add(method);
                if (method.isAnnotationPresent(After.class))
                    afterMethods.add(method);
                if (method.isAnnotationPresent(Before.class))
                    beforeMethods.add(method);
            }

            if(method.isAnnotationPresent(AfterSuite.class)){
                    afterAll = method;
            }

            if(method.isAnnotationPresent(BeforeSuite.class)){
                    beforeAll = method;
            }
        }
    }

    private void clear() {
        methods.clear();
        beforeMethods.clear();
        afterMethods.clear();
        beforeAll = null;
        afterAll = null;
    }

    private int runTest(Object object, List<Method> beforeMethods, List<Method> methods, List<Method> afterMethods) {
        int errorCount = 0;
        for (Method method : methods) {
            executeMethod(object, beforeMethods.stream().filter(m -> m != method).toList());
            if(!executeMethod(object, method))
                errorCount++;
            executeMethod(object, afterMethods.stream().filter(m -> m != method).toList());
        }
        return errorCount;
    }

    private void executeMethod(Object object, List<Method> methods) {
        for(Method method : methods)
            executeMethod(object, method);
    }

    // TODO
    private boolean executeMethod(Object object, Method method) {
        if(object != null && method != null) {
            try {
                method.invoke(object);
                return true;
            } catch (InvocationTargetException e) {
                if (e.getCause() != null) {
                    System.out.println(e.getCause().getMessage());
                    if(method.isAnnotationPresent(ThrowsException.class)) {
                        if(e.getCause().getClass().equals(method.getAnnotation(ThrowsException.class).exception()))
                            return true;
                    }
                    return false;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

}
