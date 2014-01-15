package us.evelus.world.net.msg.codec;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.GameMessage;

public abstract class MessageDecoder<T extends GameMessage> {

    private final int id;

    public MessageDecoder(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract T decode(ByteBuf buf) throws MalformedMessageException;
}
