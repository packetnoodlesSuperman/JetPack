package com.jetpack.xhb.okhttp;

/**
 * Request的组成部分
 * 1，HttpUrl
 * 2. method
 * 3. headers
 * 4. body
 *
 */
public class Request {

    private final HttpUrl url;
    private final String method;
//    private final Headers headers;
    private final RequestBody body;
    private final Object tag;

    private Request(Builder builder) {
        this.url = builder.url;
        this.method = builder.method;
//        this.headers = builder.headers.build();
        this.body = builder.body;
        this.tag = builder.tag != null ? builder.tag : this;
    }

    public HttpUrl url() {
        return url;
    }


    /**
     * 建造者模式
     */
    public static class Builder {
        private HttpUrl url;
        private String method;
        private Headers.Builder headers;
        private RequestBody body;
        private Object tag;

        private Builder(Request request) {
            this.url = request.url;
            this.method = request.method;
            this.body = request.body;
            this.tag = request.tag;
//            this.headers = request.headers.newBuilder();
        }

        public Request build() {
            if (url == null) throw new IllegalStateException("url == null");
            return new Request(this);
        }
    }

}
