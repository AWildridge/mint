package us.evelus.world.task;

import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public final class TaskScheduler {

    private Deque<Task> tasks = new LinkedList<>();

    public TaskScheduler() {}

    public void schedule(Task task) {
        tasks.addFirst(task);
    }

    public void tick() {
        Iterator<Task> iterator = tasks.iterator();
        while(iterator.hasNext()) {
            Task task = iterator.next();
            if(task.isStopped()) {
                iterator.remove();
                continue;
            }
            task.cycle();
        }
    }
}