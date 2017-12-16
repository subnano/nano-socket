package net.nano.net.channel;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

// TODO add support for blocking AND non-blocking operation
public class NanoSocketChannel implements Channel {

    private final SocketChannel socketChannel;

    // TODO need to pass in buffer or allocator
    private ByteBuffer inboundBuffer = ByteBuffer.allocateDirect(512).order(ByteOrder.nativeOrder());
    private ByteBuffer outboundBuffer = ByteBuffer.allocateDirect(512).order(ByteOrder.nativeOrder());

    public NanoSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    @Override
    public boolean connect(SocketAddress socketAddress) throws IOException {
        setOptions();
        // TODO if interface specified then bind first
        socketChannel.configureBlocking(false);
        boolean connected = socketChannel.connect(socketAddress);
        if (!connected) {
            // TODO convert to non blocking selectors
            connected = socketChannel.finishConnect();
        }
        return connected;
    }

    // not sure yet where to put this
    private void setOptions() throws IOException {
        socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, false);
        socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
    }

    @Override
    public boolean isOpen() {
        return socketChannel.isOpen();
    }

    @Override
    public boolean isConnected() {
        return socketChannel.isConnected();
    }

    @Override
    public void close() throws IOException {
        socketChannel.close();
    }

    @Override
    public ByteBuffer inboundBuffer() {
        return inboundBuffer;
    }

    @Override
    public ByteBuffer outboundBuffer() {
        return outboundBuffer;
    }

    public int read() throws IOException {
        return socketChannel.read(inboundBuffer);
    }

    @Override
    public void write() throws IOException {
        int bytesWritten = socketChannel.write(outboundBuffer);
    }

    @Override
    public SocketAddress localAddress() throws IOException {
        return socketChannel.getLocalAddress();
    }

    @Override
    public SocketAddress remoteAddress() throws IOException {
        return socketChannel.getRemoteAddress();
    }


    @Override
    public String toString() {
        String address;
        try {
            address = remoteAddress().toString();
        } catch (IOException e) {
            address = "(unknown)";
        }
        return "[" + address + "]";
    }
}
