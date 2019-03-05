package com.jetpack.xhb.okio;

import java.io.IOException;

final class RealBufferedSink implements BufferedSink {

    public final Buffer buffer = new Buffer();

    boolean closed;

    public final Sink sink;

    RealBufferedSink(Sink sink) {
        if (sink == null) throw new IllegalArgumentException("sink == null");
        this.sink = sink;
    }

    @Override
    public Buffer buffer() {
        return null;
    }

    @Override
    public BufferedSink write(ByteString byteString) throws IOException {
        if (closed) throw new IllegalStateException("closed");
        buffer.write(byteString);
        return null;
    }

    @Override
    public BufferedSink wirte(byte[] source) throws IOException {
        return null;
    }

    @Override
    public BufferedSink write(byte[] source, int offset, int byteCount) throws IOException {
        return null;
    }

    @Override
    public long writeAll(Source source) throws IOException {
        return 0;
    }

    @Override
    public void write(Buffer source, long byteCount) throws IOException {

    }

    @Override
    public void flush() throws IOException {

    }

    @Override
    public Timeout timeout() {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
