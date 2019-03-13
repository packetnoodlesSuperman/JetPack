package com.jetpack.xhb.eventbus.copy;

import java.lang.reflect.Method;

/**
 * @Subscribe 修饰的方法  解析
 */
public class SubscriberMethod {

    final Method method;
    final ThreadMode threadMode;
    final Class<?> eventType;
    final int priority;
    final boolean sticky;

    public SubscriberMethod(Method method, Class<?> eventType, ThreadMode threadMode, int priority, boolean sticky) {
        this.method = method;
        this.threadMode = threadMode;
        this.eventType = eventType;
        this.priority = priority;
        this.sticky = sticky;
    }
}
