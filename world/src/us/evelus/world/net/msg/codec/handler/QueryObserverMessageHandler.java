package us.evelus.world.net.msg.codec.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.base64.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.model.World;
import us.evelus.world.model.observer.SceneObserver;
import us.evelus.world.net.DatagramMessageSender;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.QueryObserverMessage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public final class QueryObserverMessageHandler extends DatagramMessageHandler<QueryObserverMessage> {

    private static final Logger logger = LoggerFactory.getLogger(QueryObserverMessageHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private final World world;

    public QueryObserverMessageHandler(World world) {
        super(QueryObserverMessage.class);
        this.world = world;
    }

    @Override
    public void handle(DatagramMessageSender sender, QueryObserverMessage msg) {
        SceneObserver observer = world.getSceneObserver(msg.getId());

        ByteBuf buf = Unpooled.buffer();
        observer.serializeDescriptors(buf);

        ByteBuf encoded = Base64.encode(buf);

        Map<String, Object> response = new HashMap<>();
        response.put("uid", msg.getUid());
        response.put("data", encoded.toString(StandardCharsets.UTF_8));

        try {
            sender.write(Unpooled.wrappedBuffer(mapper.writeValueAsBytes(response)));
        } catch (IOException ex) {
            logger.error("Failed to write back response", ex);
        }
    }
}
