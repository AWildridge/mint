package us.evelus.world.net.msg.codec.handler;

import us.evelus.world.command.CommandDispatcher;
import us.evelus.world.net.ChannelHelper;
import us.evelus.world.net.msg.codec.MessageHandler;
import us.evelus.world.net.msg.impl.CommandMessage;

public final class CommandMessageHandler extends MessageHandler<CommandMessage> {

    private final CommandDispatcher dispatcher;

    public CommandMessageHandler(CommandDispatcher dispatcher) {
        super(CommandMessage.class);
        this.dispatcher = dispatcher;
    }

    @Override
    public void handle(ChannelHelper sender, CommandMessage msg) {
        dispatcher.handle(msg.getName(), msg.getArgs());
    }
}
