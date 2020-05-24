package learn.jvm.ujvmmm.m04refs;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;

public class PhantomReferencesUsage {
    @SuppressWarnings("UnusedAssignment")
    public static void main(String[] args) {
        ReferenceQueue<Person> queue = new ReferenceQueue<>();
        ArrayList<FinalizePerson> list = new ArrayList<>();
        ArrayList<Person> people = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Person p = new Person();
            people.add(p);
            list.add(new FinalizePerson(p, queue));
        }

        people = null;
        System.gc();

        for (PhantomReference<Person> ref : list) {
            System.out.println(ref.isEnqueued());
        }

        Reference<? extends Person> referenceFromQueue;
        while ((referenceFromQueue = queue.poll()) != null) {
            ((FinalizePerson) referenceFromQueue).cleanup();
            referenceFromQueue.clear();
        }
    }
}

class FinalizePerson extends PhantomReference<Person> {

    public FinalizePerson(Person referent, ReferenceQueue<? super Person> q) {
        super(referent, q);
    }

    public void cleanup() {
        System.out.println("Person is finalizing resources");
    }
}
