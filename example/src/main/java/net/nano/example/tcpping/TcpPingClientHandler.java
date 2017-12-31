package net.nano.example.tcpping;

import net.nano.net.channel.Channel;
import net.nano.net.channel.LoggingChannelHandler;
import org.HdrHistogram.Histogram;

import java.io.IOException;
import java.nio.ByteBuffer;

class TcpPingClientHandler extends LoggingChannelHandler {

    private static final int WARMUP_COUNT = 10_000;
    private static final int DEFAULT_PACKET_SIZE = 256;

    private final int packetSize = DEFAULT_PACKET_SIZE;

    private static final Histogram HISTO = new Histogram(3600000000000L, 3);
    private final int packetCount;

    TcpPingClientHandler(int packetCount) {
        this.packetCount = packetCount;
    }

    @Override
    public void connected(Channel channel) {
        super.connected(channel);
        sendPacket(channel, 0);
    }

    @Override
    public void dataReceived(Channel channel, ByteBuffer buffer) {
        buffer.flip();
        int seqNum = buffer.getInt();
        long timeSent = buffer.getLong();
        buffer.clear();

        if (recordMetric(seqNum, timeSent)) {
            sendPacket(channel, ++seqNum);
        } else {
            shutdown(channel);
        }
    }

    @Override
    public void disconnected(Channel channel) {
        super.disconnected(channel);
        System.out.println("Recorded latencies [in usec]:");
        HISTO.outputPercentileDistribution(System.out, 1000.0);
    }

    private boolean recordMetric(int seqNum, long timeSent) {
        if (seqNum >= WARMUP_COUNT) {
            if (seqNum > WARMUP_COUNT) {
                if (seqNum > packetCount + WARMUP_COUNT) {
                    return false;
                }
                HISTO.recordValue(System.nanoTime() - timeSent);
            } else {
                System.out.println("warmup complete, begin recording metrics ..");
            }
        }
        return true;
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

    private void shutdown(Channel channel) {
        System.out.println("benchmark complete, closing ..");
        try {
            channel.disconnect();
        } catch (IOException e) { // TODO must be cleaner
            e.printStackTrace();
        }
    }

}
