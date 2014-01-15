package us.evelus.world.net.msg;

public abstract class GameMessage {

    private long uid = -1L;

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getUid() {
        return uid;
    }
}
