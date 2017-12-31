package net.nano.net.client;

import net.nano.net.channel.Channel;
import net.nano.net.channel.ChannelFactory;
import net.nano.net.channel.ChannelHandler;
import net.nano.net.reactor.Reactor;
import net.nano.net.reactor.ReactorFactories;
import net.nano.net.socket.SocketConfig;
import net.nano.net.socket.SocketUtil;

import java.io.IOException;
import java.net.SocketAddress;

/**
 * This class is not intended to support concurrent use so any interaction should originate from the same thread.
 */
public class NanoSocketClient implements NanoClient {

    private final SocketConfig socketConfig;
    private final ChannelFactory channelFactory;
    private final ChannelHandler channelHandler;

    private Channel channel;
    private Reactor reactor;

    NanoSocketClient(SocketConfig socketConfig,
                     ChannelFactory channelFactory,
                     ChannelHandler channelHandler) {
        this.socketConfig = socketConfig;
        this.channelFactory = channelFactory;
        this.channelHandler = channelHandler;
    }

    @Override
    public void connect() {
        try {
            channel = channelFactory.newChannel();

            reactor = ReactorFactories.defaultFactory().newReactor(
                    channel,
                    channelHandler
            );
            reactor.start();

            // create address and connect
            SocketAddress socketAddress = SocketUtil.getSocketAddress(socketConfig);
            channel.connect(socketAddress);
        } catch (IOException e) {
            channelHandler.connectFailed(channel, e);
        }
    }

    @Override
    public void disconnect() {
        if (channel != null) {
            try {
                channel.disconnect();
            } catch (IOException e) {
                channelHandler.disconnectFailed(channel, e);
            }
        }
    }
}
