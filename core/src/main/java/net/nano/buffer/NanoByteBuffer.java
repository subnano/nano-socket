package net.nano.buffer;

import java.nio.ByteBuffer;

public class NanoByteBuffer implements NanoBuffer {

    private ByteBuffer byteBuffer;
    private int capacity;
    private int readerIndex;
    private int writerIndex;

    @Override
    public void wrap(final ByteBuffer buffer) {
        if (byteBuffer != null && byteBuffer.isDirect()) {
            // manually release direct buffer or let the gc do the work?
            byteBuffer = null;
        }
        byteBuffer = buffer;
        capacity = buffer.capacity();
        readerIndex = buffer.position();
        writerIndex = buffer.limit();
    }

    @Override
    public int readerIndex() {
        return readerIndex;
    }

    @Override
    public int writerIndex() {
        return writerIndex;
    }

    @Override
    public boolean hasBytes() {
        return writerIndex > readerIndex;
    }

    @Override
    public boolean hasBytes(int bytes) {
        return writerIndex - readerIndex >= bytes;
    }

    @Override
    public boolean canWrite(int bytes) {
        return capacity - writerIndex >= bytes;
    }

    @Override
    public void clear() {
        readerIndex = writerIndex = 0;
        byteBuffer.clear();
    }

    @Override
    public ByteBuffer byteBuffer() {
        return byteBuffer;
    }

    // positional accessors
    @Override
    public byte readByte() {
        return byteBuffer.get(readerIndex++);
    }

    @Override
    public short readUnsignedByte() {
        return getUnsignedByte(readerIndex++);
    }

    @Override
    public short readShort() {
        short value = getShort(readerIndex);
        readerIndex += 2;
        return value;
    }

    @Override
    public int readUnsignedShort() {
        int value = getUnsignedShort(readerIndex);
        readerIndex += 2;
        return value;
    }

    @Override
    public int readlnt() {
        int value = getlnt(readerIndex);
        readerIndex += 4;
        return value;
    }

    @Override
    public long readUnsignedlnt() {
        long value = getlnt(readerIndex) & 0xFFFFFFFFL;
        readerIndex += 4;
        return value;
    }

    @Override
    public long readLong() {
        long value = getLong(readerIndex);
        readerIndex += 8;
        return value;
    }

    // positional mutators

    @Override
    public void writeByte(byte value) {
        putByte(writerIndex++, value);
    }

    @Override
    public void writeShort(short value) {
        putShort(writerIndex, value);
        writerIndex += 2;
    }

    @Override
    public void writelnt(int value) {
        putlnt(writerIndex++, value);
        writerIndex += 4;
    }

    @Override
    public void writeLong(long value) {
        putLong(writerIndex, value);
        writerIndex += 8;
    }

    // index specific accessors

    @Override
    public byte getByte(int index) {
        return byteBuffer.get(index);
    }

    @Override
    public short getUnsignedByte(int index) {
        return (short) (getByte(index) & 0xFF);
    }

    @Override
    public short getShort(int index) {
        return byteBuffer.getShort(index);
    }

    @Override
    public int getUnsignedShort(int index) {
        return getShort(index) & 0xFFFF;
    }

    @Override
    public int getlnt(int index) {
        return byteBuffer.getInt(index);
    }

    @Override
    public long getLong(int index) {
        return byteBuffer.getLong(index);
    }

    // index specific mutators

    @Override
    public void putByte(int index, byte value) {
        byteBuffer.put(index, value);
    }

    @Override

    public void putShort(int index, short value) {
        byteBuffer.putShort(index, value);
    }

    @Override
    public void putlnt(int index, int value) {
        byteBuffer.putInt(index, value);
    }

    @Override
    public void putLong(int index, long value) {
        byteBuffer.putLong(index, value);
    }
}
