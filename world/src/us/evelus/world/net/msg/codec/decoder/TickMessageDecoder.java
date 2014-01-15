package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.GameMessage;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.MessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.TickMessage;

public final class TickMessageDecoder extends MessageDecoder {

    public TickMessageDecoder() {
        super(MessageConstants.CLOCK_TICK_MSG);
    }

    @Override
    public GameMessage decode(ByteBuf buf) throws MalformedMessageException {
        TickMessage message = new TickMessage();
        return message;
    }
}
