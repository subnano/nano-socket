package net.nano.net.client;

import net.nano.net.socket.NioSocketChannelFactory;
import net.nano.net.socket.SocketChannelFactory;
import net.nano.net.socket.SocketConfig;

public class TcpClient extends AbstractNanoClient {

    private TcpClient(SocketConfig socketConfig, SocketChannelFactory socketChannelFactory) {
        super(socketConfig, socketChannelFactory);
    }

    public static TcpClient newInstance(SocketConfig socketConfig) {
        return new TcpClient(socketConfig, new NioSocketChannelFactory());
    }
}
