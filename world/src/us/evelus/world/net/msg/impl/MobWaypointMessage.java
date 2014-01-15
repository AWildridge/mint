package us.evelus.world.net.msg.impl;

import us.evelus.world.model.Position;
import us.evelus.world.net.msg.GameMessage;

public final class MobWaypointMessage extends GameMessage {

    private Position[] points;
    private int characterId;
    private boolean clip;

    public MobWaypointMessage(int characterId, Position[] points, boolean clip) {
        this.characterId = characterId;
        this.points = points;
        this.clip = clip;
    }

    public Position[] getPoints() {
        return points;
    }

    public int getCharacterId() {
        return characterId;
    }

    public boolean isClipped() {
        return clip;
    }
}
