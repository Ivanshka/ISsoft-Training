package com.pavlovich.list;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListTest {
    private List<Integer> list;
    private final int DEFAULT_LIST_SIZE = 5;

    @Before
    public void before() {
        list = ListSample.getFilledList(DEFAULT_LIST_SIZE);
    }

    @Test
    public void getSizeTest() {
        assertEquals("List size doesn't equal expected.", DEFAULT_LIST_SIZE, list.getSize());
    }

    @Test
    public void getFirstElementTest() {
        final int fromList = list.getFirstElement();
        final int listFirstElement = 0;

        assertEquals("First element doesn't equal expected element", listFirstElement, fromList);
    }

    @Test
    public void getLastElementTest() {
        final int fromList = list.getLastElement();
        final int listLastElement = DEFAULT_LIST_SIZE - 1;

        assertEquals("Last element doesn't equal expected element", listLastElement, fromList);
    }

    @Test
    public void getElementFromMiddleTest() {
        final int fromList = list.get(2);
        final int LIST_ELEMENT_WITH_INDEX_2 = 2;

        assertEquals("List element with specified index doesn't equal expected value",
                LIST_ELEMENT_WITH_INDEX_2, fromList);
    }

    @Test
    public void addTest() {
        final int listElement = 10;
        list.add(listElement);
        final int addedInteger = list.getLastElement();

        assertEquals("Added value doesn't equal the last value in the list", listElement, addedInteger);
    }

    @Test
    public void removeFirst() {
        final int expected = 1;
        list.removeAt(0);
        final int actual = list.getFirstElement();
        final int newSize = list.getSize();

        assertEquals("First element doesn't equal expected after removing", expected, actual);
        assertEquals("List size was not decremented", DEFAULT_LIST_SIZE - 1, newSize);
    }

    @Test
    public void removeLast() {
        final int expected = DEFAULT_LIST_SIZE - 2;
        list.removeAt(DEFAULT_LIST_SIZE - 1);
        final int actual = list.getLastElement();
        final int newSize = list.getSize();

        assertEquals("Last element doesn't equal expected after removing", expected, actual);
        assertEquals("List size was not decremented", DEFAULT_LIST_SIZE - 1, newSize);
    }

    @Test
    public void removeFromMiddle() {
        final int expected = 3;
        list.removeAt(2);
        final int actual = list.get(2);
        final int newSize = list.getSize();

        assertEquals("Last element doesn't equal expected after removing", expected, actual);
        assertEquals("List size was not decremented", DEFAULT_LIST_SIZE - 1, newSize);
    }

    @Test
    public void isEmptyTest() {
        list = new List<>();
        assertTrue("State of new list should be \"empty\"", list.isEmpty());

        list.add(1);
        list.removeAt(0);
        assertTrue("State of empty list should be \"empty\"", list.isEmpty());
    }

    @Test
    public void clearTest() {
        list.clear();
        assertTrue("List should be empty", list.isEmpty());
    }

    @Test(expected = IllegalStateException.class)
    public void clearCheckFirstElementTest() {
        list.clear();
        assertNotNull("First element should be null", list.getFirstElement());
    }

    @Test(expected = IllegalStateException.class)
    public void clearCheckLastElementTest() {
        list.clear();
        assertNotNull("First element should be null", list.getLastElement());
    }

    @Test
    public void containsTest(){
        final int elementThatListContains = 3;
        list = ListSample.getFilledList(5);

        assertTrue("List doesn't contain element, but should.", list.contains(elementThatListContains));
    }
}
