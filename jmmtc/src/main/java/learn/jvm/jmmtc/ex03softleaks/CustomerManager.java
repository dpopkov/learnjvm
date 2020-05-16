package learn.jvm.jmmtc.ex03softleaks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static learn.jvm.jmmtc.ex03softleaks.Helper.sleep;

public class CustomerManager {
    private final List<Customer> customers = new ArrayList<>();
    private int nextId = 0;

    public void addCustomer(Customer customer) {
        synchronized (this) {
            customer.setId(nextId);
            nextId++;
        }
        customers.add(customer);
    }

    public Customer getNextCustomer() {
        //should do:
        //customers.remove(0);
        return customers.get(0);
    }

    public void printHowManyCustomers() {
        int size = customers.size();
        System.out.println("" + new Date() + " : " + size);
    }

    public void displayCustomers() {
        synchronized (customers) {
            for (Customer c : customers) {
                System.out.println(c.toString());
                sleep(500L);
            }
        }
    }
}
