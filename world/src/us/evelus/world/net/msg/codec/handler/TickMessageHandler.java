package us.evelus.world.net.msg.codec.handler;

import us.evelus.world.model.World;
import us.evelus.world.net.ChannelHelper;
import us.evelus.world.net.msg.codec.MessageHandler;
import us.evelus.world.net.msg.impl.TickMessage;

public final class TickMessageHandler extends MessageHandler<TickMessage> {

    private final World world;

    public TickMessageHandler(World world) {
        super(TickMessage.class);
        this.world = world;
    }

    @Override
    public void handle(ChannelHelper sender, TickMessage msg) {
        world.tick();
    }
}
