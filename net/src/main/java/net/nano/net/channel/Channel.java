package net.nano.net.channel;

import net.nano.net.reactor.Reactor;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public interface Channel {

    void connect(SocketAddress socketAddress) throws IOException;

    void disconnect() throws IOException;

    void register(Reactor reactor);

    boolean isOpen();

    boolean isConnected();

    ByteBuffer inboundBuffer();

    ByteBuffer outboundBuffer();

    int write() throws IOException;

    int read() throws IOException;

    SocketAddress localAddress() throws IOException;

    SocketAddress remoteAddress() throws IOException;

    enum State {
        CONNECTED,
        DISCONNECTED
    }
}
