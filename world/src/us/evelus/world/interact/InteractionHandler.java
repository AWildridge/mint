package us.evelus.world.interact;

import us.evelus.world.model.*;
import us.evelus.world.model.Mob;

public final class InteractionHandler {

    public static final int BUTTON_TYPE = 0x0;
    public static final int CHAR_TYPE   = 0x1;

    private ButtonDispatcher buttonDispatcher = new ButtonDispatcher();
    private MobActionDispatcher charActionDispatcher = new MobActionDispatcher();
    private final World world;

    public InteractionHandler(World world) {
        this.world = world;
    }

    public void handle(Mob character, String action, int type, int id) {
        switch(type) {
            case BUTTON_TYPE:
                if(!(character instanceof Player)) {
                    throw new IllegalStateException("NPCs cannot interact with buttons");
                }
                Player player = (Player) character;
                buttonDispatcher.buttonPressed(player, id);
                break;

            case CHAR_TYPE:
                Mob target = world.getMob(id);
                handleMobInteraction(character, action, target);
                break;
        }
    }

    public void handleMobInteraction(Mob character, String action, Mob target) {
        charActionDispatcher.handle(character, action, target);
    }
}
