package com.jetpack.xhb.okhttp;

import java.io.IOException;

public interface Interceptor {

    Response intercept(Chain chain) throws IOException;

    interface Chain {

        Request request();

        Response proceed(Request request) throws IOException;

        Connection connection();

    }

}
