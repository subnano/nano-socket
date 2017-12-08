package net.nano.net.socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;

public final class SocketUtil {

    public static SocketAddress getSocketAddress(SocketConfig socketConfig) throws UnknownHostException {
        InetAddress netAddress = InetAddress.getByName(socketConfig.hostname());
        SocketAddress socketAddress = new InetSocketAddress(netAddress, socketConfig.port());
        return socketAddress;
    }
}
