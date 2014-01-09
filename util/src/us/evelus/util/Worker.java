package us.evelus.util;

import java.util.concurrent.CountDownLatch;

public abstract class Worker implements Runnable {

    private final CountDownLatch latch;

    public Worker(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        execute();
        latch.countDown();
    }

    public abstract void execute();
}