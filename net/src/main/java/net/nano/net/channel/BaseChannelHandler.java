package net.nano.net.channel;

import java.nio.ByteBuffer;

public class BaseChannelHandler implements ChannelHandler {

    @Override
    public void connected(Channel channel) {
        // NO-OP
    }

    @Override
    public void connectFailed(Channel channel, Throwable cause) {
        // NO-OP
    }

    @Override
    public void disconnected(Channel channel) {
        // NO-OP
    }

    @Override
    public void disconnectFailed(Channel channel, Throwable cause) {
        // NO-OP
    }

    @Override
    public void dataReceived(Channel channel, ByteBuffer byteBuffer) {
        // NO-OP
    }

    @Override
    public void dataReadFailed(Channel channel, Throwable cause) {
        // NO-OP
    }

}
