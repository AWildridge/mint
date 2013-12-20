package us.evelus.world.net.msg.codec.handler;

import io.netty.buffer.Unpooled;
import org.codehaus.jackson.map.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.model.Player;
import us.evelus.world.model.World;
import us.evelus.world.net.Sender;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.SpawnPlayerMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SpawnPlayerMessageHandler extends DatagramMessageHandler<SpawnPlayerMessage> {

    private static final Logger logger = LoggerFactory.getLogger(SpawnPlayerMessageHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private final World world;

    public SpawnPlayerMessageHandler(World world) {
        super(SpawnPlayerMessage.class);
        this.world = world;
    }

    @Override
    public void handle(Sender sender, SpawnPlayerMessage msg) {

        Player player = new Player();
        player.setPosition(msg.getPosition());

        boolean successful = world.addPlayer(player);

        Map<String, Object> response = new HashMap<>();
        response.put("responseId", msg.getResponseId());
        response.put("successful", successful);
        response.put("playerId", player.getId());

        try {
            sender.write(Unpooled.wrappedBuffer(mapper.writeValueAsBytes(response)));
        } catch (IOException ex) {
            logger.error("Failed to write back response", ex);
        }
    }
}
