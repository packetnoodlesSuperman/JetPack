package com.jetpack.xhb.okio;

import java.io.UnsupportedEncodingException;

public final class Base64 {

    //私有构造
    private Base64() {}

    //解码
    //Base64是一种用64个字符来表示任意二进制数据的方法 [a-z, A-Z, 0-9, +, -] 64个字符
    //Base64编码会把3字节的二进制数据编码为4字节的文本数据，长度增加33%  一个字符占6bit
    //Base64用\x00字节在末尾补足后，再在编码的末尾加上1个或2个=号，表示补了多少字节，解码的时候，会自动去掉
    public static byte[] decode(String in) {
        int limit = in.length();

        //解码最后字符的位置边界
        for(; limit > 0; limit--) {
            char c = in.charAt(limit - 1);
            if (c != '=' && c != '\n' && c != '\r' && c != '\t' && c != ' ') {
                break;
            }
        }

        //创建解码后的实际存储空间
        byte[] out = new byte[(int) (limit*6L/8L)];
        int outCount = 0;
        int inCount = 0;

        int word = 0;

        for (int pos = 0; pos < limit; pos++) {
            //获取每个字符
            char c = in.charAt(pos);

            int bits;
            if (c >= 'A' && c <= 'Z') {
                // char ASCII value
                //  A    65    0
                //  Z    90    25 (ASCII - 65)
                bits = c - 65;
            }else if (c >= 'a' && c <= 'z') {
                // char ASCII value
                //  a    97    26
                //  z    122   51 (ASCII - 71)
                bits = c - 71;
            } else if (c >= '0' && c <= '9') {
                // char ASCII value
                //  0    48    52
                //  9    57    61 (ASCII + 4)
                bits = c + 4;
            } else if (c == '+' || c == '-') {
                bits = 62;
            } else if (c == '/' || c == '_') {
                bits = 63;
            } else if (c == '\n' || c == '\r' || c == ' ' || c == '\t') {
                continue;
            } else {
                return null;
            }

        }

        return null;
    }

    //编码
    public static String encodeUrl(byte[] in) {
        return encode(in, MAP);
    }

    private static String encode(byte[] in, byte[] map) {
        //编码后的长度
        int  length = (in.length + 2) * 4/3;
        byte[] out = new byte[length];

        //排除最后除不尽 也就是末尾余数字符
        int index = 0, end = in.length - in.length % 3;

        //三个字节变四个字节存储  每个字节都是0到63 闭区间
        for (int i = 0; i < end; i+= 3) {
            //位运算 取一个字节 其实byte应该是只有一个字节的  取排列的第一个byte的第一位到第六位
            out[index++] = map[(in[i] & 0xff) >> 2];

            out[index++] = map[((in[i] & 0x03) << 4) | ((in[i + 1] & 0xff) >> 4)];
            out[index++] = map[((in[i + 1] & 0x0f) << 2) | ((in[i + 2] & 0xff) >> 6)];
            out[index++] = map[(in[i + 2] & 0x3f)];
        }

        switch (in.length % 3) {
            case 1:
                out[index++] = map[(in[end] & 0xff) >> 2];
                out[index++] = map[(in[end] & 0x03) << 4];
                out[index++] = '=';
                out[index++] = '=';
                break;
            case 2:
                out[index++] = map[(in[end] & 0xff) >> 2];
                out[index++] = map[((in[end] & 0x03) << 4) | ((in[end + 1] & 0xff) >> 4)];
                out[index++] = map[((in[end + 1] & 0x0f) << 2)];
                out[index++] = '=';
                break;
        }
        try {
            return new String(out, 0, index, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }


    //base64 64种字符
    private static final byte[] MAP = new byte[]{
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
            'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9', '+', '/'
    };

}
