package us.evelus.world.net.msg.codec;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.DatagramMessage;

public abstract class DatagramMessageDecoder<T extends DatagramMessage> {

    private final int id;

    public DatagramMessageDecoder(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract T decode(ByteBuf buf) throws MalformedMessageException;
}
