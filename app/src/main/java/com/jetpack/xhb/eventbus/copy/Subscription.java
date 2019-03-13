package com.jetpack.xhb.eventbus.copy;


/**
 * 注册者 （订阅者）
 * 注册者被@Subscribe修饰的方法
 */
public class Subscription {

    final Object subscriber;

    final SubscriberMethod subscriberMethod;

    public Subscription(Object subscriber, SubscriberMethod subscriberMethod) {
        this.subscriber = subscriber;
        this.subscriberMethod = subscriberMethod;
    }
}
