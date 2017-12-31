package net.nano.net.channel.nio;

import net.nano.net.channel.Channel;
import net.nano.net.channel.ChannelFactory;
import net.nano.net.channel.NanoSocketChannel;

import java.io.IOException;

public class NioChannelFactory implements ChannelFactory {

    private final SocketChannelFactory socketChannelFactory;

    public NioChannelFactory() {
        this(new NioSocketChannelFactory());
    }

    public NioChannelFactory(SocketChannelFactory socketChannelFactory) {
        this.socketChannelFactory = socketChannelFactory;
    }

    @Override
    public Channel newChannel() throws IOException {
        return new NanoSocketChannel(socketChannelFactory.createSocketChannel());
    }

}
