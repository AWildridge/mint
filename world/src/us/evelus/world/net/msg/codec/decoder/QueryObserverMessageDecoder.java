package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.DatagramMessage;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.QueryObserverMessage;

public final class QueryObserverMessageDecoder extends DatagramMessageDecoder<QueryObserverMessage> {

    public QueryObserverMessageDecoder() {
        super(MessageConstants.QUERY_OBSERVER_MSG);
    }

    @Override
    public QueryObserverMessage decode(ByteBuf buf) throws MalformedMessageException {
        int id = buf.readShort();
        long uid = buf.readLong();

        QueryObserverMessage msg = new QueryObserverMessage(id);
        msg.setUid(uid);
        return msg;
    }
}
