package us.evelus.world.net.msg.codec;

import us.evelus.world.net.msg.GameMessage;
import us.evelus.world.net.msg.codec.decoder.*;

import java.util.HashMap;
import java.util.Map;

public final class CodecRepository {

    private Map<Class<? extends GameMessage>, MessageHandler> handlers = new HashMap<>();
    private MessageDecoder<?>[] decoders = new MessageDecoder[256];

    public CodecRepository() {

        // Register all of the decoders
        register(new CommandMessageDecoder());
        register(new MobWaypointMessageDecoder());
        register(new SpawnPlayerMessageDecoder());
        register(new SpawnObserverMessageDecoder());
        register(new TeleportMobMessageDecoder());
        register(new QueryObserverMessageDecoder());
        register(new TickMessageDecoder());
    }

    private void checkId(int id) {
        if(id < 0 || id > 255) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void register(MessageDecoder decoder) {
        checkId(decoder.getId());
        decoders[decoder.getId()] = decoder;
    }

    public MessageDecoder getDecoder(int id) {
        checkId(id);
        return decoders[id];
    }

    public void register(MessageHandler handler) {
        handlers.put(handler.getMessageClass(), handler);
    }

    public MessageHandler getHandler(GameMessage message) {
        return handlers.get(message.getClass());
    }
}
