package us.evelus.world.net.msg.impl;

import us.evelus.world.net.msg.DatagramMessage;

public final class InteractMessage extends DatagramMessage {

    private String action;
    private int mobId;
    private int type;
    private int targetId;

    public InteractMessage(int mobId, int type, int targetId, String action) {
        this.mobId = mobId;
        this.type = type;
        this.targetId = targetId;
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public int getMobId() {
        return mobId;
    }

    public int getType() {
        return type;
    }

    public int getTargetId() {
        return targetId;
    }
}
