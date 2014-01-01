package us.evelus.world.net.msg.codec.handler;

import us.evelus.world.model.World;
import us.evelus.world.net.DatagramMessageSender;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.TickMessage;

public final class TickMessageHandler extends DatagramMessageHandler<TickMessage> {

    private final World world;

    public TickMessageHandler(World world) {
        super(TickMessage.class);
        this.world = world;
    }

    @Override
    public void handle(DatagramMessageSender sender, TickMessage msg) {
        world.tick();
    }
}
