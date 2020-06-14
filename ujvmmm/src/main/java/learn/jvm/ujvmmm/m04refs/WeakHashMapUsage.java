package learn.jvm.ujvmmm.m04refs;

import java.util.WeakHashMap;
import org.apache.commons.cli.*;

public class WeakHashMapUsage {
    public static void main(String[] args) {
        CommandLine cmd = prepareArgs(args);
        WeakHashMap<Person, PersonMetaData> weakHashMap = new WeakHashMap<>();
        Person kevin = new Person();
        weakHashMap.put(kevin, new PersonMetaData());

        PersonMetaData metaData = weakHashMap.get(kevin);
        System.out.println(metaData);

        //noinspection UnusedAssignment
        kevin = null;
        if (cmd.hasOption("GC")) {
            System.gc();
        }

        if (cmd.hasOption("w")) {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (weakHashMap.containsValue(metaData)) {
            System.out.println("Still contains key");
        } else {
            System.out.println("Key gone");
        }
    }

    private static CommandLine prepareArgs(String[] args) {
        Options options = new Options();
        options.addOption("GC", false, "garbage collection");
        options.addOption("w", false, "wait for garbage collection");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("java " + WeakHashMapUsage.class.getName(), options, true);
            System.exit(1);
        }
        return cmd;
    }
}
