package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.DatagramMessage;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.ClockTickMessage;

public final class ClockTickMessageDecoder extends DatagramMessageDecoder {

    public ClockTickMessageDecoder() {
        super(MessageConstants.CLOCK_TICK_MSG);
    }

    @Override
    public DatagramMessage decode(ByteBuf buf) throws MalformedMessageException {
        ClockTickMessage message = new ClockTickMessage();
        return message;
    }
}
