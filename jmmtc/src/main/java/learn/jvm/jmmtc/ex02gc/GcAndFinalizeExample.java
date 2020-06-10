package learn.jvm.jmmtc.ex02gc;

import org.apache.commons.cli.*;
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
        CommandLine cmd = prepareCmdOptions(args);
        printMemory("Starting");

        int numObjects = Integer.parseInt(cmd.getOptionValue("numObjects", "10"));
        boolean runGC = cmd.hasOption("runGC");
        boolean waitGC = cmd.hasOption("waitGC");
        boolean waitEnd = cmd.hasOption("waitKey");

        LOG.info("Generating {} objects", numObjects);
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
            printMemory("After GC");
        }

        if (waitEnd) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Print any key to finish...");
            scanner.nextLine();
        }
    }

    private static CommandLine prepareCmdOptions(String[] args) {
        Options options = new Options();
        Option numObjects = new Option("n", "numObjects", true, "number of objects");
        options.addOption(numObjects);
        options.addOption("runGC", false, "run System.gc()");
        options.addOption("waitGC", false, "pause to give time for garbage collection");
        options.addOption("waitKey", false, "wait until user prints any key");
        options.addOption("help", "print this help");
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
            if (cmd.hasOption("help")) {
                printHelp(options);
                System.exit(0);
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            printHelp(options);
            System.exit(1);
        }
        return cmd;
    }

    private static void printHelp(Options options) {
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp(GcAndFinalizeExample.class.getSimpleName(), options, true);
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
