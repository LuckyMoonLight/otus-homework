package dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Box implements Iterable<String> {
    private List<String> one = Arrays.asList("aaa", "bbb", "ccc");
    private List<String> two = Arrays.asList("test1", "test2");
    private List<String> three = Arrays.asList("three-test1", "3333333-test-3333333");
    private List<String> four = Arrays.asList("44444");

    @Override
    public Iterator<String> iterator() {
        List<String> iteratorList = new ArrayList<>();
        iteratorList.addAll(one);
        iteratorList.addAll(two);
        iteratorList.addAll(three);
        iteratorList.addAll(four);
        return iteratorList.iterator();
    }
}
