package net.nano.net.channel;

import java.nio.ByteBuffer;

public interface ChannelHandler {
    void connected(Channel channel);

    void connectFailed(Channel channel, Throwable cause);

    void disconnected(Channel channel);

    void disconnectFailed(Channel channel, Throwable cause);

    void dataReceived(Channel channel, ByteBuffer byteBuffer);

    void dataReadFailed(Channel channel, Throwable cause);
}
