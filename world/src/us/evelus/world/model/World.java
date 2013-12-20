package us.evelus.world.model;

import us.evelus.world.interact.InteractionHandler;

public final class World {

    public static final int PLAYER_CAPACITY = 2048;

    private MobList<Player> players = new MobList<>(PLAYER_CAPACITY);
    private InteractionHandler interactionHandler = new InteractionHandler(this);

    public void tick() {

    }

    public boolean addPlayer(Player player) {
        boolean bool = players.add(player);

        if(bool) {
            player.setInteractionHandler(interactionHandler);
        }

        return bool;
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
