package learn.jvm.optjava.ch03hardos;

/*
 * Listing 3.1.
 * Simple code to exercise the cache hardware.
 */
public class Caching {
    private static final int NUMBER_OF_INTS_PER_CACHING_LINE = 16;
    private final int ARR_SIZE = 2 * 1024 * 1024;

    @SuppressWarnings("MismatchedReadAndWriteOfArray")
    private final int[] testData = new int[ARR_SIZE];

    void run() {
        System.err.println("Start: " + System.currentTimeMillis());
        for (int i = 0; i < 15_000; i++) {
            touchEveryLine();
            touchEveryItem();
        }
        System.err.println("Warm-up finished: " + System.currentTimeMillis());
        System.err.println("Item     Line");
        for (int i = 0; i < 100; i++) {
            long t0 = System.nanoTime();
            touchEveryLine();
            long t1 = System.nanoTime();
            touchEveryItem();
            long t2 = System.nanoTime();
            long elItem = t2 - t1;
            long elLine = t1 - t0;
            double diff = elItem - elLine;
            System.err.println(elItem + " " + elLine + " " + (100 * diff / elLine));
        }
    }

    private void touchEveryItem() {
        for (int i = 0; i < testData.length; i++) {
            testData[i]++;
        }
    }

    private void touchEveryLine() {
        for (int i = 0; i < testData.length; i += NUMBER_OF_INTS_PER_CACHING_LINE) {
            testData[i]++;
        }
    }
}
