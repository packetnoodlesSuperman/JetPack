package com.jetpack.xhb.okio.copy;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * 字节序列的封装
 */
public class ByteString implements Serializable, Comparable<ByteString> {

    static final char[] HEX_DIGIST = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private static final long serialVersionUID = 1L;

    final byte[] data;

    public static final ByteString EMPTY = ByteString.of();

    private static ByteString of(byte... data) {
        if (data == null) throw new IllegalArgumentException("data == null");
        return new ByteString(data.clone());
    }

    public ByteString(byte[] data) {
        this.data = data;
    }

    @Override
    public int compareTo(@NonNull ByteString o) {
        return 0;
    }
}
