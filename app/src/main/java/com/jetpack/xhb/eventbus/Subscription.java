package com.jetpack.xhb.eventbus;

public final class Subscription {

    final Object subscriber;
    final SubscriberMethod subscriberMethod;

    volatile boolean active;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
        this.active = true;
    }
}
