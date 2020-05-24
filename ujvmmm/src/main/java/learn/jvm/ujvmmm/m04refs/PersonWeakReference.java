package learn.jvm.ujvmmm.m04refs;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class PersonWeakReference extends WeakReference<Person> {
    private final PersonCleaner cleaner;

    public PersonWeakReference(Person referent, PersonCleaner cleaner, ReferenceQueue<? super Person> q) {
        super(referent, q);
        this.cleaner = cleaner;
    }

    public void clean() {
        cleaner.clean();
    }
}
