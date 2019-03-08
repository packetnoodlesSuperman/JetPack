package com.jetpack.xhb.okhttp;

import java.util.ArrayList;
import java.util.List;

/**
 * HttpUrl组成部分
 * 1. 协议 scheme
 * 2. 域名 host
 * 3. 端口 port
 * 4. 路径 pathSegments
 * 5. 参数 queryNamesAndValues
 */
public class HttpUrl {

    public HttpUrl resolve(String link) {
        Builder builder = newBuilder(link);
        return builder != null ? builder.build() : null;
    }

    public Builder newBuilder(String link) {
        Builder builder = new Builder();
        Builder.ParseResult result = builder.parse(this, link);
        return result == Builder.ParseResult.SUCCESS ? builder : null;
    }


    /**
     * 内部类
     */
    public static final class Builder {

        String scheme;//协议
        final List<String> encodedPathSegments = new ArrayList<>();

        public Builder() {
            encodedPathSegments.add("");
        }

        public Builder scheme(String scheme) {
            if (scheme == null) {
                throw new NullPointerException("scheme == null");
            } else if (scheme.equalsIgnoreCase("http")) {
                this.scheme = "http";
            } else if (scheme.equalsIgnoreCase("https")) {
                this.scheme = "https";
            } else {
                throw new IllegalArgumentException("unexpected scheme: " + scheme);
            }
            return this;
        }

        public Builder host(String host) {
            if (host == null) {
                throw new NullPointerException("host == null");
            }

            String encoded = canonicalizeHost(host, 0, host.length());

            return null;
        }

        enum ParseResult {
            SUCCESS,
            MISSING_SCHEME,
            UNSUPPORTED_SCHEME,
            INVALID_PORT,
            INVALID_HOST,
        }

        ParseResult parse(HttpUrl base, String input) {
            return null;
        }

        private static String canonicalizeHost(String input, int pos, int limit) {
            return null;
        }

        public HttpUrl build() {
            return null;
        }
    }

}
