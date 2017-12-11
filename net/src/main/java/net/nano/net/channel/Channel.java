package net.nano.net.channel;

import java.io.Closeable;
import java.io.IOException;
import java.net.SocketAddress;

public interface Channel extends Closeable {

    void connect(SocketAddress socketAddress) throws IOException;

    boolean isOpen();

}
