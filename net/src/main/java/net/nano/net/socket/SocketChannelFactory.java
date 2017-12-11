package net.nano.net.socket;

import net.nano.net.channel.Channel;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

public interface SocketChannelFactory {

    Channel createSocketChannel() throws IOException;

    ServerSocketChannel createServerSocketChannel() throws IOException;

}
