package us.evelus.world.net.msg.codec.handler;

import us.evelus.world.model.World;
import us.evelus.world.net.Sender;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.ClockTickMessage;

public final class ClockTickMessageHandler extends DatagramMessageHandler<ClockTickMessage> {

    private final World world;

    public ClockTickMessageHandler(World world) {
        super(ClockTickMessage.class);
        this.world = world;
    }

    @Override
    public void handle(Sender sender, ClockTickMessage msg) {
        world.tick();
    }
}
