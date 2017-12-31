package net.nano.net.client;

import net.nano.net.channel.ChannelFactory;
import net.nano.net.channel.ChannelHandler;
import net.nano.net.channel.nio.NioChannelFactory;
import net.nano.net.socket.SocketConfig;

public class NanoClientBuilder {

    private ChannelFactory channelFactory;
    private SocketConfig config;
    private ChannelHandler handler;

    NanoClientBuilder() {
        this.channelFactory = new NioChannelFactory();
    }

    public NanoClientBuilder channelFactory(ChannelFactory channelFactory) {
        this.channelFactory = channelFactory;
        return this;
    }

    public NanoClientBuilder config(SocketConfig config) {
        this.config = config;
        return this;
    }

    public NanoClientBuilder handler(ChannelHandler handler) {
        this.handler = handler;
        return this;
    }

    public NanoClient newClient() {
        return new NanoSocketClient(
                config,
                channelFactory,
                handler
        );
    }
}
