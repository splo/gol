package com.github.splo.concurrent;

import java.util.concurrent.CompletableFuture;

public class ConstantFrequencyUpdater {

    private final Runnable runnable;
    private final long wantedDelayNano;
    private transient boolean running;
    private CompletableFuture<Void> future;

    public ConstantFrequencyUpdater(final Runnable runnable, final float frequency) {
        this.runnable = runnable;
        this.wantedDelayNano = (long) (1000000000.f / frequency);
        this.running = false;
        this.future = null;
    }

    public void start() {
        running = true;
        future = CompletableFuture.runAsync(() -> {
            long nextUpdateTimeNano = System.nanoTime();
            while (running) {
                long currentTimeNano = System.nanoTime();
                while (currentTimeNano >= nextUpdateTimeNano) {
                    runnable.run();
                    nextUpdateTimeNano += wantedDelayNano;
                    currentTimeNano = System.nanoTime();
                }
                safeSleep((nextUpdateTimeNano - currentTimeNano) / 1000000);
            }
        });
    }

    public void stop() {
        running = false;
    }

    public void awaitStopped() {
        if (future != null) {
            future.join();
        }
    }

    private static void safeSleep(long sleepDurationMs) {
        if (sleepDurationMs < 1) {
            sleepDurationMs = 1;
        }
        try {
            Thread.sleep(sleepDurationMs);
        } catch (InterruptedException ignored) {
        }
    }
}
