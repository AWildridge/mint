package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.SpawnObserverMessage;

public final class SpawnObserverMessageDecoder extends DatagramMessageDecoder<SpawnObserverMessage> {

    public SpawnObserverMessageDecoder() {
        super(MessageConstants.SPAWN_OBSERVER_MSG);
    }

    @Override
    public SpawnObserverMessage decode(ByteBuf buf) throws MalformedMessageException {
        int flag     = buf.readInt();
        int location = buf.readInt();
        long uid     = buf.readLong();

        SpawnObserverMessage msg = new SpawnObserverMessage(flag, location);
        msg.setUid(uid);
        return msg;
    }
}
