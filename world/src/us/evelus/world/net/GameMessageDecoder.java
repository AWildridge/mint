package us.evelus.world.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.net.msg.codec.CodecRepository;
import us.evelus.world.net.msg.codec.MessageDecoder;

import java.util.List;

public final class GameMessageDecoder extends MessageToMessageDecoder<GameFrame> {

    private static final Logger logger = LoggerFactory.getLogger(GameMessageDecoder.class);
    private final CodecRepository repository;

    public GameMessageDecoder(CodecRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, GameFrame frame, List<Object> list) throws Exception {
        MessageDecoder<?> decoder = repository.getDecoder(frame.getId());
        if(decoder == null) {
            throw new RuntimeException();
        }
        list.add(decoder.decode(frame.getPayload()));
    }
}
