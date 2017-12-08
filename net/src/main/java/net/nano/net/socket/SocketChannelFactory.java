package net.nano.net.socket;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public interface SocketChannelFactory {

    SocketChannel createSocketChannel() throws IOException;

    ServerSocketChannel createServerSocketChannel() throws IOException;

}
