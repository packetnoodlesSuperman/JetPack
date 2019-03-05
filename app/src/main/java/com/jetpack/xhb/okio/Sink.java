package com.jetpack.xhb.okio;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;

/**
 * 写 输出 发送数据
 */
public interface Sink extends Closeable, Flushable {

    void write(Buffer source, long byteCount) throws IOException;

    @Override void flush() throws IOException;

    Timeout timeout();

    @Override void close() throws IOException;
}
