package learn.jvm.ujvmmm.m03tools;

import java.util.Random;

public class AllocationOverwrite {
    @SuppressWarnings({"MismatchedReadAndWriteOfArray", "InfiniteLoopStatement"})
    public static void main(String[] args) throws Exception {
        int arraySize = 1_000_000;
        GCMe[] array = new GCMe[arraySize];
        int count = 0;
        Random rnd = new Random();
        while (true) {
            array[rnd.nextInt(arraySize)] = new GCMe();
            if (count % 1_000_000 == 0) {
                System.out.print(".");
            }
            count++;
//            Thread.sleep(1L);
        }
    }
}
