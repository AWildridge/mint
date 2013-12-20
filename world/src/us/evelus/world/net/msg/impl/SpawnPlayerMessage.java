package us.evelus.world.net.msg.impl;

import us.evelus.world.model.Position;
import us.evelus.world.net.msg.DatagramMessage;

public final class SpawnPlayerMessage extends DatagramMessage {

    private Position position;

    public SpawnPlayerMessage(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
