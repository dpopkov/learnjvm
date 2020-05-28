package learn.jvm.jvmutils.gcmonitor;

/**
 * Stores info about memory region.
 */
public class MRegion {
    private final boolean heap;
    private final String name;

    public MRegion(boolean heap, String name) {
        this.heap = heap;
        this.name = name;
    }

    public boolean isHeap() {
        return heap;
    }

    public String getName() {
        return name;
    }
}
