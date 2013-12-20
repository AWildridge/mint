package us.evelus.world.net.msg.codec;

import us.evelus.world.net.msg.DatagramMessage;

import java.util.HashMap;
import java.util.Map;

public final class CodecRepository {

    private Map<Class<? extends DatagramMessage>, DatagramMessageHandler> handlers = new HashMap<>();
    private DatagramMessageDecoder<?>[] decoders = new  DatagramMessageDecoder[256];

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
