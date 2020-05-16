package learn.jvm.jmmtc.ex03softleaks;

import static learn.jvm.jmmtc.ex03softleaks.Helper.*;

public class CustomerHarness {

    public static void main(String[] args) {
        CustomerManager manager = new CustomerManager();
        GenerateCustomerTask task = new GenerateCustomerTask(manager);
        for (int user = 0; user < 10; user++) {
            Thread t = new Thread(task);
            t.start();
        }

        //main thread is now acting as the monitoring thread
        while (true) {
            sleep(5000L);
            manager.printHowManyCustomers();
            printAvailableMemory();
        }
    }

    private static void printAvailableMemory() {
        System.out.println("Available memory: " + Runtime.getRuntime().freeMemory() / 1024 + "k");
    }
}
