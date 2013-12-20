package us.evelus.world.command;

public abstract class CommandHandler {

    private final String name;

    public CommandHandler(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void handle(String[] args);
}
