package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.DatagramMessage;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.TickMessage;

public final class TickMessageDecoder extends DatagramMessageDecoder {

    public TickMessageDecoder() {
        super(MessageConstants.CLOCK_TICK_MSG);
    }

    @Override
    public DatagramMessage decode(ByteBuf buf) throws MalformedMessageException {
        TickMessage message = new TickMessage();
        return message;
    }
}
