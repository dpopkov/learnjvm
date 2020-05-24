package learn.jvm.ujvmmm.m04refs;

public class PersonCleaner {
    public void clean() {
        System.out.println("Cleaning...");
        try {
            Thread.sleep(500L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cleaned");
    }
}
