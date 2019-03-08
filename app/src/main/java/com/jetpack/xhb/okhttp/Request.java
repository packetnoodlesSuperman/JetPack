package com.jetpack.xhb.okhttp;

/**
 * Request的组成部分
 * 1， HttpUrl
 * 2.  method
 * 3. headers
 * 4. body
 *
 */
public class Request {

    private HttpUrl url;

    public HttpUrl url() {
        return url;
    }

}
