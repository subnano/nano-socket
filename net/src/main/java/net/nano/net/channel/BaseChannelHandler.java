package net.nano.net.channel;

import java.nio.ByteBuffer;

public class BaseChannelHandler implements ChannelHandler {

    @Override
    public void opened(Channel channel) {
        // NO-OP
    }

    @Override
    public void openFailed(Channel channel, Throwable cause) {
        // NO-OP
    }

    @Override
    public void closed(Channel channel) {
        // NO-OP
    }

    @Override
    public void closeFailed(Channel channel, Throwable cause) {
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
