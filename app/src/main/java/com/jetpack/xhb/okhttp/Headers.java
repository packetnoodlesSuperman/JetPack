package com.jetpack.xhb.okhttp;

import java.util.ArrayList;
import java.util.List;

public final class Headers {

    private final String[] namesAndValues;

    public Headers(Builder builder) {
        this.namesAndValues = builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    private Headers(String[] namesAndValues) {
        this.namesAndValues = namesAndValues;
    }

    public String get(String name) {
        return get(namesAndValues, name);
    }

    public int size() {
        return namesAndValues.length / 2;
    }

    public String name(int index) {
        return namesAndValues[index * 2];
    }

    public String value(int index) {
        return namesAndValues[index * 2 + 1];
    }

    private static String get(String[] namesAndValues, String name) {
        for (int i = namesAndValues.length - 2; i >= 0; i -= 2) {
            if (name.equalsIgnoreCase(namesAndValues[i])) {
                return namesAndValues[i + 1];
            }
        }
        return null;
    }


    public static final class Builder {
        private final List<String> namesAndValues = new ArrayList<>(20);
    }
}
