package net.nano.example.tcpping;

import net.nano.net.channel.Channel;
import net.nano.net.channel.LoggingChannelHandler;
import net.nano.net.client.NanoSocketClient;
import net.nano.net.socket.SocketConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class TcpPingClient extends LoggingChannelHandler {

    private static final int DEFAULT_PACKET_SIZE = 256;
    private final int packetSize = DEFAULT_PACKET_SIZE;

    @Override
    public void opened(Channel channel) {
        super.opened(channel);
        sendPacket(channel, 0);
    }

    @Override
    public void dataReceived(Channel channel, ByteBuffer buffer) {
        buffer.flip();
        int seqNum = buffer.getInt();
        long timeSent = buffer.getLong();
        buffer.clear();
        if (seqNum % 10 == 0) {
            System.out.println(
                    String.format("Received seq=%d rtt=%d us", seqNum,
                            TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - timeSent)));
        }
        if (seqNum >= 100) {
            close(channel);
        } else {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sendPacket(channel, ++seqNum);
        }
    }

    private void close(Channel channel) {
        try {
            channel.close();
        } catch (IOException e) { // TODO must be cleaner
            e.printStackTrace();
        }
    }

    private void sendPacket(Channel channel, int seqNum) {
        ByteBuffer buffer = channel.outboundBuffer();
        buffer.clear();
        buffer.putInt(seqNum);
        buffer.putLong(System.nanoTime());
        buffer.position(packetSize);
        buffer.flip();
        try {
            channel.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketConfig config = SocketConfig.newBuilder().hostname("localhost").port(8080).build();
        NanoSocketClient client = new NanoSocketClient(config, new TcpPingClient());
        client.connect();
    }

//        NanoSocketClient client = NanoClient.newBuilder()
//                .channelFactory()
//                .config()
//                .newClient();

}
