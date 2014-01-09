package us.evelus.world.net.msg.impl;

import us.evelus.world.net.msg.DatagramMessage;

public final class SpawnObserverMessage extends DatagramMessage {

    public static final int EXACT_POS = 0x0;
    public static final int SYNC_MOB = 0x1;

    private int flag;
    private int hash;

    public SpawnObserverMessage(int flag, int hash) {
        this.flag = flag;
        this.hash = hash;
    }

    public int getFlag() {
        return flag;
    }

    public int getHash() {
        return hash;
    }
}
