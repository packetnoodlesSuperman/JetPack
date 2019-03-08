package com.jetpack.xhb.okio;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;


public class RealBufferedSource implements BufferedSource {

    public final Source source;

    RealBufferedSource(Source source) {
        if (source == null) throw new IllegalArgumentException("source == null");
        this.source = source;
    }
}
