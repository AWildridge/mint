package us.evelus.world.net.msg.codec;

import us.evelus.world.net.ChannelHelper;
import us.evelus.world.net.msg.GameMessage;

public abstract class MessageHandler<T extends GameMessage> {

    private final Class<T> messageClass;

    public MessageHandler(Class<T> messageClass) {
        this.messageClass = messageClass;
    }

    public Class<T> getMessageClass() {
        return messageClass;
    }

    public abstract void handle(ChannelHelper helper, T message);
}
