package com.pavlovich.list;

public class ListSample {
    public static List<Integer> getFilledList(int listSize) {
        List<Integer> list = new List<>();
        for (int i = 0; i < listSize; i++) {
            list.add(i);
        }
        return list;
    }
}
