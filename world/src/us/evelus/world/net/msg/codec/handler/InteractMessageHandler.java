package us.evelus.world.net.msg.codec.handler;

import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.World;
import us.evelus.world.net.DatagramMessageSender;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.InteractMessage;

public final class InteractMessageHandler extends DatagramMessageHandler<InteractMessage> {

    private final World world;

    public InteractMessageHandler(World world) {
        super(InteractMessage.class);
        this.world = world;
    }

    @Override
    public void handle(DatagramMessageSender sender, InteractMessage msg) {
        Mob mob = world.getMob(msg.getMobId());
        world.getInteractionHandler().handle(mob, msg.getAction(), msg.getType(), msg.getTargetHash());
    }
}
