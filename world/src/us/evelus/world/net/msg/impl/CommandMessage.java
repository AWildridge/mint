package us.evelus.world.net.msg.impl;

import us.evelus.world.net.msg.DatagramMessage;

public final class CommandMessage extends DatagramMessage {

    private String name;
    private String[] args;

    public CommandMessage(String name, String[] args) {
        this.name = name;
        this.args = args;
    }

    public String getName() {
        return name;
    }

    public String[] getArgs() {
        return args;
    }
}
