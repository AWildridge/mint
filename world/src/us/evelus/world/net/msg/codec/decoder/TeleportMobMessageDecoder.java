package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.TeleportMobMessage;

public final class TeleportMobMessageDecoder extends DatagramMessageDecoder<TeleportMobMessage> {

    public TeleportMobMessageDecoder() {
        super(MessageConstants.TELEPORT_CHAR_MSG);
    }

    @Override
    public TeleportMobMessage decode(ByteBuf buf) throws MalformedMessageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
