package net.nano.core.util.time;

public class NoOpIdleStrategy implements IdleStrategy {

    @Override
    public void idle() {
        // NO-OP
    }
}
