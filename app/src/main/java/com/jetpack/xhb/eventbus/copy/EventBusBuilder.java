package com.jetpack.xhb.eventbus.copy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventBusBuilder {

    EventBusBuilder() {}

    //默认线程池
    private final static ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;

    SubscriberMethodFinder subscriberMethodFinder;


    boolean eventInheritance;


}
