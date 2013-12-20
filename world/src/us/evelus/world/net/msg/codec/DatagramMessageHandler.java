package us.evelus.world.net.msg.codec;

import us.evelus.world.net.Sender;
import us.evelus.world.net.msg.DatagramMessage;

public abstract class DatagramMessageHandler<T extends DatagramMessage> {

    private final Class<T> messageClass;

    public DatagramMessageHandler(Class<T> messageClass) {
        this.messageClass = messageClass;
    }

    public Class<T> getMessageClass() {
        return messageClass;
    }

    public abstract void handle(Sender sender, T message);
}
