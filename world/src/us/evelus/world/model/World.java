package us.evelus.world.model;

import us.evelus.world.interact.InteractionHandler;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobUpdateObserver;
import us.evelus.world.model.mob.event.PlayerBeaconEvent;
import us.evelus.world.model.observer.SceneObserver;
import us.evelus.world.model.update.UpdateDispatcher;
import us.evelus.world.task.TaskScheduler;

public final class World {

    public static final int PLAYER_CAPACITY = 2048;
    public static final int NPC_CAPACITY = 0;

    private EntityList<Player> players = new EntityList<>(PLAYER_CAPACITY);
    private EntityList<NPC> npcs = new EntityList<>(NPC_CAPACITY);
    private UpdateDispatcher updateDispatcher = new UpdateDispatcher();
    private InteractionHandler interactionHandler = new InteractionHandler(this);
    private TaskScheduler scheduler = new TaskScheduler();

    public void tick() {

        // Tick the task scheduler to pre-execute any tasks registered to the world
        scheduler.tick();

        // Pre-process all of the players
        for(Player player : players) {

            // Update the player
            player.tick();

            // Alert the observers that the player exists
            updateDispatcher.queue(new PlayerBeaconEvent(player));

            // Synchronize the state of the player with all of its observers
            player.synchronize();
        }

        // Dispatch all the events to the observers registered to the world
        updateDispatcher.tick();
    }

    public boolean addObserver(SceneObserver observer) {
        return updateDispatcher.addObserver(observer);
    }

    public void removeObserver(int id) {
        updateDispatcher.removeObserver(id);
    }

    public SceneObserver getSceneObserver(int id) {
        return updateDispatcher.getObserver(id);
    }

    public boolean addPlayer(Player player) {

        // Add the player to the player entity list
        boolean bool = players.add(player);
        if(!bool) return false;

        // Initialize the player object
        player.attach(new MobUpdateObserver(updateDispatcher));
        player.setInteractionHandler(interactionHandler);

        return true;
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

    public Mob getMob(int id) {
        if((id & 0x8000) != 0) {
            return players.get(id & 0x7fff);
        }
        return npcs.get(id);
    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }
}
