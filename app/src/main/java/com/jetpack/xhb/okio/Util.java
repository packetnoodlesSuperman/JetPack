package com.jetpack.xhb.okio;

import java.nio.charset.Charset;

public class Util {

    public static final Charset UTF_8 = Charset.forName("UTF-8");

    private Util() { }

    //数组越界检查
    public static void checkOffsetAndCount(long size, long offset, long byteCount) {
        if ((offset | byteCount) < 0 || offset > size || size - offset < byteCount) {
            throw new ArrayIndexOutOfBoundsException(
                    String.format("size=%s offset=%s byteCount=%s", size, offset, byteCount));
        }
    }

    /**
     * >>   算术位移
     * >>>  逻辑位移
     */
    public static short reverseBytesShort(short s) {
        int i = s & 0xffff; //两个字节
        int reversed = (i & 0xff00) >>> 8 | (i & 0x00ff) << 8;
        return (short) reversed;
    }



}