package us.evelus.world.model.update;

import io.netty.buffer.ByteBuf;

public abstract class SceneDescriptor {
    public abstract void encode(ByteBuf buf);
}
