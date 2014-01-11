package us.evelus.world.model.update.descriptor;

import io.netty.buffer.ByteBuf;
import us.evelus.world.model.Player;
import us.evelus.world.model.update.DescriptorConstants;
import us.evelus.world.model.update.SceneDescriptor;

public final class PlayerDeletedDescriptor extends SceneDescriptor {

    private final Player player;

    public PlayerDeletedDescriptor(Player player) {
        this.player = player;
    }

    @Override
    public void serialize(ByteBuf buf) {
        buf.writeByte(DescriptorConstants.PLAYER_TYPE);
        buf.writeByte(1);
        buf.writeShort(player.getId());
    }
}
