package learn.jvm.jmmtc.ex03softleaks;

import java.util.UUID;

import static learn.jvm.jmmtc.ex03softleaks.Helper.sleep;

public class GenerateCustomerTask implements Runnable {

    private final CustomerManager manager;
    private int totalCustomersGenerated = 0;

    public GenerateCustomerTask(CustomerManager manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        while (true) {
            sleep(1L);
            // Simulate user adding a customer through a web page
            String name = new UUID(1L, 10L).toString();
            Customer c = new Customer(name);
            manager.addCustomer(c);
            totalCustomersGenerated++;
            manager.getNextCustomer();
            //System.out.println(totalCustomersGenerated);
        }
    }
}
