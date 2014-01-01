package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.MobWaypointMessage;

public class MobWaypointMessageDecoder extends DatagramMessageDecoder<MobWaypointMessage> {

    public MobWaypointMessageDecoder() {
        super(MessageConstants.WAYPOINT_MOB_MSG);
    }

    @Override
    public MobWaypointMessage decode(ByteBuf buf) throws MalformedMessageException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
