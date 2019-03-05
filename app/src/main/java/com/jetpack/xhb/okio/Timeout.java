package com.jetpack.xhb.okio;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.concurrent.TimeUnit;

public class Timeout {

    public static final Timeout NONE = new Timeout() {
        @Override public Timeout timeout(long timeout, TimeUnit unit) {
            return this;
        }

        @Override public Timeout deadlineNanoTime(long deadlineNanoTime) {
            return this;
        }

        @Override public void throwIfReached() throws IOException { }
    };

    private boolean hasDeadline;
    private long deadlineNanoTime;
    private long timeoutNanos;

    public Timeout() { }

    /**
     * 赋值 timeoutNanos
     */
    public Timeout timeout(long timeout, TimeUnit unit) {
        if (timeout < 0) throw new IllegalArgumentException("timeout < 0: " + timeout);
        if (unit == null) throw new IllegalArgumentException("unit == null");

        this.timeoutNanos = unit.toNanos(timeout);

        return this;
    }

    /**
     * 获取 timeoutNanos
     */
    public long timeoutNanos() {
        return timeoutNanos;
    }

    /**
     * 赋值 deadlineNanoTime
     */
    public Timeout deadlineNanoTime(long deadlineNanoTime) {
        this.hasDeadline = true;
        this.deadlineNanoTime = deadlineNanoTime;
        return this;
    }
    /**
     * 通过计算赋值 deadlineNanoTime
     */
    public final Timeout deadline(long duration, TimeUnit unit) {
        if (duration <= 0) throw new IllegalArgumentException("duration <= 0: " + duration);
        if (unit == null) throw new IllegalArgumentException("unit == null");
        return deadlineNanoTime(System.nanoTime() + unit.toNanos(duration));
    }


    /**
     * 获取 deadlineNanoTime
     */
    public long deadlineNanoTime() {
        if (!hasDeadline) throw new IllegalStateException("No deadline");
        return deadlineNanoTime;
    }

    /**
     * 获取 hasDeadline
     */
    public boolean hasDeadline() {
        return hasDeadline;
    }




    public void throwIfReached() throws IOException {
        if (Thread.interrupted()) {
            throw new InterruptedIOException("thread interrupted");
        }

        if (hasDeadline && deadlineNanoTime - System.nanoTime() <= 0) {
            throw new InterruptedIOException("deadline reached");
        }
    }
}
