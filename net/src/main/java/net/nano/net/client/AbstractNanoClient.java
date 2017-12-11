package net.nano.net.client;

import net.nano.net.channel.Channel;
import net.nano.net.socket.SocketChannelFactory;
import net.nano.net.socket.SocketConfig;
import net.nano.net.socket.SocketUtil;

import java.io.IOException;
import java.net.SocketAddress;

public class AbstractNanoClient implements NanoClient {

    private final SocketConfig socketConfig;
    private final SocketChannelFactory socketChannelFactory;

    private Channel channel;

    public AbstractNanoClient(SocketConfig socketConfig, SocketChannelFactory socketChannelFactory) {
        this.socketConfig = socketConfig;
        this.socketChannelFactory = socketChannelFactory;
    }

    @Override
    public void connect() {
        try {
            this.channel = socketChannelFactory.createSocketChannel();
            SocketAddress socketAddress = SocketUtil.getSocketAddress(socketConfig);
            channel.connect(socketAddress);
            channelOpened();
        } catch (IOException e) {
            channelOpenFailed(e);
        }
    }

    @Override
    public void disconnect() {
        if (channel != null) {
            try {
                channel.close();
                channelClosed();
            } catch (IOException e) {
                channelCloseFailed(e);
            }
        }
    }

    private void channelOpened() {
        // TODO notify channel handlers
    }

    private void channelOpenFailed(Throwable cause) {
        // TODO notify channel handlers
    }

    private void channelClosed() {
        // TODO notify channel handlers
    }

    private void channelCloseFailed(Throwable cause) {
        // TODO notify channel handlers
    }

}
