package com.jetpack.xhb.okhttp.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public final class Util {

    public static String format(String format, Object... args) {
        return String.format(Locale.US, format, args);
    }

    public static <T> List<T> immutableList(List<T> list) {
        return Collections.unmodifiableList(new ArrayList<>(list));
    }
}
