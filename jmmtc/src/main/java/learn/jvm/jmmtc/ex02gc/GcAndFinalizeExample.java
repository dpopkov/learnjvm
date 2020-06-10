package learn.jvm.jmmtc.ex02gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * Example of allocation and finalization.
 * Program arguments may be: 10 runGC noWaitGC noWaitEnd
 */
public class GcAndFinalizeExample {
    private static final Logger LOG = LoggerFactory.getLogger(GcAndFinalizeExample.class);

    public static final int KIBIBYTE = 1024;

    public static void main(String[] args) throws InterruptedException {
        printMemory("Starting");

        int argIdx = 0;
        int numObjects = 1_000_000;
        if (args.length > 0) {
            numObjects = Integer.parseInt(args[argIdx++]);
        }
        boolean runGC = false;
        if (args.length > argIdx) {
            runGC = "runGC".equals(args[argIdx++]);
        }
        boolean waitGC = false;
        if (args.length > argIdx) {
            waitGC = "waitGC".equals(args[argIdx++]);
        }
        boolean waitEnd = false;
        if (args.length > argIdx) {
            //noinspection UnusedAssignment
            waitEnd = "waitEnd".equals(args[argIdx++]);
        }

        for (int i = 0; i < numObjects; i++) {
            new Customer(Integer.toString(i));
        }
        printMemory("After Allocation");

        if (runGC) {
            LOG.info("Invoking System.gc()");
            System.gc();
            if (waitGC) {
                LOG.info("Waiting for GC");
                giveTimeForGC();
            }
        }
        printMemory("After GC");

        if (waitEnd) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Print any key to finish...");
            scanner.nextLine();
        }
    }

    private static void giveTimeForGC() throws InterruptedException {
        Thread.sleep(100L);
    }

    private static void printMemory(String title) {
        System.out.println("State of memory " + title);
        Runtime runtime = Runtime.getRuntime();
        long total = runtime.totalMemory();
        printKiBiBytes(total, "Total memory");
        long available = runtime.freeMemory();
        printKiBiBytes(available, "Available memory");
        long used = total - available;
        printKiBiBytes(used, "Used memory");
    }

    private static void printKiBiBytes(long amount, String title) {
        System.out.printf("%-16s :%7dk%n", title, amount / KIBIBYTE);
    }
}
