package us.evelus.world.net.msg.impl;

import us.evelus.world.net.msg.GameMessage;

public final class QueryObserverMessage extends GameMessage {

    private int id;

    public QueryObserverMessage(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
