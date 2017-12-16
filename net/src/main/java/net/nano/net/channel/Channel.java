package net.nano.net.channel;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;

public interface Channel extends Closeable {

    boolean connect(SocketAddress socketAddress) throws IOException;

    boolean isOpen();

    boolean isConnected();

    ByteBuffer inboundBuffer();

    ByteBuffer outboundBuffer();

    void write() throws IOException;

    int read() throws IOException;

    SocketAddress localAddress() throws IOException;

    SocketAddress remoteAddress() throws IOException;
}
