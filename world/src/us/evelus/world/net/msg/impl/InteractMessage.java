package us.evelus.world.net.msg.impl;

import us.evelus.world.net.msg.GameMessage;

public final class InteractMessage extends GameMessage {

    private String action;
    private int mobId;
    private int type;
    private long targetHash;

    public InteractMessage(int mobId, int type, int targetHash, String action) {
        this.mobId = mobId;
        this.type = type;
        this.targetHash = targetHash;
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

    public long getTargetHash() {
        return targetHash;
    }
}
