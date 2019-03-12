package com.jetpack.xhb.eventbus;

import java.util.ArrayList;
import java.util.List;

public final class PendingPost {

    private static final List<PendingPost> pendingPostPool = new ArrayList<>();

    Object event;
    Subscription subscription;
    PendingPost next;


    public PendingPost(Object event, Subscription subscription) {
        this.event = event;
        this.subscription = subscription;
    }

    static PendingPost obtainPendingPost(Subscription subscription, Object event) {
        synchronized (pendingPostPool) {
            int size = pendingPostPool.size();

            if (size > 0) {
                PendingPost pendingPost = pendingPostPool.remove(size - 1);
                pendingPost.event = event;
                pendingPost.subscription = subscription;
                pendingPost.next = null;
                return pendingPost;
            }
        }

        return new PendingPost(event, subscription);
    }
}
