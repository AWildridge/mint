package us.evelus.world.model.observer;

import io.netty.buffer.ByteBuf;
import us.evelus.world.model.Player;
import us.evelus.world.model.mob.event.*;
import us.evelus.world.model.Entity;
import us.evelus.world.model.EventVisitor;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.update.SceneDescriptor;
import us.evelus.world.model.update.descriptor.PlayerAddedDescriptor;
import us.evelus.world.model.update.descriptor.PlayerDeletedDescriptor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class SceneObserver extends Entity implements EventVisitor {

    private List<SceneDescriptor> descriptors = new LinkedList<>();
    private Set<Player> players = new HashSet<>();
    private Set<Player> plrDelete = new HashSet<>();

    @Override
    public void visit(PlayerBeaconEvent event) {

        // Limit the amount of players that can be appended to the list
        if(players.size() >= 256) {
            return;
        }

        // Add the player to the players list
        if(!players.contains(event.getMob())) {
            descriptors.add(new PlayerAddedDescriptor(event.getMob()));
            return;
        }

        // Remove the player from the players to delete
        plrDelete.remove(event.getMob());
    }

    @Override
    public void visit(GraphicDisplayedEvent event) {

    }

    @Override
    public void visit(AnimatedEvent event) {

    }

    @Override
    public void visit(TraversedEvent event) {

    }

    @Override
    public void visit(TeleportedEvent event) {

    }

    public void tick() {

        // Remove all of the players that are scheduled to be removed
        players.removeAll(plrDelete);

        // Add a description for each player that needs to be deleted
        for(Player player : plrDelete) {
            descriptors.add(new PlayerDeletedDescriptor(player));
        }

        // Clear the deleted set of players
        plrDelete.clear();

        // Append all of the players to the list
        plrDelete.addAll(players);
    }

    public void reset() {

        // Remove all of the descriptors
        descriptors.clear();
    }

    public void serializeDescriptors(ByteBuf buf) {
        for(SceneDescriptor descriptor : descriptors) {
            descriptor.serialize(buf);
        }
    }

    public void synchronizePositionWith(Mob mob) {
        mob.attach(new SynchronizePositionObserver(this));
        setPosition(mob.getPosition());
    }
}
