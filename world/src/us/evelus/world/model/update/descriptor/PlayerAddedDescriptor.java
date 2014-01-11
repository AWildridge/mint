package us.evelus.world.model.update.descriptor;

import io.netty.buffer.ByteBuf;
import us.evelus.world.model.Player;
import us.evelus.world.model.update.DescriptorConstants;
import us.evelus.world.model.update.SceneDescriptor;

public final class PlayerAddedDescriptor extends SceneDescriptor {

    private final Player player;

    public PlayerAddedDescriptor(Player player) {
        this.player = player;
    }

    @Override
    public void serialize(ByteBuf buf) {
        buf.writeByte(DescriptorConstants.PLAYER_TYPE);
        buf.writeByte(0);
        buf.writeShort(player.getId());
    }
}
