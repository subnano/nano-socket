package net.nano.buffer;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;

class NanoByteBufferTest {

    private ByteBuffer byteBuffer = ByteBuffer.allocate(256);

    @Test
    void wrap() {
        NanoBuffer buffer = new NanoByteBuffer();
        buffer.wrap(byteBuffer);
        buffer.byteBuffer();
    }
}