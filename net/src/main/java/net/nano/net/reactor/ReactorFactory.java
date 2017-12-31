package net.nano.net.reactor;

import net.nano.net.channel.Channel;
import net.nano.net.channel.ChannelHandler;

public interface ReactorFactory {
    Reactor newReactor(Channel channel, ChannelHandler channelHandler);
}
