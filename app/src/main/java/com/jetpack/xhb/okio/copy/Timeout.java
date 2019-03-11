package com.jetpack.xhb.okio.copy;

import java.util.concurrent.TimeUnit;

public class Timeout {

    private boolean hasDeadline;
    private long deadlineNanoTime;
    private long timeoutNanos;

    public Timeout() {}

    public Timeout timeout(long timeout, TimeUnit unit) {
        if (timeout < 0) {
            throw new IllegalArgumentException("timeout < 0:" + timeout);
        }
        if (unit == null) {
            throw new IllegalArgumentException("unit == null");
        }

        this.timeoutNanos = unit.toNanos(timeout);
        return this;
    }

    public long timeoutnanos() {
        return timeoutNanos;
    }

    public boolean hasDeadline() {
        return hasDeadline;
    }



}