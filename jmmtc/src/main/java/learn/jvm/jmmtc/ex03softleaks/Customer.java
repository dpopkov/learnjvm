package learn.jvm.jmmtc.ex03softleaks;

public class Customer {
    private int id;
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return id + " : " + name;
    }
}
