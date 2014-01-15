package us.evelus.world.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class ChannelHelper {

    private final Channel channel;
    private boolean closed;

    public ChannelHelper(Channel channel) {
        this.channel = channel;
    }

    public void destroy() {

        // If a recursive destroy is called just return
        if(closed) {
            return;
        }

        // Close the channel if its still open
        if(channel.isOpen()) {
            channel.close();
        }

        // Mark that the channel is closed
        closed = true;
    }

    public void write(ByteBuf buf) throws IOException {
        if(closed) {
            throw new IOException();
        }
        //channel.write(new DatagramPacket(buf, address));
    }
}
