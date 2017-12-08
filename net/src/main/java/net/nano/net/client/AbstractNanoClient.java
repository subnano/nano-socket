package net.nano.net.client;

import net.nano.net.socket.SocketChannelFactory;
import net.nano.net.socket.SocketConfig;
import net.nano.net.socket.SocketUtil;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class AbstractNanoClient implements NanoClient {

    private final SocketConfig socketConfig;
    private final SocketChannelFactory socketChannelFactory;

    public AbstractNanoClient(SocketConfig socketConfig, SocketChannelFactory socketChannelFactory) {
        this.socketConfig = socketConfig;
        this.socketChannelFactory = socketChannelFactory;
    }

    public void connect() {
        try {
            SocketChannel channel = socketChannelFactory.createSocketChannel();
            SocketAddress socketAddress = SocketUtil.getSocketAddress(socketConfig);
            channel.connect(socketAddress);
            connectionOpened();
        } catch (IOException e) {
            connectionFailed(e);
        }
    }

    private void connectionOpened() {
        // TODO notify channel handlers
    }

    private void connectionFailed(Throwable cause) {
        // TODO notify channel handlers
    }

}
