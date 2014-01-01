package us.evelus.world.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.socket.DatagramPacket;

import java.io.IOException;
import java.net.InetSocketAddress;

public final class DatagramMessageSender {

    private final InetSocketAddress address;
    private final Channel channel;
    private boolean closed;

    public DatagramMessageSender(InetSocketAddress address, Channel channel) {
        this.address = address;
        this.channel = channel;
    }

    public void destroy() {
        if(closed) {
            throw new IllegalArgumentException();
        }
        closed = true;
    }

    public void write(ByteBuf buf) throws IOException {
        if(closed) {
            throw new IOException();
        }
        channel.write(new DatagramPacket(buf, address));
    }
}
