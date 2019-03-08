package com.jetpack.xhb.okio;

import java.io.IOException;

public interface BufferedSink extends Sink{

    Buffer buffer();

    BufferedSink write(ByteString byteString) throws IOException;

    BufferedSink wirte(byte[] source) throws IOException;

    BufferedSink write(byte[] source, int offset, int byteCount) throws IOException;

    long writeAll(Source source) throws IOException;

    BufferedSink emitCompleteSegments() throws IOException;

}
