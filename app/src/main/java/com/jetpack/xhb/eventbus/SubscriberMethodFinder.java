package com.jetpack.xhb.eventbus;


import com.jetpack.xhb.eventbus.meta.SubscriberInfo;
import com.jetpack.xhb.eventbus.meta.SubscriberInfoIndex;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscriberMethodFinder {

    private static final int BRIDGE = 0x40;
    private static final int SYNTHETIC = 0x1000;
    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC | BRIDGE | SYNTHETIC;

    //了解 ConcurrentHashMap
    private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new ConcurrentHashMap<>();

    //指定了FindState的缓存大小为4，并使用一维的静态数组
    private static final int POOL_SIZE = 4;
    private static final FindState[] FIND_STATE_POOL = new FindState[POOL_SIZE];

    private List<SubscriberInfoIndex> subscriberInfoIndexes;
    private final boolean strictMethodVerification;

    //使用SubscriberInfoIndex方案 自动生成代码
    private final boolean ignoreGeneratedIndex;

    SubscriberMethodFinder(List<SubscriberInfoIndex> subscriberInfoIndexes, boolean strictMethodVerification,
                           boolean ignoreGeneratedIndex) {
        this.subscriberInfoIndexes = subscriberInfoIndexes;
        this.strictMethodVerification = strictMethodVerification;
        this.ignoreGeneratedIndex = ignoreGeneratedIndex;
    }

    List<SubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
        //是否有缓存
        List<SubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        if (subscriberMethods != null) {
            return subscriberMethods;
        }

        //没有缓存
        if (ignoreGeneratedIndex) {
            //使用反射方案
            subscriberMethods = findUsingRelection(subscriberClass);
        } else {
            subscriberMethods = findUsingInfo(subscriberClass);
        }
        return null;
    }


    /**
     * 使用反射的方式
     */
    private List<SubscriberMethod> findUsingRelection(Class<?> subscriberClass) {
        //创建FindState  1.订阅类  2.事件对象 3.查找的结果
        FindState findState = prepareFindState();
        findState.initForSubscriber(subscriberClass);

        //如果事件对象不是空
        while (findState.clazz != null) {
            findUsingReflectionInSingleClass(findState);


            //如果skipSuperClasses != true  继续查询父类  （会过滤掉系统类）
            findState.moveToSuperclass();
        }

        return getMethodsAndRelease(findState);
    }

    private List<SubscriberMethod> getMethodsAndRelease(FindState findState) {
        return null;
    }

    private void findUsingReflectionInSingleClass(FindState findState) {
        Method[] methods;
        try {
            methods = findState.clazz.getDeclaredMethods();//获取事件类的所有方法
        } catch (Throwable t) {
            //反射出现异常的话
            methods = findState.clazz.getMethods();
            findState.skipSuperClasses = true;
        }

        //遍历methods
        for (Method method : methods) {
            //PRIVATE: 2    （二进制  0000 0010）  PROTECTED: 4 （二进制  0000 0100） STATIC: 8 （二进制  0000 1000）
            //FINAL: 16 （二进制 0001 0000） SYNCHRONIZED: 32  （二进制  0010 0000） VOLATILE: 64  （二进制  0100 0000）
            //TRANSIENT: 128  （二进制  1000 0000） NATIVE: 256（二进制 0001  0000 0000） INTERFACE: 512（二进制 0010 0000 0000）
            //ABSTRACT: 1024  （二进制  0100 0000 0000） STRICT: 2048  （二进制 1000 0000 0000）
            //获取修饰符
            int modifiers = method.getModifiers();

            if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {

            }
        }
    }


    private FindState prepareFindState() {
        synchronized (FIND_STATE_POOL) {
            for (int i = 0; i < POOL_SIZE; i++) {
                FindState state = FIND_STATE_POOL[i];
                if (state != null) {
                    FIND_STATE_POOL[i] = null;
                    return state;
                }
            }
        }
        return new FindState();
    }

    private List<SubscriberMethod> findUsingInfo(Class<?> subscriberClass) {

        FindState findState = prepareFindState();
        findState.initForSubscriber(subscriberClass);
        return null;
    }


    /**
     * EventBus采用了一个中间器FindState
     */
    static class FindState {

        //查找的结果 反射的方式
        final List<SubscriberMethod> subscriberMethods = new ArrayList<>();
        final Map<Class, Object> anyMethodByEventType = new HashMap();

        //获取clazz  事件类的 通过反射获取父类方法 ，true 为没有获取到
        boolean skipSuperClasses;

        //订阅类subscriberClass
        Class<?> subscriberClass;
        //事件对象
        Class<?> clazz;
        //查找的结果 SubscriberInfoIndex方案
        SubscriberInfo subscriberInfo;


        void initForSubscriber(Class<?> subscriberClass) {
            this.subscriberClass = clazz = subscriberClass;
            skipSuperClasses = false;
            subscriberInfo = null;
        }

        public void moveToSuperclass() {
            if (skipSuperClasses) {
                clazz = null;
            } else {
                clazz = clazz.getSuperclass();
                String clazzName = clazz.getName();
                /** Skip system classes, this just degrades performance. */
                if (clazzName.startsWith("java.") || clazzName.startsWith("javax.") || clazzName.startsWith("android.")) {
                    clazz = null;
                }
            }
        }
    }

}
