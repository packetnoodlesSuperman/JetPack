package com.jetpack.xhb.okio.copy;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class ByteString implements Serializable, Comparable<ByteString> {

    static final char[] HEX_DIGIST = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private static final long serialVersionUID = 1L;

    public static final ByteString EMPTY = ByteString.of();

    //对字节码的封装
    final byte[] data;

    transient String utf8;

    public ByteString(byte[] data) {
        this.data = data;
    }

    /**
     * of() 等同于of(new byte[]{});
     */
    public static ByteString of(byte... data) {
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }
        return new ByteString(data.clone());
    }

    public static ByteString of(byte[] data, int offset, int byteCount) {
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }

        Util.checkOffsetAndCount(data.length, offset, byteCount);

        byte[] copy = new byte[byteCount];
        System.arraycopy(data, offset, copy, 0, byteCount);
        return new ByteString(copy);
    }

    public static ByteString encodeUtf8(String s) {
        if (s == null) {
            throw new IllegalArgumentException("s == null");
        }

        ByteString byteString = new ByteString(s.getBytes(Util.UTF_8));
        byteString.utf8 = s;
        return byteString;
    }

    @Override
    public int compareTo(@NonNull ByteString o) {
        return 0;
    }
}
