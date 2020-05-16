package learn.jvm.jmmtc.ex03softleaks;

public class Helper {
    public static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
