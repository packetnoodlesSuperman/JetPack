package com.jetpack.xhb.eventbus.meta;

import com.jetpack.xhb.eventbus.SubscriberMethod;

/**
 * Subscriber Index（订阅者索引）是一个可选择的优化技术，用来加速初始化subscriber注册。
 * 通过使用EventBus annotation processor（EventBus注解处理器），订阅者索引在编译期间就会被创建。
 * 虽然没有规定必须使用它，但是由于它在Android中最佳性能，官方推荐使用此方式。
 *
 * 第一种方案
 *      @ Subscribe注解subscriber类 同时subscriber类和事件类必须是public 在运行时通过反射的方式
 *
 * 第二种方案
 * android {
 *      defaultConfig {
 *              javaCompileOptions {
 *                      annotationProcessorOptions {
 *                          arguments = [ eventBusIndex : 'com.example.myapp.MyEventBusIndex' ]
 *                      }
 *              }
 *     }
 * }
 */
public interface SubscriberInfoIndex {

    Class<?> getSubscriberClass();

    SubscriberMethod[] getSubscriberMethod();

    SubscriberInfo getSuperSubscriberInfo();

    boolean shouldCheckSuperclass();

}