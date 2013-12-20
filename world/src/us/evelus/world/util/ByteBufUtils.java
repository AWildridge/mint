package us.evelus.world.util;

import io.netty.buffer.ByteBuf;

public final class ByteBufUtils {

    public static String readString(ByteBuf buffer) {
        buffer.markReaderIndex();

        int len = 0;
        while (buffer.readByte() != '\0') {
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
