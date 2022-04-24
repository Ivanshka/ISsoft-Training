package org.pavlovich;

public class Runner {
    public static void main(String[] args) {
        int[] sortedArray = {0, 2, 4, 6, 7, 9, 11, 14, 17, 21};
        int index = BinarySearch.binarySearch(sortedArray, 9);

        System.out.println("Index is found: " + index);
    }
}
