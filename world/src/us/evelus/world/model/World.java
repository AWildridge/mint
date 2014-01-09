package us.evelus.world.model;

import us.evelus.world.interact.InteractionHandler;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.mob.MobUpdateObserver;
import us.evelus.world.model.update.UpdateDispatcher;
import us.evelus.world.task.TaskScheduler;

public final class World {

    public static final int PLAYER_CAPACITY = 2048;

    private EntityList<Player> players = new EntityList<>(PLAYER_CAPACITY);
    private UpdateDispatcher updateDispatcher = new UpdateDispatcher();
    private InteractionHandler interactionHandler = new InteractionHandler(this);
    private TaskScheduler scheduler = new TaskScheduler();

    public void tick() {

        // Tick the task scheduler to pre-execute any tasks registered to the world
        scheduler.tick();

        // Pre-process all of the players
        for(Player player : players) {

            // Synchronize the state of the player with all of its observers
            player.synchronize();
        }

        // Dispatch all the events to the observers registered to the world
        updateDispatcher.tick();
    }

    public boolean addObserver(Observer observer) {
        return updateDispatcher.addObserver(observer);
    }

    public Observer getObserver(int id) {
        return updateDispatcher.getObserver(id);
    }

    public boolean addPlayer(Player player) {
        boolean bool = players.add(player);
        if(!bool) return false;

        player.setInteractionHandler(interactionHandler);
        player.attach(new MobUpdateObserver(updateDispatcher));
        return true;
    }

    public Player getPlayer(int id) {
        return players.get(id);
    }

    public Mob getMob(int id) {
        // 0x8000 - flag for specific character type
        return null;
    }

    public InteractionHandler getInteractionHandler() {
        return interactionHandler;
    }
}
