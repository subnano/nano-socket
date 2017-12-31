package net.nano.net.client;

import net.nano.net.channel.Channel;
import net.nano.net.channel.ChannelHandler;

import java.io.IOException;

class BlockingChannelReader implements Runnable {

    private ChannelHandler channelHandler;
    private final Channel channel;

    BlockingChannelReader(ChannelHandler channelHandler, Channel channel) {
        this.channelHandler = channelHandler;
        this.channel = channel;
    }

    public void start() {
        Thread thread = new Thread(this);
        thread.setName(BlockingChannelReader.class.getSimpleName());
        thread.start();;
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
        channelHandler.disconnected(channel);
    }
}
