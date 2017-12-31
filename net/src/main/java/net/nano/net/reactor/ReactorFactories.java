package net.nano.net.reactor;

import net.nano.core.util.time.SleepingIdleStrategy;

public class ReactorFactories {

    private static final SleepingIdleStrategy DEFAULT_SLEEPING_IDLE_STRATEGY = new SleepingIdleStrategy(50 );

    private static final SleepingIdleStrategy SLOW_SLEEPING_IDLE_STRATEGY = new SleepingIdleStrategy(50 * 1_000_000 * 100);

    public static ReactorFactory defaultFactory() {
        return (channel, channelHandler) -> new NioChannelReactor(
                channel,
                channelHandler,
                DEFAULT_SLEEPING_IDLE_STRATEGY
        );
    }
}
