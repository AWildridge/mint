package us.evelus.world.net.msg.codec.handler;

import us.evelus.world.command.CommandDispatcher;
import us.evelus.world.net.DatagramMessageSender;
import us.evelus.world.net.msg.codec.DatagramMessageHandler;
import us.evelus.world.net.msg.impl.CommandMessage;

public final class CommandMessageHandler extends DatagramMessageHandler<CommandMessage> {

    private final CommandDispatcher dispatcher;

    public CommandMessageHandler(CommandDispatcher dispatcher) {
        super(CommandMessage.class);
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(DatagramMessageSender sender, CommandMessage msg) {
        dispatcher.handle(msg.getName(), msg.getArgs());
    }
}
