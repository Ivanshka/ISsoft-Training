package org.pavlovich;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinarySearch {
    private static final Logger logger = LoggerFactory.getLogger(BinarySearch.class);

    public static int binarySearch(int[] sortedArray, int key) {
        return binarySearchRecursively(sortedArray, key, 0, sortedArray.length - 1);
    }

    private static int binarySearchRecursively(int[] sortedArray, int key, int lowerBound, int upperBound) {
        logger.debug("Lower bound is {}, upper bound is {}", lowerBound, upperBound);

        int middleIndex = lowerBound  + ((upperBound - lowerBound) / 2);

        logger.debug("Middle index is {}", middleIndex);

        if (upperBound < lowerBound) {
            logger.warn("Upper bound is less than lower bound!");
            logger.debug("Element index is not found!");
            return -1;
        }

        if (key == sortedArray[middleIndex]) {
            logger.debug("Element index is found: {}", middleIndex);
            return middleIndex;
        } else if (key < sortedArray[middleIndex]) {
            logger.debug("key is less than element {} with index {}, next iteration",
                    sortedArray[middleIndex], middleIndex);
            return binarySearchRecursively(sortedArray, key, lowerBound, middleIndex - 1);
        } else {
            logger.debug("key is bigger than element {} with index {}, next iteration",
                    sortedArray[middleIndex], middleIndex);
            return binarySearchRecursively(sortedArray, key, middleIndex + 1, upperBound);
        }
    }
}
