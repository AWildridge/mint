package us.evelus.world.net;

import io.netty.buffer.ByteBuf;

public final class GameFrame {

    private final int id;
    private final ByteBuf buf;

    public GameFrame(int id, ByteBuf buf) {
        this.id = id;
        this.buf = buf;
    }

    public int getId() {
        return id;
    }

    public ByteBuf getPayload() {
        return buf;
    }
}
