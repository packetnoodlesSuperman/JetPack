package com.jetpack.xhb.okio.copy;

import java.nio.charset.Charset;

final class Util {

    private Util() {}

    public static final Charset UTF_8 = Charset.forName("utf-8");

    public static void checkOffsetAndCount(long size, long offset, long byteCount) {
        // (offset | byteCount) < 0  检查是否存在负数
        //
        if ((offset | byteCount) < 0 || offset > size || size - offset < byteCount) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("size=%s offset=%s byteCount=%s", size, offset, byteCount));
        }

    }

}
