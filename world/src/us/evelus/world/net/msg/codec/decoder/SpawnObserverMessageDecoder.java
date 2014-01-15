package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.MessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.SpawnObserverMessage;

public final class SpawnObserverMessageDecoder extends MessageDecoder<SpawnObserverMessage> {

    public SpawnObserverMessageDecoder() {
        super(MessageConstants.SPAWN_OBSERVER_MSG);
    }

    @Override
    public SpawnObserverMessage decode(ByteBuf buf) throws MalformedMessageException {
        int flag     = buf.readByte();
        int hash     = buf.readInt();
        long uid     = buf.readLong();

        SpawnObserverMessage msg = new SpawnObserverMessage(flag, hash);
        msg.setUid(uid);
        return msg;
    }
}
