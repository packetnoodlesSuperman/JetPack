package com.jetpack.xhb.okhttp;

import com.jetpack.xhb.okio.BufferedSink;
import com.jetpack.xhb.okio.Util;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 请求体
 */
public abstract class RequestBody {

    public static RequestBody create(MediaType contentType, String content){
        Charset charset = Util.UTF_8;
        if (contentType != null) {
            charset = contentType.charset();
            if (charset == null) {
                charset = Util.UTF_8;
                contentType = MediaType.parse(contentType + "; charset=utf-8");
            }
        }
        byte[] bytes = content.getBytes(charset);
        return create(contentType, bytes);
    }

    public static RequestBody create(final MediaType contentType, final byte[] content) {
        return create(contentType, content, 0, content.length);
    }

    public static RequestBody create(final MediaType contentType, final byte[] content,
                                     final int offset, final int byteCount) {
        if (content == null) throw new NullPointerException("content == null");
        Util.checkOffsetAndCount(content.length, offset, byteCount);

        return new RequestBody() {
            @Override public MediaType contentType() {
                return contentType;
            }

            @Override public long contentLength() {
                return byteCount;
            }

            //写在内存中
            @Override public void writeTo(BufferedSink sink) throws IOException {
                sink.write(content, offset, byteCount);
            }
        };
    }

    public long contentLength() throws IOException {
        return -1;
    }

    public abstract void writeTo(BufferedSink sink) throws IOException;

    public abstract MediaType contentType();

}
