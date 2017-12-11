package net.nano.net.client;

import net.nano.net.socket.SocketConfig;

class TcpClientExample {

    public static void main(String[] args) {
        SocketConfig config = SocketConfig.newBuilder().hostname("localhost").port(2400).build();
        TcpClient client = TcpClient.newInstance(config);
        client.connect()
        .onConnect(TcpClientExample::onConnect)
        .onOpenFailed();
        // TODO do stuff here
        client.disconnect();
    }
}