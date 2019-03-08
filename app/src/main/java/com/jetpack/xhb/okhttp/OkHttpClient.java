package com.jetpack.xhb.okhttp;

public class OkHttpClient implements Cloneable, Call.Factory {

    Dispatcher dispatcher;

    @Override
    public Call newCall(Request request) {
        return new RealCall(this, request);
    }

    public Dispatcher dispatcher() {
        return dispatcher;
    }
}
