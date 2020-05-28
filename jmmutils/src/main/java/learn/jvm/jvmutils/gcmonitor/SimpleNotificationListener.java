package learn.jvm.jvmutils.gcmonitor;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.openmbean.CompositeData;
import java.lang.management.MemoryUsage;
import java.util.Map;
import java.util.function.Consumer;

class SimpleNotificationListener implements NotificationListener {
    private final Map<String, MRegion> memRegions;
    private final Consumer<String> output;

    public SimpleNotificationListener(Map<String, MRegion> memRegions, Consumer<String> output) {
        this.memRegions = memRegions;
        this.output = output;
    }

    @Override
    public void handleNotification(Notification notification, Object handBack) {
        if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
            GarbageCollectionNotificationInfo notificationInfo = GarbageCollectionNotificationInfo.from(
                    (CompositeData) notification.getUserData());
            Map<String, MemoryUsage> memBefore = notificationInfo.getGcInfo().getMemoryUsageBeforeGc();
            Map<String, MemoryUsage> memAfter = notificationInfo.getGcInfo().getMemoryUsageAfterGc();
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(notificationInfo.getGcAction()).append(" / ")
                    .append(notificationInfo.getGcCause()).append(" / ")
                    .append(notificationInfo.getGcName()).append(" / (");
            appendMemUsage(sb, memBefore);
            sb.append(") -> (");
            appendMemUsage(sb, memAfter);
            sb.append("), ").append(notificationInfo.getGcInfo().getDuration()).append(" ms]");
            output.accept(sb.toString());
        }
    }

    private void appendMemUsage(StringBuilder sb, Map<String, MemoryUsage> memUsage) {
        for (Map.Entry<String, MemoryUsage> entry : memUsage.entrySet()) {
            if (memRegions.get(entry.getKey()).isHeap()) {
                sb.append(entry.getKey()).append(" used=")
                        .append(entry.getValue().getUsed() >> 10)
                        .append("K; ");
            }
        }
    }
}
