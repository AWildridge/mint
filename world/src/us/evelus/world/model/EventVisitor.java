package us.evelus.world.model;

import us.evelus.world.model.mob.event.*;

public interface EventVisitor {
    public void visit(MobActiveEvent event);
    public void visit(GraphicDisplayedEvent event);
    public void visit(AnimatedEvent event);
    public void visit(TraversedEvent event);
    public void visit(TeleportedEvent event);
}
