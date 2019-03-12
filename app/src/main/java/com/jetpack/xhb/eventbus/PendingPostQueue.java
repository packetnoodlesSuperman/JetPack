package com.jetpack.xhb.eventbus;

public final class PendingPostQueue {

    private PendingPost head;
    private PendingPost tail;

    synchronized void enqueue(PendingPost pendingPost) {

        if (pendingPost == null) {
            throw new NullPointerException("null cannot be enqueued");
        }

        if (tail != null) {
            tail.next =pendingPost;
            tail = pendingPost;
        } else if (head == null) {
            head = tail = pendingPost;
        } else {
            throw new IllegalStateException("Head present, but no tail");
        }

        notifyAll();
    }

}
