package net.nano.net.reactor;

import net.nano.core.util.time.IdleStrategy;
import net.nano.net.channel.Channel;
import net.nano.net.channel.Channel.State;
import net.nano.net.channel.ChannelException;
import net.nano.net.channel.ChannelHandler;
import net.nano.net.channel.NanoSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class NioChannelReactor implements NioReactor {

    private static final Logger LOGGER = LoggerFactory.getLogger(NioChannelReactor.class);

    private final Channel channel;
    // I expect the executor to become a little more elaborate as complexity grows
    private final ChannelHandler channelHandler;

    private final IdleStrategy idleStrategy;
    private final Thread reactorThread;

    private volatile boolean active = true;
    private State state;

    NioChannelReactor(final Channel channel,
                      final ChannelHandler channelHandler,
                      final IdleStrategy idleStrategy) {
        this.channel = channel;
        this.channelHandler = channelHandler;
        this.idleStrategy = idleStrategy;
        this.reactorThread = newReactorThread(new NioReactorTask());
    }

    private static Thread newReactorThread(Runnable runnable) {
        Thread thread = new Thread(runnable, NioChannelReactor.class.getSimpleName());
        thread.setDaemon(false);
        return thread;
    }

    @Override
    public void start() {
        LOGGER.info("starting reactor");
        reactorThread.start();
    }

    @Override
    public void stop() {
        active = false;
    }

    @Override
    public void execute(Runnable command) {
        throw new ChannelException("execute not supported");
    }

    private void tryRead() {
        try {
            int bytesRead = channel.read();
            if (bytesRead > 0) {
                //LOGGER.trace("tryRead");
                channelHandler.dataReceived(channel, channel.inboundBuffer());
            }
        } catch (IOException e) {
            channelHandler.dataReadFailed(channel, e);
            disconnectInErrorCondition();
        }
    }

    /**
     * Determine if running in the reactor thread.
     */
    private boolean isReactorThread() {
        return Thread.currentThread() == reactorThread;
    }

    private void stateConnected() {
        state = State.CONNECTED;
        channelHandler.connected(channel);
    }

    private void stateDisconnected() {
        state = State.DISCONNECTED;
        channelHandler.disconnected(channel);
    }

    private void disconnectInErrorCondition() {
        try {
            channel.disconnect();
        } catch (IOException e) {
            // we have already reported an error
        }
    }

    private void checkSocketConnectionPending() {
        if (channel instanceof NanoSocketChannel) {
            NanoSocketChannel socketChannel = (NanoSocketChannel) channel;
            if (socketChannel.isConnectionPending()) {
                try {
                    boolean connectSuccessful = socketChannel.finishConnect();
                    if (connectSuccessful) {
                        channelHandler.connected(channel);
                    }
                    else {
                        LOGGER.debug("still not connected ...");
                    }
                } catch (IOException e) {
                    channelHandler.connectFailed(channel, e);
                }
            }
        }
    }

    private void checkSocketConnectTimeout() {

    }

    private class NioReactorTask implements Runnable {

        @Override
        public void run() {
            while (active) {
                if (channel.isConnected()) {

                    // check connection state change
                    if (State.DISCONNECTED == state) {
                        stateConnected();
                    }

                    // see if data is available
                    tryRead();
                }

                // what to do if disconnected
                else {
                    checkSocketConnectionPending();

                    checkSocketConnectTimeout();

                    if (State.CONNECTED == state) {
                        stateDisconnected();
                    }
                }

                // simplest case to yield no matter the state
                idleStrategy.idle();
            }
        }
    }

}
