package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.DatagramMessage;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.CodecRepository;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.BatchedMessage;

public final class BatchedMessageDecoder extends DatagramMessageDecoder<BatchedMessage> {

    private final CodecRepository repository;

    public BatchedMessageDecoder(CodecRepository repository) {
        super(MessageConstants.BATCH);
        this.repository = repository;
    }

    @Override
    public BatchedMessage decode(ByteBuf buf) throws MalformedMessageException {

        BatchedMessage message = new BatchedMessage();

        while(buf.isReadable()) {
            int id = buf.readByte() & 0xff;

            DatagramMessageDecoder decoder = repository.getDecoder(id);
            if(decoder == null) {
                throw new MalformedMessageException("no message decoder registered for msg " + id);
            }

            message.addMessage(decoder.decode(buf));
        }

        return message;
    }
}
