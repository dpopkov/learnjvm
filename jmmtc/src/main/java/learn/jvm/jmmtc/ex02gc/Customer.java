package learn.jvm.jmmtc.ex02gc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Customer {
    private static final Logger LOG = LoggerFactory.getLogger(Customer.class);

    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Customer{name='" + name + '\'' + '}';
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void finalize() {
        LOG.trace("This object is being gc'd.");
    }
}
