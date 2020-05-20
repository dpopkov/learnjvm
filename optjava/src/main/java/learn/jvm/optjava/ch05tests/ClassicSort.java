package learn.jvm.optjava.ch05tests;

import java.util.*;

/*
Run with JVM options:
 java -Xms2048m -Xmx2048m -XX:+PrintCompilation
 or
 java -Xms2048m -Xmx2048m -verbose:gc
 */
/** Example of bad benchmark test. */
public class ClassicSort {
    private static final int NUM_ITEMS = 1_000;
    private static final int NUM_SORTS = 150_000;
    private static final List<Integer> testData = new ArrayList<>();

    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < NUM_ITEMS; i++) {
            testData.add(random.nextInt(Integer.MAX_VALUE));
        }
        System.out.println("Testing sort algorithm");
        double startTime = System.nanoTime();
        for (int i = 0; i < NUM_SORTS; i++) {
            List<Integer> copy = new ArrayList<>(testData);
            Collections.sort(copy);
        }
        double endTime = System.nanoTime();
        double timePerOperation = ((endTime - startTime) / (1_000_000_000L * NUM_SORTS));
        System.out.println("Result: " + (1 / timePerOperation) + " operations per second");
    }
}
