package us.evelus.world.command;

import java.util.HashMap;
import java.util.Map;

public final class CommandDispatcher {

    private Map<String, CommandHandler> handlers = new HashMap<>();

    public CommandDispatcher() {}

    public void handle(String command, String[] args) {
        CommandHandler handler = handlers.get(command);
        if(handler != null) {
            handler.handle(args);
        }
    }
}
