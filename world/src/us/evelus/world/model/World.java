package us.evelus.world.model;

import us.evelus.world.interact.InteractionHandler;
import us.evelus.world.task.TaskScheduler;

public final class World {

    public static final int PLAYER_CAPACITY = 2048;

    private MobList<Player> players = new MobList<>(PLAYER_CAPACITY);
    private InteractionHandler interactionHandler = new InteractionHandler(this);
    private TaskScheduler scheduler = new TaskScheduler();

    public void tick() {
        scheduler.tick();
    }

    public boolean addPlayer(Player player) {
        boolean successful = players.add(player);

        if(successful) {
            player.setInteractionHandler(interactionHandler);
        }

        return successful;
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
