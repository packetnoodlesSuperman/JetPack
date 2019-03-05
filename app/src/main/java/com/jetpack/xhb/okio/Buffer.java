package com.jetpack.xhb.okio;

import okio.BufferedSource;

public class Buffer implements BufferedSink, BufferedSource, Cloneable{

    @Override public Buffer write(ByteString byteString) {
        if (byteString == null) throw new IllegalArgumentException("byteString == null");
        byteString.write(this);
        return this;
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

}
