package net.nano.net.client;

public class TcpClient extends AbstractNanoClient {


    public TcpClient defaultInstance() {
        this(socketConfig, socketChannelFactory);
    }
}
