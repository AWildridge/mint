package us.evelus.world.net.msg.codec.handler;

import io.netty.buffer.Unpooled;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.model.Position;
import us.evelus.world.model.World;
import us.evelus.world.model.mob.Mob;
import us.evelus.world.model.observer.SceneObserver;
import us.evelus.world.net.DatagramMessageSender;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.SpawnObserverMessage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class SpawnObserverMessageHandler extends DatagramMessageHandler<SpawnObserverMessage> {

    private static final Logger logger = LoggerFactory.getLogger(SpawnObserverMessageHandler.class);
    private static final ObjectMapper mapper = new ObjectMapper();
    private final World world;

    public SpawnObserverMessageHandler(World world) {
        super(SpawnObserverMessage.class);
        this.world = world;
    }

    @Override
    public void handle(DatagramMessageSender sender, SpawnObserverMessage msg) {
        SceneObserver observer = new SceneObserver();
        switch(msg.getFlag()) {

            case SpawnObserverMessage.EXACT_POS:
                Position position = Position.fromHash30(msg.getHash());
                observer.setPosition(position);
                break;

            case SpawnObserverMessage.SYNC_MOB:
                Mob mob = world.getMob(msg.getHash());
                observer.synchronizePositionWith(mob);
                break;
        }

        boolean successful = world.addObserver(observer);

        Map<String, Object> response = new HashMap<>();
        response.put("uid", msg.getUid());
        response.put("successful", successful);
        response.put("id", observer.getId());

        try {
            sender.write(Unpooled.wrappedBuffer(mapper.writeValueAsBytes(response)));
        } catch (IOException ex) {
            logger.error("Failed to write back response", ex);
        }
    }
}
