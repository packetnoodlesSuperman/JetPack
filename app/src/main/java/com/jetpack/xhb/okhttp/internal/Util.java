package com.jetpack.xhb.okhttp.internal;

import java.util.Locale;

public final class Util {

    public static String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }
}
