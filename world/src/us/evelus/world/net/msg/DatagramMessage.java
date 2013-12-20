package us.evelus.world.net.msg;

public abstract class DatagramMessage {

    private long responseId = -1L;

    public void setResponseId(long responseId) {
        this.responseId = responseId;
    }

    public long getResponseId() {
        return responseId;
    }
}
