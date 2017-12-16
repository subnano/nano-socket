package net.nano.net.client;

import net.nano.net.channel.BaseChannelHandler;
import net.nano.net.channel.Channel;
import net.nano.net.channel.ChannelHandler;
import net.nano.net.channel.NanoSocketChannel;
import net.nano.net.socket.NioSocketChannelFactory;
import net.nano.net.socket.SocketChannelFactory;
import net.nano.net.socket.SocketConfig;
import net.nano.net.socket.SocketUtil;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.util.concurrent.Executors;

public class NanoSocketClient implements NanoClient {

    private final SocketConfig socketConfig;
    private final SocketChannelFactory socketChannelFactory;

    private Channel channel;
    private ChannelHandler channelHandler;

    public NanoSocketClient(SocketConfig socketConfig) {
        this(socketConfig, new NioSocketChannelFactory(), new BaseChannelHandler());
    }

    public NanoSocketClient(SocketConfig config, ChannelHandler channelHandler) {
        this(config, new NioSocketChannelFactory(), channelHandler);
    }

    private NanoSocketClient(SocketConfig socketConfig,
                             SocketChannelFactory socketChannelFactory,
                             ChannelHandler channelHandler) {
        this.socketConfig = socketConfig;
        this.socketChannelFactory = socketChannelFactory;
        this.channelHandler = new NanoSocketClientHandler(channelHandler);
    }

    @Override
    public void connect() {
        try {
            this.channel = new NanoSocketChannel(socketChannelFactory.createSocketChannel());
            SocketAddress socketAddress = SocketUtil.getSocketAddress(socketConfig);
            boolean connected = channel.connect(socketAddress);
            if (connected) {
                channelHandler.opened(channel);
            } else {
                channelHandler.openFailed(channel, null);
            }
        } catch (IOException e) {
            channelHandler.openFailed(channel, e);
        }
    }

    @Override
    public void disconnect() {
        if (channel != null) {
            try {
                channel.close();
                channelHandler.closed(channel);
            } catch (IOException e) {
                channelHandler.closeFailed(channel, e);
            }
        }
    }

    private void startReaderThread(Channel channel) {
        // TODO proper thread name
        Executors.newSingleThreadExecutor().execute(new BlockingChannelReader(channel));
    }

    private class NanoSocketClientHandler implements ChannelHandler {

        private final ChannelHandler delegate;

        public NanoSocketClientHandler(ChannelHandler delegate) {
            this.delegate = delegate;
        }

        @Override
        public void opened(Channel channel) {
            // somewhere start a reader thread or create a worker
            startReaderThread(channel);
            delegate.opened(channel);
        }

        @Override
        public void openFailed(Channel channel, Throwable cause) {
            delegate.openFailed(channel, cause);
        }

        @Override
        public void closed(Channel channel) {
            delegate.closed(channel);
        }

        @Override
        public void closeFailed(Channel channel, Throwable cause) {
            delegate.closeFailed(channel, cause);
        }

        @Override
        public void dataReceived(Channel channel, ByteBuffer buffer) {
            delegate.dataReceived(channel, buffer);
        }

        @Override
        public void dataReadFailed(Channel channel, Throwable cause) {
            delegate.dataReadFailed(channel, cause);
        }
    }

    private class BlockingChannelReader implements Runnable {

        private final Channel channel;

        public BlockingChannelReader(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            while (channel.isConnected()) {
                try {
                    int bytesRead = channel.read();
                    if (bytesRead > 0) {
                        channelHandler.dataReceived(channel, channel.inboundBuffer());
                    }
                } catch (IOException e) {
                    channelHandler.dataReadFailed(channel, e);
                }
            }
        }
    }
}
