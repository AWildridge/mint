package us.evelus.world.interact;

import us.evelus.world.model.*;

public final class InteractionHandler {

    public static final int BUTTON_TYPE = 0x0;
    public static final int PLR_TYPE = 0x1;
    public static final int OBJ_TYPE = 0x2;

    private ButtonDispatcher buttonDispatcher = new ButtonDispatcher();
    private PlayerActionDispatcher plrActionDispatcher = new PlayerActionDispatcher();
    private ObjectActionDispatcher objectActionDispatcher = new ObjectActionDispatcher();
    private final World world;

    public InteractionHandler(World world) {
        this.world = world;
    }

    public void handle(Mob mob, String action, int type, long hash) {
        switch(type) {
            case BUTTON_TYPE:
                if(!(mob instanceof Player)) {
                    throw new IllegalStateException("NPCs cannot interact with buttons");
                }
                int buttonId = (int) hash & 0xffff;
                Player player = (Player) mob;
                buttonDispatcher.buttonPressed(player, buttonId);
                break;

            case PLR_TYPE:
                int plrId = (int) hash & 0xffff;
                Player target = world.getPlayer(plrId);
                handlePlayerInteraction(mob, action, target);
                break;

            case OBJ_TYPE:
                Position position = Position.fromHash30((int) hash & 0x3fffffff);
                int typeId = (int) hash >> 30 & 0xffff;

                // TEMPORARY
                GameObject object = new GameObject();
                handleObjInteraction(mob, action, object);
                break;
        }
    }

    public void handlePlayerInteraction(Mob mob, String action, Player target) {
        plrActionDispatcher.handle(mob, action, target);
    }

    public void handleObjInteraction(Mob mob, String action, GameObject object) {
        objectActionDispatcher.handle(mob, action, object);
    }
}
