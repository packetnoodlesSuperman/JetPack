package com.jetpack.xhb.okhttp;

import java.io.IOException;

public interface Call {

    //获取完整请求
    Request request();

    //发起网络请求 同步
    Response execute() throws IOException;

    //发起网络请求 异步
    void enqueue(Callback responseCallback);

    void cancel();

    boolean isExecuted();

    boolean isCanceled();

    interface Factory {

        Call newCall(Request request);

    }

}
