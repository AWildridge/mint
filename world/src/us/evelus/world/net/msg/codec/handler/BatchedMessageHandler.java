package us.evelus.world.net.msg.codec.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.evelus.world.net.Sender;
import us.evelus.world.net.msg.DatagramMessage;
import us.evelus.world.net.msg.codec.CodecRepository;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.BatchedMessage;

public final class BatchedMessageHandler extends DatagramMessageHandler<BatchedMessage> {

    private Logger logger = LoggerFactory.getLogger(BatchedMessageHandler.class);
    private final CodecRepository repository;

    public BatchedMessageHandler(CodecRepository repository) {
        super(BatchedMessage.class);
        this.repository = repository;
    }

    @Override
    public void handle(Sender sender, BatchedMessage msg) {
        for(DatagramMessage message : msg.getMessages()) {
            DatagramMessageHandler handler = repository.getHandler(message);

            if(handler == null) {
                logger.info("No handler registered for msg " + message.getClass().getSimpleName());
                continue;
            }

            handler.handle(sender, message);
        }
    }
}
