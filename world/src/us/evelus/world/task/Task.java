package us.evelus.world.task;

public abstract class Task {

    private boolean isStopped;
    private int counter;
    private int delay;

    public Task(int delay, boolean immediate) {
        counter = immediate ? 1 : delay;
        this.delay = delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public abstract void execute();

    public void cycle() {
        if(--counter == 0) {
            counter = delay;
            execute();
        }
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void stop() {
        isStopped = true;
    }
}
