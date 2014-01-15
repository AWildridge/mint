package us.evelus.world.net;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import us.evelus.world.net.msg.codec.CodecRepository;

public final class GameChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final CodecRepository repository;

    public GameChannelInitializer(CodecRepository repo) {
        repository = repo;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast("frame-decoder", new FrameDecoder())
                .addLast("message-decoder", new GameMessageDecoder(repository))
                .addLast("message-handler", new GameMessageHandler(repository));
    }
}
