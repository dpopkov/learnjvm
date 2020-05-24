package learn.jvm.ujvmmm.m04refs;

import java.lang.ref.ReferenceQueue;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReferenceQueueUsage {
    @SuppressWarnings({"UnusedAssignment", "unused"})
    public static void main(String[] args) {
        Person person = new Person();
        final ReferenceQueue<Person> referenceQueue = new ReferenceQueue<>();
        PersonCleaner cleaner = new PersonCleaner();
        PersonWeakReference weakReference = new PersonWeakReference(person, cleaner, referenceQueue);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                PersonWeakReference wr = (PersonWeakReference) referenceQueue.remove();
                wr.clean();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        person = null;
        System.gc();

        Scanner in = new Scanner(System.in);
        System.out.println("Press any key to continue");
        in.nextLine();
        executorService.shutdown();
    }
}
