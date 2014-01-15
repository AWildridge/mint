package us.evelus.world.net.msg.impl;

import us.evelus.world.model.Position;
import us.evelus.world.net.msg.GameMessage;

public final class SpawnPlayerMessage extends GameMessage {

    private Position position;

    public SpawnPlayerMessage(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
