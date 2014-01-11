package us.evelus.world.net.msg.codec;

import us.evelus.world.net.msg.DatagramMessage;
import us.evelus.world.net.msg.codec.decoder.*;
import us.evelus.world.net.msg.codec.handler.BatchedMessageHandler;

import java.util.HashMap;
import java.util.Map;

public final class CodecRepository {

    private Map<Class<? extends DatagramMessage>, DatagramMessageHandler> handlers = new HashMap<>();
    private DatagramMessageDecoder<?>[] decoders = new  DatagramMessageDecoder[256];

    public CodecRepository() {

        // Register all of the decoders
        register(new CommandMessageDecoder());
        register(new MobWaypointMessageDecoder());
        register(new SpawnPlayerMessageDecoder());
        register(new SpawnObserverMessageDecoder());
        register(new TeleportMobMessageDecoder());
        register(new QueryObserverMessageDecoder());
        register(new TickMessageDecoder());

        register(new BatchedMessageDecoder(this));

        // Register all of the handlers
        register(new BatchedMessageHandler(this));
    }

    private void checkId(int id) {
        if(id < 0 || id > 255) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public void register(DatagramMessageDecoder decoder) {
        checkId(decoder.getId());
        decoders[decoder.getId()] = decoder;
    }

    public DatagramMessageDecoder getDecoder(int id) {
        checkId(id);
        return decoders[id];
    }

    public void register(DatagramMessageHandler handler) {
        handlers.put(handler.getMessageClass(), handler);
    }

    public DatagramMessageHandler getHandler(DatagramMessage message) {
        return handlers.get(message.getClass());
    }
}
