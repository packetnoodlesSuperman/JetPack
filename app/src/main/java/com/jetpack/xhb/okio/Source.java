package com.jetpack.xhb.okio;

import java.io.Closeable;
import java.io.IOException;

/**
 * 读
 */
public interface Source extends Closeable {

    long read(Buffer sink, long byteCount) throws IOException;

    Timeout tiemout();

    @Override void close() throws IOException;
}
