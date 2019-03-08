package com.jetpack.xhb.okio;

public class SegmentPool {

    static final long MAX_SIZE = 64 * 1024;

    static Segment next;

    static long byteCount;

    private SegmentPool() {}

    static Segment take() {
        synchronized (SegmentPool.class) {
            if (next != null) {
                Segment result = next;
                next = result.next;
                result.next = null;
                byteCount -= Segment.SIZE;
                return result;
            }
            return new Segment();
        }
    }

    static void recycle(Segment segment) {
        if (segment.next != null || segment.prev != null) {
            throw  new IllegalArgumentException();
        }

        if (segment.shared) return;

        synchronized (SegmentPool.class) {
            if (byteCount + Segment.SIZE > MAX_SIZE) return;

            byteCount += Segment.SIZE;

            segment.next = next;
            segment.pos = segment.limit = 0;
            next = segment;
        }
    }
}