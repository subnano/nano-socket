package net.nano.net.reactor;

public interface Reactor extends EventExecutor {

    void start();

    void stop();

}
