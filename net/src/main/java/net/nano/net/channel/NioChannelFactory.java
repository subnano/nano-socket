package net.nano.net.channel;

import net.nano.net.socket.SocketChannelFactory;

import java.io.IOException;

public class NioChannelFactory implements ChannelFactory {

    private final SocketChannelFactory socketChannelFactory;

    public NioChannelFactory(SocketChannelFactory socketChannelFactory) {
        this.socketChannelFactory = socketChannelFactory;
    }

    public Channel newChannel() throws IOException {
        return new NanoSocketChannel(socketChannelFactory.createSocketChannel());
    }
}
