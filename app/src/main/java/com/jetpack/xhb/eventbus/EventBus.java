package com.jetpack.xhb.eventbus;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * EventBus使用起来还是很方便的，它的源码是基于反射实现的，
 * 在3.0之后增加了注解处理器，在程序的编译时候，就可以根据注解生成相对应的代码，
 * 相对于之前的直接通过运行时反射，大大提高了程序的运行效率，
 * 但是在3.0默认的还是通过反射去查找用@Subscribe标注的方法，一般在使用的时候基本都是这个模式
 */
public class EventBus {

    //单例
    static volatile EventBus defaultInstance;

    private static final EventBusBuilder DEFAULT_BUILDER = new EventBusBuilder();
    private final SubscriberMethodFinder subscriberMethodFinder;

    //Inheritance 继承是否查询该事件的父类(包括直接父类 间接父类)以及接口 默认查询
    private final boolean eventInheritance;

    //了解ThreadLocal
    //他的特点是获取当前线程一份独有的变量数据，不受其他线程影响。
    private final ThreadLocal<PostingThreadState> currentPostingThreadState = new ThreadLocal<PostingThreadState>() {
        @Override
        protected PostingThreadState initialValue() {
            return new PostingThreadState();
        }
    };


    public static EventBus getDefault() {
        if (defaultInstance == null) {
            synchronized (EventBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new EventBus();
                }
            }
        }

        return defaultInstance;
    }

    public void register(Object subscriber) {
        //获取注册者的 当前对象的Class
        Class<?> subscriberClass = subscriber.getClass();
        //从这个class中获取到所有添加注解 （@Subscriber）的方法保存在集合中
        List<SubscriberMethod> subscriberMethods = subscriberMethodFinder.findSubscriberMethods(subscriberClass);

        synchronized (this) {
            for (SubscriberMethod subscriberMethod : subscriberMethods) {
                subscribe(subscriber, subscriberMethod);
            }
        }
    }

    private void subscribe(Object subscriber, SubscriberMethod subscriberMethod) {
        Class<?> eventType = subscriberMethod.eventType;

        Subscription newSubscription = new Subscription(subscriber, subscriberMethod);

    }

    public void post(Object event) {
        //获取当前线程缓存的 PostingThreadState
        PostingThreadState postingState = currentPostingThreadState.get();

        //把post的事件添加到事件队列
        List<Object> eventQueue = postingState.eventQueue;
        eventQueue.add(event);

        // 如果没有处在事件发布状态，那么开始发送事件并一直保持发布状态
        if (!postingState.isPosting) {


            while (!eventQueue.isEmpty()) {
                postSingleEvent(eventQueue.remove(0), postingState);
            }
        }
    }

    private void postSingleEvent(Object event, PostingThreadState postingState) {
        Class<?> eventClass = event.getClass();

        boolean subscriptionFound = false;

        if (eventInheritance) {
            //查询该事件的父类(包括直接父类 间接父类)以及接口
        } else {
            subscriptionFound = postSingleEventForEventType(event, postingState, eventClass);
        }

    }

    private boolean postSingleEventForEventType(Object event, PostingThreadState postingState, Class<?> eventClass) {
        //了解 CopyOnWriteArrayList
        CopyOnWriteArrayList<Subscription> subscriptions;
        return false;
    }

    private void postToSubscription(Subscription subscription, Object event, boolean isMainThread) {

    }

    /**
     * 使用反射调用@Subscriber注解的方法
     */
    void invokeSubscriber(Subscription subscription, Object event) {
        try {
            subscription.subscriberMethod.method.invoke(subscription.subscriber, event);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    public EventBus() {
        this(DEFAULT_BUILDER);
    }

    EventBus(EventBusBuilder builder) {

        subscriberMethodFinder = new SubscriberMethodFinder(builder.subscriberInfoIndexes,
                builder.strictMethodVerification, builder.ignoreGeneratedIndex);

        eventInheritance = builder.eventInheritance;
    }


    final static class PostingThreadState {
        //事件队列
        final List<Object> eventQueue = new ArrayList<Object>();
        boolean isPosting;
        boolean isMainThread;
        Subscription subscription;
        Object event;
        boolean canceled;
    }

}
