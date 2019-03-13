package com.jetpack.xhb.eventbus.copy;


import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SubscriberMethodFinder {

    private static final int BRIDGE = 0x40;
    private static final int SYNTHETIC = 0x1000;
    private static final int MODIFIERS_IGNORE = Modifier.ABSTRACT | Modifier.STATIC | BRIDGE | SYNTHETIC;

    private static final Map<Class<?>, List<SubscriberMethod>> METHOD_CACHE = new ConcurrentHashMap<>();

    private final boolean ignoreGeneratedIndex;

    public SubscriberMethodFinder(boolean ignoreGeneratedIndex) {
        this.ignoreGeneratedIndex = ignoreGeneratedIndex;
    }

    //注册EventBus的类 查询被@Subscriber修饰的方法
    public List<SubscriberMethod> findSubscriberMethods(Class<?> subscriberClass) {
        List<SubscriberMethod> subscriberMethods = METHOD_CACHE.get(subscriberClass);
        if (subscriberClass != null) {
            return subscriberMethods;
        }

        if (ignoreGeneratedIndex) {
            subscriberMethods = findUsingReflection(subscriberClass);
        } else {

        }

        return subscriberMethods;
    }

    //使用反射
    private List<SubscriberMethod> findUsingReflection(Class<?> subscriberClass) {
        FindState findState = prepareFindState();
        findState.initForSubscriber(subscriberClass);

        while (findState.clazz != null) {
            findUsingReflectionInSingleClass(findState);
        }

        return getMethodsAndRelease(findState);
    }

    private List<SubscriberMethod> getMethodsAndRelease(FindState findState) {
        List<SubscriberMethod> subscriberMethods = new ArrayList<>(findState.subscriberMethods);
        return subscriberMethods;
    }

    //解析
    private void findUsingReflectionInSingleClass(FindState findState) {
        Method[] methods;
        try {
            methods = findState.clazz.getDeclaredMethods();
        } catch (Throwable t) {
            methods = findState.clazz.getMethods();
            findState.skipSuperClassses = true;
        }

        for (Method method : methods) {
            int modifiers = method.getModifiers();
            //方法修饰符 符合规则
            if ((modifiers & Modifier.PUBLIC) != 0 && (modifiers & MODIFIERS_IGNORE) == 0) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 1) {
                    //形参数量为1
                    Subscribe subscribeAnnotation = method.getAnnotation(Subscribe.class);
                    if (subscribeAnnotation != null) {
                        Class<?> eventType = parameterTypes[0];

                        if (findState.checkAdd(method, eventType)) {
                            ThreadMode threadMode = subscribeAnnotation.threadMode();
                            findState.subscriberMethods.add(
                                    new SubscriberMethod(method, eventType, threadMode, subscribeAnnotation.priority(), subscribeAnnotation.sticky())
                            );
                        }
                    }
                }
            }
        }
    }

    private FindState prepareFindState() {
        return null;
    }

    static class FindState {

        final List<SubscriberMethod> subscriberMethods = new ArrayList<>();

        boolean skipSuperClassses;

        Class<?> clazz;

        public void initForSubscriber(Class<?> subscriberClass) {
            clazz = subscriberClass;
        }

        public boolean checkAdd(Method method, Class<?> eventType) {
            return false;
        }
    }
}
