package net.nano.net.channel;

import java.nio.ByteBuffer;

public interface ChannelHandler {
    void opened(Channel channel);

    void openFailed(Channel channel, Throwable cause);

    void closed(Channel channel);

    void closeFailed(Channel channel, Throwable cause);

    void dataReceived(Channel channel, ByteBuffer byteBuffer);

    void dataReadFailed(Channel channel, Throwable cause);
}
