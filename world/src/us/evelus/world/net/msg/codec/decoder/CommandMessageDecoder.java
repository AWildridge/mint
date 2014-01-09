package us.evelus.world.net.msg.codec.decoder;

import io.netty.buffer.ByteBuf;
import us.evelus.world.net.msg.MessageConstants;
import us.evelus.world.net.msg.codec.DatagramMessageDecoder;
import us.evelus.world.net.msg.codec.MalformedMessageException;
import us.evelus.world.net.msg.impl.CommandMessage;
import us.evelus.util.ByteBufUtils;

public final class CommandMessageDecoder extends DatagramMessageDecoder<CommandMessage> {

    public CommandMessageDecoder() {
        super(MessageConstants.COMMAND_MSG);
    }

    @Override
    public CommandMessage decode(ByteBuf buf) throws MalformedMessageException {
        String command = ByteBufUtils.readString(buf);

        String[] split = command.split(" ");

        String name = split[0];

        String[] args = new String[split.length - 1];
        System.arraycopy(split, 1, args, 0, args.length);

        CommandMessage msg = new CommandMessage(name, args);
        return msg;
    }
}
