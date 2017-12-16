package net.nano.net.channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

// TODO log the addresses
public class LoggingChannelHandler implements ChannelHandler {

    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingChannelHandler.class);

    @Override
    public void opened(Channel channel) {
        LOGGER.info("{} OPEN", channel);
    }

    @Override
    public void openFailed(Channel channel, Throwable cause) {
        LOGGER.info("OPEN FAILED - {}", channel, cause);
    }

    @Override
    public void closed(Channel channel) {
        LOGGER.info("{} CLOSED", channel);
    }

    @Override
    public void closeFailed(Channel channel, Throwable cause) {
        LOGGER.info("{} CLOSE FAILED - {}", channel, cause);
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
