package net.nano.core.util.time;

import java.util.concurrent.locks.LockSupport;

public class SleepingIdleStrategy implements IdleStrategy {

    private final long sleepNanos;

    public SleepingIdleStrategy(long sleepNanos) {
        this.sleepNanos = sleepNanos;
    }

    @Override
    public void idle() {
        LockSupport.parkNanos(sleepNanos);
    }
}
