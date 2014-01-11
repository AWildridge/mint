package us.evelus.world.net.msg.impl;

import us.evelus.world.net.msg.DatagramMessage;

public final class QueryObserverMessage extends DatagramMessage {

    private int id;

    public QueryObserverMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
