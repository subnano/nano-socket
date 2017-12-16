package net.nano.core.util;

import java.nio.ByteBuffer;

public class BufferUtil {

    private static final char[] HEX_CHAR_TABLE = new char[256 * 4];

    static {
        final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
        for (int i = 0; i < 256; i++) {
            HEX_CHAR_TABLE[i << 1] = HEX_CHARS[i >>> 4 & 0x0F];
            HEX_CHAR_TABLE[(i << 1) + 1] = HEX_CHARS[i & 0x0F];
        }
    }

    /**
     * Returns a hex dump     * of the given buffer's readable bytes.
     */
    public static String hexDump(ByteBuffer buffer) {
        // TODO avoid this object creation
        int readable = buffer.limit() - buffer.position();
        byte[] newBytes = new byte[readable];
        buffer.duplicate().get(newBytes, 0, readable);
        return hexDump(newBytes, buffer.position(), readable);
    }

    private static String hexDump(byte[] array, int fromIndex, int length) {
        if (length < 0) {
            throw new IllegalArgumentException("length: " + length);
        }
        if (length == 0) {
            return "";
        }

        int endIndex = fromIndex + length;
        char[] newChars = new char[length << 1];

        int srcIdx = fromIndex;
        int dstIdx = 0;
        for (; srcIdx < endIndex; srcIdx ++, dstIdx += 2) {
            System.arraycopy(
                    HEX_CHAR_TABLE, (array[srcIdx] & 0xFF) << 1,
                    newChars, dstIdx, 2);
        }
        return new String(newChars);
    }


}
