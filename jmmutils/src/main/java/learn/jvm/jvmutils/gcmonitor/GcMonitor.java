package learn.jvm.jvmutils.gcmonitor;

import javax.management.ListenerNotFoundException;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import java.io.PrintStream;
import java.lang.management.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Prints current state of memory and monitors GC events.
 */
public class GcMonitor {
    private static final long KB = 1024;
    private static final long MB = KB * KB;
    private static final long GB = MB * KB;
    private static final int REGION_NAME_LENGTH = 25;
    private static final String MEM_USAGE_FORMAT = "%-" + REGION_NAME_LENGTH + "s%s\t%.1f%%\t[%s]";

    private final Map<String, MRegion> memoryRegions;
    private final NotificationListener gcHandler;
    private final PrintStream out;

    public GcMonitor(PrintStream out) {
        this.out = out;
        int numRegions = ManagementFactory.getMemoryPoolMXBeans().size();
        memoryRegions = new HashMap<>(numRegions);
        for (MemoryPoolMXBean mb : ManagementFactory.getMemoryPoolMXBeans()) {
            MRegion region = new MRegion(mb.getType() == MemoryType.HEAP, mb.getName());
            memoryRegions.put(mb.getName(), region);
        }
        gcHandler = new SimpleNotificationListener(memoryRegions, out::println);
    }

    /**
     * Prints info about memory pools state (such as Eden, old gen, and survivor space).
     */
    public void printUsage(boolean heapOnly) {
        for (MemoryPoolMXBean mBean : ManagementFactory.getMemoryPoolMXBeans()) {
            if (!heapOnly || mBean.getType() == MemoryType.HEAP) {
                printMemoryPoolUsage(mBean.getName(), mBean.getUsage());
            }
        }
    }

    private void printMemoryPoolUsage(String title, MemoryUsage usage) {
        out.println(String.format(MEM_USAGE_FORMAT, memoryRegions.get(title).getName(),
                formatMemory(usage.getUsed()),
                usage.getMax() < 0 ? 0.0 : (double) usage.getUsed() / (double) usage.getMax() * 100,
                formatMemory(usage.getMax())));
    }

    /**
     * Starts the process of GC monitoring.
     */
    public void start() {
        for (GarbageCollectorMXBean mBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            ((NotificationEmitter) mBean).addNotificationListener(gcHandler, null, null);
        }
    }

    /**
     * Stops the process of GC monitoring.
     */
    public void stop() {
        for (GarbageCollectorMXBean mBean : ManagementFactory.getGarbageCollectorMXBeans()) {
            try {
                ((NotificationEmitter) mBean).removeNotificationListener(gcHandler);
            } catch (ListenerNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private static String formatMemory(long bytes) {
        if (bytes > GB) {
            return String.format("%.2f Gb", bytes / (double) GB);
        } else if (bytes > MB) {
            return String.format("%.2f Mb", bytes / (double) MB);
        } else if (bytes > KB) {
            return String.format("%.2f Kb", bytes / (double) KB);
        }
        return Long.toString(bytes);
    }
}
