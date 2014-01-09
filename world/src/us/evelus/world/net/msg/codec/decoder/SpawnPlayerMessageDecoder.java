package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.model.Position;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.SpawnPlayerMessage;

public final class SpawnPlayerMessageDecoder extends DatagramMessageDecoder<SpawnPlayerMessage> {

    public SpawnPlayerMessageDecoder() {
        super(MessageConstants.SPAWN_PLAYER_MSG);
    }

    @Override
    public SpawnPlayerMessage decode(ByteBuf buf) throws MalformedMessageException {
        Position position = Position.fromHash30(buf.readInt());
        long uid = buf.readLong();

        SpawnPlayerMessage message = new SpawnPlayerMessage(position);
        message.setUid(uid);

        return message;
    }
}
