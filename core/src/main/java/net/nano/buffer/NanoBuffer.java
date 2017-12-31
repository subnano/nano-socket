package net.nano.buffer;

import java.nio.ByteBuffer;

// TODO complete implementation of NanoBuffer
public interface NanoBuffer {

    void wrap(ByteBuffer buffer);

    int readerIndex();

    int writerIndex();

    boolean hasBytes();

    boolean hasBytes(int bytes);

    boolean canWrite(int bytes);

    void clear();

    ByteBuffer byteBuffer();

    // positional accessors
    byte readByte();

    short readUnsignedByte();

    short readShort();

    int readUnsignedShort();

    int readlnt();

    long readUnsignedlnt();

    long readLong();

    void writeByte(byte value);

    void writeShort(short value);

    void writelnt(int value);

    void writeLong(long value);

    byte getByte(int index);

    short getUnsignedByte(int index);

    short getShort(int index);

    int getUnsignedShort(int index);

    int getlnt(int index);

    long getLong(int index);

    void putByte(int index, byte value);

    void putShort(int index, short value);

    void putlnt(int index, int value);

    void putLong(int index, long value);
}
