package learn.jvm.jmmtc.ex03softleaks;

import learn.jvm.jmmtc.ex02gc.GcAndFinalizeExample;
import org.apache.commons.cli.*;

import static learn.jvm.jmmtc.ex03softleaks.Helper.*;

public class CustomerHarness {

    @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) {
        CommandLine cmd = prepareCmdOptions(args);
        boolean makeLeak = cmd.hasOption("makeLeak");
        long delay = 5000L;
        if (cmd.hasOption("outputDelay")) {
            delay = Long.parseLong(cmd.getOptionValue("outputDelay"));
        }
        CustomerManager manager = new CustomerManager(makeLeak);
        GenerateCustomerTask task = new GenerateCustomerTask(manager);
        for (int user = 0; user < 10; user++) {
            Thread t = new Thread(task);
            t.start();
        }

        //main thread is now acting as the monitoring thread
        while (true) {
            sleep(delay);
            manager.printHowManyCustomers();
            printAvailableMemory();
        }
    }

    private static void printAvailableMemory() {
        System.out.println("Available memory: " + Runtime.getRuntime().freeMemory() / 1024 + "k");
    }

    private static CommandLine prepareCmdOptions(String[] args) {
        Options options = new Options();
        options.addOption("L", "makeLeak", false, "make memory leak");
        options.addOption("d", "outputDelay", true, "output delay in ms");
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
}
