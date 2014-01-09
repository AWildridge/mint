package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.CodecRepository;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.BatchedMessage;

public final class BatchedMessageDecoder extends DatagramMessageDecoder<BatchedMessage> {

    private static final int CHUNK_SIZE = 512;
    private static final int UNSET = 0;

    private final ByteBuf buffer = Unpooled.buffer(1024);
    private final CodecRepository repository;
    private int mask = UNSET;

    public BatchedMessageDecoder(CodecRepository repository) {
        super(MessageConstants.BATCH);
        this.repository = repository;
    }

    @Override
    public BatchedMessage decode(ByteBuf buf) throws MalformedMessageException {

        int amtChunks = buf.readByte();

        if(mask == UNSET) {
            mask = (1 << amtChunks) - 1;
            buffer.clear();
        }

        int chunkId = buf.readByte();
        mask &= ~(1 << chunkId);

        buffer.writerIndex(chunkId * CHUNK_SIZE);
        buffer.writeBytes(buf);

        if(mask == UNSET) {

            BatchedMessage msg = new BatchedMessage();

            while(buffer.isReadable()) {
                int id = buffer.readByte() & 0xff;

                DatagramMessageDecoder codec = repository.getDecoder(id);
                if(codec == null) {
                    throw new MalformedMessageException("no message decoder registered for msg " + id);
                }

                msg.addMessage(codec.decode(buffer));
            }

            return msg;
        }

        return null;
    }
}
