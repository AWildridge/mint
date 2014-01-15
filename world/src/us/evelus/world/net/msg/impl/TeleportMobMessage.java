package us.evelus.world.net.msg.impl;

import us.evelus.world.model.Position;
import us.evelus.world.net.msg.GameMessage;

public final class TeleportMobMessage extends GameMessage {

    private Position position;
    private int characterId;

    public TeleportMobMessage(int characterId, Position position) {
        this.characterId = characterId;
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    public int getCharacterId() {
        return characterId;
    }
}