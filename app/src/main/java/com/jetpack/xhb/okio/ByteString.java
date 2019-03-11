package com.jetpack.xhb.okio;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * ByteString的几大特点
 * 1.就是把一些输入类型(字符串/字节数组/ByteBuffer/byte)转成字节数组，并封装成新的ByteString返回
 *      比如ByteString.of(...)
 * 2.封装了对ByteString的比较、加密操作，方便使用
 *      比如ByteString.encode(...)、base64(...)、md5(...)、sha1()、....
 *      ByteString.startWith(...）、endWith(...)
 */
public class ByteString implements Serializable, Comparable<ByteString>{
    //序列ID
    private static final long serialVersionUID = 1L;

    static final char[] HEX_DIGITS =  { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


    transient String utf8;


    final byte[] data;

    //构造函数
    ByteString(byte[] data) {
        this.data = data;
    }

    /**
     * of() 等同于of(new byte[]{});
     * copy一样 将字节码序列封装成ByteString
     */
    public static ByteString of(byte... data) {
        if (data == null) {
            throw new IllegalArgumentException("data == null");
        }
        return new ByteString(data.clone());
    }

    public static ByteString encodeUtf8(String s) {
        if (s == null) throw new IllegalArgumentException("s == null");
        ByteString byteString = new ByteString(s.getBytes(Util.UTF_8));
        byteString.utf8 = s;
        return byteString;
    }

    public String utf8() {
        String result = utf8;
        return result != null ? result : (utf8 = new String(data, Util.UTF_8));
    }

    void write(Buffer buffer) {
        buffer.write(data, 0, data.length);
    }

    /**
     * 先比较字节码序列
     * 再比较字节码长度
     */
    @Override
    public int compareTo(@NonNull ByteString byteString) {
        return 0;
    }
}
