package us.evelus.world.net.msg.codec;

import java.io.IOException;

public class MalformedMessageException extends IOException {

    public MalformedMessageException(String msg) {
        super(msg);
    }
}
