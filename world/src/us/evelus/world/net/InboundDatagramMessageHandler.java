package us.evelus.world.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.net.msg.DatagramMessage;
import us.evelus.world.net.msg.codec.CodecRepository;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;

import java.io.IOException;

public final class InboundDatagramMessageHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final Logger logger = LoggerFactory.getLogger(InboundDatagramMessageHandler.class);
    private final CodecRepository repository;

    public InboundDatagramMessageHandler(CodecRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        ByteBuf buf = packet.content();

        /* Ignore packets that contain no data */
        if(!buf.isReadable()) {
            return;
        }

        int id = buf.readByte() & 0xff;

        DatagramMessageDecoder decoder = repository.getDecoder(id);
        if(decoder == null) {
            logger.info("No such decoder registered [msg=" + id + "]");
            return;
        }

        DatagramMessageSender sender = new DatagramMessageSender(packet.sender(), ctx.channel());

        try {

            DatagramMessage message = decoder.decode(packet.content());
            if(message == null) {
                return;
            }

            DatagramMessageHandler handler = repository.getHandler(message);
            if(handler == null) {
                logger.info("No such handler registered [msg=" + id + "]");
                return;
            }

            handler.handle(sender, message);
        } catch(IOException | RuntimeException ex) {
            logger.error("Exception caught while dispatching message [msg=" + id + "]", ex);
        } finally {
            sender.destroy();
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
