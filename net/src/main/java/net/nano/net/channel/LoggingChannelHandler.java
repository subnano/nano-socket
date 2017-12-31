package net.nano.net.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

// TODO log the addresses
public class LoggingChannelHandler implements ChannelHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingChannelHandler.class);

    @Override
    public void connected(Channel channel) {
        LOGGER.info("{} CONNECT", channel);
    }

    @Override
    public void connectFailed(Channel channel, Throwable cause) {
        LOGGER.info("CONNECT FAILED - {}", channel, cause);
    }

    @Override
    public void disconnected(Channel channel) {
        LOGGER.info("{} DISCONNECT", channel);
    }

    @Override
    public void disconnectFailed(Channel channel, Throwable cause) {
        LOGGER.info("{} DISCONNECT FAILED - {}", channel, cause);
    }

    @Override
    public void dataReceived(Channel channel, ByteBuffer buffer) {
        LOGGER.info("{} DATA - {} bytes", channel, buffer.remaining());
    }

    @Override
    public void dataReadFailed(Channel channel, Throwable cause) {
        LOGGER.info("{} READ FAILED - {}", channel, cause);
    }
}
