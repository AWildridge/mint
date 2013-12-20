package us.evelus.world.net.msg;

public class MessageConstants {

    public static final int SPAWN_PLAYER_MSG      = 0x00;
    public static final int SPAWN_NPC_MSG         = 0x01;
    public static final int SPAWN_GFX_MSG         = 0x02;
    public static final int SPAWN_OBJ_MSG         = 0x03;
    public static final int SPAWN_PROJECTILE_MSG  = 0x04;
    public static final int SPAWN_GROUND_ITEM_MSG = 0x05;

    public static final int TELEPORT_CHAR_MSG     = 0x06;
    public static final int WAYPOINT_CHAR_MSG     = 0x07;


    public static final int INTERACT_MSG     = 0x08;

    public static final int COMMAND_MSG = 0xC8;

    private MessageConstants() {}
}
