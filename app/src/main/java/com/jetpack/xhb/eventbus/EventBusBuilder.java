package com.jetpack.xhb.eventbus;

import com.jetpack.xhb.eventbus.meta.SubscriberInfoIndex;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * EventBusBuilder 是 EventBus 框架中的个性化配置类
 */
public class EventBusBuilder {

    //默认的线程池 定义一个线程池用于处理后台线程和异步线程分发事件
    private final static ExecutorService DEFAULT_EXECUTOR_SERVICE = Executors.newCachedThreadPool();
    ExecutorService executorService = DEFAULT_EXECUTOR_SERVICE;

    // 打印订阅异常，默认true
    boolean logSubscriberExceptions = true;
    // 打印没有订阅消息，默认为true
    boolean logNoSubscriberMessages = true;

    // 发送分发事件的异常，默认true
    boolean sendSubscriberExceptionEvent = true;
    // 设置发送的的事件在没有订阅者的情况时，EventBus是否保持静默，默认true
    boolean sendNoSubscriberEvent = true;
    // 如果onEvent***方法出现异常，是否将此异常分发给订阅者（默认：false）
    boolean throwSubscriberException;

    // 默认情况下，EventBus认为事件类有层次结构（订户超类将被通知）
    boolean eventInheritance = true;

    // 设置忽略订阅索引，即使事件已被设置索引，默认为false
    //是否忽略生成被观察者订阅的方法（通过反射）
    boolean ignoreGeneratedIndex;

    //在3.0以前，接收处理事件的方法名以onEvent开头，方法名称验证避免不是以此开头，启用严格的方法验证（默认：false）
    //是否开启严格的方法验证,(public,只有一个参数,不为static及abstract),非法则均抛出异常
    boolean strictMethodVerification;

    //对类忽略方法校验，目前未实现
    List<Class<?>> skipMethodVerificationForClasses;

    // 添加由EventBus“注释预处理器生成的索引''
    //通过annotation preprocessor生成的订阅者方法list
    List<SubscriberInfoIndex> subscriberInfoIndexes;

    EventBusBuilder() { }

}
