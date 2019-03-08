package com.jetpack.xhb.okhttp;

import android.app.Application;

import com.jetpack.xhb.okhttp.internal.NamedRunnable;

import java.io.IOException;

final class RealCall implements Call {

    private final OkHttpClient client;

    Request originalRequest;

    private boolean executed;
    volatile boolean canceled;

    public RealCall(OkHttpClient client, Request originalRequest) {
        this.client = client;
        this.originalRequest = originalRequest;
    }

    @Override
    public Request request() {
        return originalRequest;
    }

    /**
     * 同步请求
     */
    @Override
    public Response execute() throws IOException {
        synchronized (this) {
            if (executed) throw new IllegalStateException("Already Executed");
            executed = true;
        }

        client.dispatcher().executed(this);
        try {
            Response result = getResponseWithInterceptorChain(false);
            if (result == null) {
                throw new IOException("Canceled");
            }
            return result;
        } finally {
            client.dispatcher().finished(this);
        }
    }

    @Override
    public void enqueue(Callback responseCallback) {
        enqueue(responseCallback, false);
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isExecuted() {
        return false;
    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    void enqueue(Callback responseCallback, boolean forWebSocket) {
        synchronized (this) {
            if (executed) {
                throw new IllegalStateException("Already Executed");
            }
            executed = true;
        }

        client.dispatcher().enqueue(new AsyncCall(responseCallback, forWebSocket));
    }

    private Response getResponseWithInterceptorChain(boolean forWebSocket) throws IOException {
        Interceptor.Chain chain = new ApplicationInterceptorChain(0, originalRequest, forWebSocket);
        return chain.proceed(originalRequest);
    }

    class ApplicationInterceptorChain implements Interceptor.Chain {

        private final int index;
        private final Request request;
        private final boolean forWebSocket;

        public ApplicationInterceptorChain(int index, Request request, boolean forWebSocket) {
            this.index = index;
            this.request = request;
            this.forWebSocket = forWebSocket;
        }

        @Override
        public Request request() {
            return null;
        }

        @Override
        public Response proceed(Request request) throws IOException {
            return null;
        }

        @Override
        public Connection connection() {
            return null;
        }
    }

    final class AsyncCall extends NamedRunnable {

        private final Callback responseCallback;
        private final boolean forWebSocket;

        public AsyncCall(Callback responseCallback, boolean forWebSocket) {
            super("OkHttp %s", redactedUrl().toString());
            this.responseCallback = responseCallback;
            this.forWebSocket = forWebSocket;
        }

        @Override
        protected void execute() {

        }
    }

    HttpUrl redactedUrl() {
        return originalRequest.url().resolve("/...");
    }
}
