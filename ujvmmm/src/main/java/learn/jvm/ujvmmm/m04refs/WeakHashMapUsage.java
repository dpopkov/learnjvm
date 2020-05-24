package learn.jvm.ujvmmm.m04refs;

import java.util.WeakHashMap;

public class WeakHashMapUsage {
    @SuppressWarnings("UnusedAssignment")
    public static void main(String[] args) {
        WeakHashMap<Person, PersonMetaData> weakHashMap = new WeakHashMap<>();
        Person kevin = new Person();
        weakHashMap.put(kevin, new PersonMetaData());

        PersonMetaData metaData = weakHashMap.get(kevin);
        System.out.println(metaData);

        kevin = null;
        System.gc();

        try {
            Thread.sleep(1L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (weakHashMap.containsValue(metaData)) {
            System.out.println("Still contains key");
        } else {
            System.out.println("Key gone");
        }
    }
}
