package net.nano.core.util;

public final class TimeUtil {

    private TimeUtil() {
        // can't touch this
    }

    // TODO sleepMicros

    public static void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }
}
