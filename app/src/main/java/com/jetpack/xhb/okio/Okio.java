package com.jetpack.xhb.okio;

public final class Okio {

    private Okio() { }

    /**
     * 创建一个Sink实例 RealBufferedSink 输出流 写
     */
    public static BufferedSink buffer(Sink sink) {
        if (sink == null) throw new IllegalArgumentException("sink == null");

        return new RealBufferedSink(sink);
    }
}
