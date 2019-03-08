package com.jetpack.xhb.okio;

import java.io.IOException;


public class Buffer implements BufferedSink, BufferedSource, Cloneable{

    Segment head;

    long size;

    @Override
    public Buffer buffer() {
        return null;
    }

    @Override public Buffer write(ByteString byteString) {
        if (byteString == null) throw new IllegalArgumentException("byteString == null");
        byteString.write(this);
        return this;
    }

    @Override
    public BufferedSink wirte(byte[] source) throws IOException {
        return null;
    }

    /**
     * byte[] source 为ByteString的data  将ByteString的data -> Segment
     */
    @Override public Buffer write(byte[] source, int offset, int byteCount) {
        if (source == null) throw new IllegalArgumentException("source == null");
        checkOffsetAndCount(source.length, offset, byteCount);

        int limit = offset + byteCount;
        while (offset < limit) {
            Segment tail = writableSegment(1);

            int toCopy = Math.min(limit - offset, Segment.SIZE - tail.limit);
            System.arraycopy(source, offset, tail.data, tail.limit, toCopy);

            offset += toCopy;
            tail.limit += toCopy;
        }

//        size += byteCount;
        return this;
    }

    @Override
    public long writeAll(Source source) throws IOException {
        return 0;
    }

     public Buffer writeUtf8(String string) {
        return writeUtf8(string, 0, string.length());
    }

    //写进缓存
    public Buffer writeUtf8(String string, int beginIndex, int endIndex) {

        return this;
    }

    @Override
    public BufferedSink emitCompleteSegments() throws IOException {
        return null;
    }

    Segment writableSegment(int minimumCapacity) {

        return null;
    }

    /**
     * 检查数组是否越界
     */
    public static void checkOffsetAndCount(long size, long offset, long byteCount) {
        if ((offset | byteCount) < 0 || offset > size || size - offset < byteCount) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("size=%s offset=%s byteCount=%s", size, offset, byteCount));
        }
    }

    @Override
    public void write(Buffer source, long byteCount) throws IOException {

    }

    public long completeSegmentByteCount() {
        long result = size;
        if (result == 0) return 0;

        Segment tail = head.prev;
        if (tail.limit < Segment.SIZE && tail.owner) {
            result -= tail.limit - tail.pos;
        }

        return result;
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
