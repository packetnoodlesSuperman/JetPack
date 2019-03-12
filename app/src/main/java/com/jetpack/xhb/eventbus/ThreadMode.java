package com.jetpack.xhb.eventbus;

public enum ThreadMode {


    //POSTING采用与事件发布者相同的线程
    POSTING,


    //MAIN指定为主线程
    MAIN,


    //BACKGROUND指定为后台线程
    //如果发布的线程不是主线程，则在该线程订阅，如果是主线程，则使用一个单独的后台线程订阅。
    BACKGROUND,


    //ASYNC相比前三者不同的地方是可以处理耗时的操作，其采用了线程池，
    //且是一个异步执行的过程，即事件的订阅者可以立即得到执行
    //用线程池线程订阅
    ASYNC
}