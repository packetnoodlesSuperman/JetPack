package com.jetpack.xhb.okhttp;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Dispatcher 调度器
 */
public final class Dispatcher {

    //同步队列
    private final Deque<RealCall> runningSyncCalls = new ArrayDeque<>();

    synchronized void executed(RealCall call) {
        runningSyncCalls.add(call);
    }

    synchronized void finished(Call call) {
        if (!runningSyncCalls.remove(call)) {
            throw new AssertionError("Call wasn't in-flight!");
        }
    }

    synchronized void enqueue(RealCall.AsyncCall call) {

    }

}
