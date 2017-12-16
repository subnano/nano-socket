package net.nano.net.client;

import net.nano.net.channel.ChannelFactory;

public class NanoClientBuilder {

    private ChannelFactory channelFactory;

    public NanoClientBuilder channelFactory(ChannelFactory channelFactory) {
        this.channelFactory = channelFactory;
        return this;
    }
}
