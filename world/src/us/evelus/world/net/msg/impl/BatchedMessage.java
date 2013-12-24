package us.evelus.world.net.msg.impl;

import us.evelus.world.net.msg.DatagramMessage;

import java.util.LinkedList;
import java.util.Queue;

public final class BatchedMessage extends DatagramMessage {

    private Queue<DatagramMessage> messages = new LinkedList<>();

    public BatchedMessage() {}

    public void addMessage(DatagramMessage message) {
        messages.add(message);
    }

    public Queue<DatagramMessage> getMessages() {
        return messages;
    }
}
