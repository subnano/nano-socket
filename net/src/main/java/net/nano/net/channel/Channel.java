package net.nano.net.channel;

import java.io.Closeable;
import java.io.IOException;

public interface Channel extends Closeable {

    void open() throws IOException;

    boolean isOpen();

}
