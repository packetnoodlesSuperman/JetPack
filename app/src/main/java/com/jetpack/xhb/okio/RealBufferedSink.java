package com.jetpack.xhb.okio;

import java.io.IOException;

final class RealBufferedSink implements BufferedSink {

    //真正的实现是通过buffer实现
    public final Buffer buffer = new Buffer();

    //构造函数 赋值
    public final Sink sink;

    boolean closed;

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
        //buffer实现
        buffer.write(byteString);
        return emitCompleteSegments();
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

    public BufferedSink writeUtf8(String string) throws IOException {
        if (closed) throw new IllegalStateException("closed");

        buffer.writeUtf8(string);
        return emitCompleteSegments();
    }

    //
    @Override public BufferedSink emitCompleteSegments() throws IOException {
        if (closed) throw new IllegalStateException("closed");

        long byteCount = buffer.completeSegmentByteCount();

        if (byteCount > 0) sink.write(buffer, byteCount);
        return this;
    }


    @Override
    public void flush() throws IOException {

    }

    @Override
    public Timeout timeout() {
        return null;
    }

    @Override
    public void close() throws IOException { }
}
