package net.nano.net.channel;

import net.nano.net.reactor.Reactor;

import java.io.IOException;
import java.net.SocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SocketChannel;

public class NanoSocketChannel extends AbstractNanoChannel implements Channel {

    private final SocketChannel socketChannel;

    // TODO need to pass in NanoBuffer or allocator
    private ByteBuffer inboundBuffer = ByteBuffer.allocateDirect(512).order(ByteOrder.nativeOrder());
    private ByteBuffer outboundBuffer = ByteBuffer.allocateDirect(512).order(ByteOrder.nativeOrder());

    public NanoSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
        initChannel(socketChannel);
    }

    private void initChannel(SocketChannel socketChannel) {
        try {
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            throw new ChannelException("Cannot configure non-blocking mode.", e);
        }

        // TODO not sure yet where to set socket options
        try {
            socketChannel.setOption(StandardSocketOptions.SO_KEEPALIVE, false);
            socketChannel.setOption(StandardSocketOptions.SO_REUSEADDR, true);
            socketChannel.setOption(StandardSocketOptions.TCP_NODELAY, true);
        } catch (Exception e) {
            throw new ChannelException("Cannot set socket options.", e);
        }
    }

    @Override
    public final void connect(SocketAddress socketAddress) throws IOException {
        // TODO bind to network interface if specified
        socketChannel.connect(socketAddress);
    }

    @Override
    public final void register(Reactor reactor) {
        if (reactor == null) {
            throw new NullPointerException("reactor");
        }
        // TODO notify channel registered?
    }

    @Override
    public final boolean isOpen() {
        return socketChannel.isOpen();
    }

    @Override
    public final boolean isConnected() {
        return socketChannel.isConnected();
    }

    @Override
    public final void disconnect() throws IOException {
        socketChannel.close();
    }

    @Override
    public final ByteBuffer inboundBuffer() {
        return inboundBuffer;
    }

    @Override
    public final ByteBuffer outboundBuffer() {
        return outboundBuffer;
    }

    public final int read() throws IOException {
        // TODO update NanoBuffer indexes when reading into the ByteBuffer
        return socketChannel.read(inboundBuffer);
    }

    @Override
    public final int write() throws IOException {
        int bytesWritten = socketChannel.write(outboundBuffer);
        return bytesWritten;
    }

    @Override
    public final SocketAddress localAddress() throws IOException {
        return socketChannel.getLocalAddress();
    }

    @Override
    public final SocketAddress remoteAddress() throws IOException {
        return socketChannel.getRemoteAddress();
    }

    public boolean isConnectionPending() {
        return socketChannel.isConnectionPending();
    }

    public boolean finishConnect() throws IOException {
        return socketChannel.finishConnect();
    }

    @Override
    public final String toString() {
        // TODO create proper toString (@ctor)
        String address;
        try {
            address = remoteAddress().toString();
        } catch (IOException e) {
            address = "(unknown)";
        }
        return "[" + address + "]";
    }

}
