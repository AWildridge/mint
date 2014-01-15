package us.evelus.world.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.net.msg.GameMessage;
import us.evelus.world.net.msg.codec.*;

public final class GameMessageHandler extends SimpleChannelInboundHandler<GameMessage> {

    private static final Logger logger = LoggerFactory.getLogger(GameMessageHandler.class);
    private final CodecRepository repository;
    private ChannelHelper helper;

    public GameMessageHandler(CodecRepository repository) {
        this.repository = repository;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        helper = new ChannelHelper(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        helper.destroy();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GameMessage msg) throws Exception {

        String name = msg.getClass().getSimpleName();
        try {

            MessageHandler handler = repository.getHandler(msg);
            if(handler == null) {
                logger.info("No such handler registered [msg=" + name + "]");
                return;
            }

            handler.handle(helper, msg);
        } catch(RuntimeException ex) {
            logger.error("Exception caught while dispatching message [msg=" + name + "]", ex);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
