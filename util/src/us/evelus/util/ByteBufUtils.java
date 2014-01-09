package us.evelus.util;

import io.netty.buffer.ByteBuf;

public final class ByteBufUtils {

    public static String readString(ByteBuf buffer) {
        return readString(buffer, '\0');
    }

    public static String readJString(ByteBuf buffer) {
        return readString(buffer, '\n');
    }

    private static String readString(ByteBuf buffer, char delimiter) {
        buffer.markReaderIndex();

        int len = 0;
        while (buffer.readByte() != delimiter) {
            len++;
        }

        buffer.resetReaderIndex();

        byte[] bytes = new byte[len];
        buffer.readBytes(bytes);
        buffer.readerIndex(buffer.readerIndex() + 1);
        return new String(bytes);
    }

    private ByteBufUtils() {}
}
