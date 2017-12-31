package net.nano.example.tcpping;

import net.nano.net.channel.nio.NioChannelFactory;
import net.nano.net.client.NanoClient;
import net.nano.net.socket.SocketConfig;

public class TcpPingClient {

    public static void main(String[] args) {
        SocketConfig config = SocketConfig.newBuilder().hostname("localhost").port(8007).build();
        NanoClient client = NanoClient.builder()
                .channelFactory(new NioChannelFactory())
                .config(config)
//                .handler(new LoggingChannelHandler())
                .handler(new TcpPingClientHandler(20_000))
                .newClient();
        client.connect();
    }

}
