package us.evelus.world.model.observer;

import io.netty.buffer.ByteBuf;
import us.evelus.world.model.*;
import us.evelus.world.model.mob.event.*;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.update.SceneDescriptor;
import us.evelus.world.model.update.descriptor.PlayerAddedDescriptor;
import us.evelus.world.model.update.descriptor.PlayerDeletedDescriptor;

import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public final class SceneObserver extends Entity implements EventVisitor {

    private List<SceneDescriptor> descriptors = new LinkedList<>();
    private Set<Player> players = new HashSet<>();
    private Set<Player> pDelete = new HashSet<>();
    private boolean boundsUpdated;
    private Rectangle bounds;
    private int size;

    /**
     * Constructs a new {@link SceneObserver};
     */
    public SceneObserver() {
        updateBounds();
    }

    /**
     * Gets if an entity event is valid by checking if the entity that the event originated
     * from is within the bounds of the observer.
     *
     * @param event
     *              The event to check if is valid.
     * @return
     *              If the event is valid.
     */
    private boolean isValid(EntityEvent event) {
        Entity entity = event.getEntity();
        return entity.getPosition().within(bounds);
    }

    /**
     * Sets the size of the bounds for the scene observer.
     *
     * @param size
     *              The size of the bounds.
     */
    public void setSize(int size) {

        this.size = size;

        // Update the bounds of the observer
        updateBounds();
    }

    @Override
    public void setPosition(Position position) {
        if(doBoundsUpdate()) {
            updateBounds();
        }
        super.setPosition(position);
    }

    @Override
    public void visit(MobActiveEvent event) {

        if(!isValid(event)) {
            return;
        }

        Mob mob = event.getMob();

        // I hate this but its the easiest way to do this
        if(mob instanceof Player) {

            Player player = (Player) mob;

            // Limit the amount of players that can be appended to the list
            if(players.size() >= 256) {
                return;
            }

            // Add the player to the players list
            if(!players.contains(player)) {
                descriptors.add(new PlayerAddedDescriptor(player));
                return;
            }

            // Remove the player from the players to delete
            pDelete.remove(player);
        }
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

    /**
     * Updates this scene observer.
     */
    public void tick() {

        // Remove all of the players that are scheduled to be deleted
        players.removeAll(pDelete);

        // Add a description for each player that needs to be deleted
        for(Player player : pDelete) {
            descriptors.add(new PlayerDeletedDescriptor(player));
        }

        // TODO
        if(boundsUpdated) {

        }

        // Clear the deleted set of players
        pDelete.clear();

        // Append all of the players to the list
        pDelete.addAll(players);
    }

    /**
     * Reset the state of the observer.
     */
    public void reset() {

        // Remove all of the descriptors
        descriptors.clear();

        // Reset the bounds updated flag
        boundsUpdated = false;
    }

    /**
     * Encodes all the descriptors to a buffer.
     *
     * @param buf
     *              The buffer to encode the descriptors to.
     */
    public void encodeDescriptors(ByteBuf buf) {
        for(SceneDescriptor descriptor : descriptors) {
            descriptor.encode(buf);
        }
    }

    /**
     * Gets if a bounds update is needed for when the observer moves
     * towards the edge of the bounds.
     *
     * @return
     *          If a bounds update is needed.
     */
    public boolean doBoundsUpdate() {

        // Get the modified rectangle, the default view is 32x32
        Rectangle rectangle = new Rectangle(bounds);
        rectangle.grow(-16, -16);

        return !getPosition().within(rectangle);
    }

    /**
     * Updates the bounds of the observer at the current position of the observer.
     */
    public void updateBounds() {
        bounds = getPosition().getSceneBounds(size);
        boundsUpdated = true;
    }

    /**
     * Synchronizes the position of this observer with a mob.
     *
     * @param mob
     *              The mob to synchronize the position of this observer with.
     */
    public void synchronizePositionWith(Mob mob) {
        mob.attach(new SynchronizePositionObserver(this));
        setPosition(mob.getPosition());
        updateBounds();
    }
}
