package us.evelus.world.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import static us.evelus.world.net.msg.MessageConstants.*;

import java.util.List;

public final class FrameDecoder extends ByteToMessageDecoder {

    private static final int[] SIZES = new int[256];
    private static final int UNSET = -3;
    private static final int VAR_BYTE = -1;
    private static final int VAR_SHORT = -2;
    private State state = State.READ_ID;
    private int currentId;
    private int size;

    private enum State {
        READ_ID, READ_SIZE, READ_PAYLOAD
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {

        do {
            if(state == State.READ_ID) {

                // Read the current frame id from the buffer
                currentId = buf.readByte() & 0xff;
                size = SIZES[currentId];

                // If the packet is unused then further frames cannot be parsed correctly
                if(size == UNSET) {
                    throw new RuntimeException();
                }

                // Update the state so that the size is read
                state = size > 0 ? State.READ_PAYLOAD : State.READ_SIZE;
            }

            if(state == State.READ_SIZE) {

                // Check if the additional bytes to read the size are available in the buffer
                int check = size == VAR_BYTE ? 1 : 2;
                if(!buf.isReadable(check)) {
                    return;
                }

                // Read the size from the buffer
                size = 0;
                for(int i = 0; i < check; i++) {
                    size |= (buf.readByte() & 0xff) << 8 * (1 - i);
                }

                // Update the state so that the payload is read
                state = State.READ_PAYLOAD;
            }

            if(state == State.READ_PAYLOAD) {

                // Check if the payload for the frame can be read
                if(!buf.isReadable(size)) {
                    return;
                }

                // Read the bytes to the buffer for the frame
                ByteBuf frameBuf = buf.readBytes(size);

                // Add the frame to the list of decoded objects
                list.add(new GameFrame(currentId, frameBuf));

                // Update the state so that the id is now read
                state = State.READ_ID;
            }

        // Continue looping as long as their is an id to be read
        } while(buf.isReadable());
    }

    static {

        // By default set all of the packet ids to unset
        for(int id = 0; id < SIZES.length; id++) {
            SIZES[id] = UNSET;
        }

        SIZES[COMMAND_MSG]    = VAR_BYTE;
        SIZES[CLOCK_TICK_MSG] = 0;
    }
}
