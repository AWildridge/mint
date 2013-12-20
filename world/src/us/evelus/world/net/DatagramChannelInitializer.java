package us.evelus.world.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.DatagramChannel;
import us.evelus.world.net.msg.codec.CodecRepository;

public final class DatagramChannelInitializer extends ChannelInitializer<DatagramChannel> {

    private final CodecRepository repository;

    public DatagramChannelInitializer(CodecRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void initChannel(DatagramChannel ch) throws Exception {
        ch.pipeline().addLast(new InboundDatagramMessageHandler(repository));
    }
}
