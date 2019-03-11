package com.jetpack.xhb.okhttp;

import com.jetpack.xhb.okhttp.internal.Util;
import com.jetpack.xhb.okio.Buffer;
import com.jetpack.xhb.okio.BufferedSink;

import java.io.IOException;
import java.util.List;


public final class FormBody extends RequestBody {

    private static final MediaType CONTENT_TYPE =
            MediaType.parse("application/x-www-form-urlencoded");

    private final List<String> encodedNames;
    private final List<String> encodedValues;

    private FormBody(List<String> encodedNames, List<String> encodedValues) {
        this.encodedNames = Util.immutableList(encodedNames);
        this.encodedValues = Util.immutableList(encodedValues);
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        writeOrCountBytes(sink, false);
    }

    @Override
    public MediaType contentType() {
        return null;
    }

    private long writeOrCountBytes(BufferedSink sink, boolean countBytes) {
        long buteCount = 0;

        Buffer buffer;
        if (countBytes) {
            buffer = new Buffer();
        } else {
            buffer = sink.buffer();
        }

        for (int i = 0, size = encodedNames.size(); i < size; i++) {
            if (i > 0) {
//                buffer.writeByte('&');

            }
        }

        return 0;
    }
}
