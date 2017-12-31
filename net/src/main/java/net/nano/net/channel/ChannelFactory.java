package net.nano.net.channel;

import java.io.IOException;

public interface ChannelFactory {
    Channel newChannel() throws IOException;
}
