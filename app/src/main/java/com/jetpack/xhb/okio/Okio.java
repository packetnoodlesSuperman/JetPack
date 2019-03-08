package com.jetpack.xhb.okio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public final class Okio {

    private Okio() {}

    /**
     * 创建一个Sink实例 RealBufferedSink 输出流 写
     */
    public static BufferedSink buffer(Sink sink) {
        if (sink == null) throw new IllegalArgumentException("sink == null");

        return new RealBufferedSink(sink);
    }

    public static BufferedSource buffer(Source source) {
        if (source == null) throw new IllegalArgumentException("source == null");
        return new RealBufferedSource(source);
    }

    public static Source source(InputStream in) {
        return source(in, new Timeout());
    }

    private static Source source(final InputStream in, final Timeout timeout) {
        return null;
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }

        //创建一个AsyncTimeout
        AsyncTimeout timeout = timeout(socket);
        Sink sink = sink(socket.getOutputStream(), timeout);
        return timeout.sink(sink);

    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file == null) throw new IllegalArgumentException("file == null");
        return sink(new FileOutputStream(file));
    }

    public static Sink sink(OutputStream out) {
        return sink(out, new Timeout());
    }

    private static Sink sink(final OutputStream out, final Timeout timeout) {
        if (out == null) throw new IllegalArgumentException("out == null");
        if (timeout == null) throw new IllegalArgumentException("timeout == null");

        return new Sink() {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                Util.checkOffsetAndCount(source.size, 0, byteCount);

                while (byteCount > 0) {

                }
            }

            @Override
            public void flush() throws IOException {

            }

            @Override
            public Timeout timeout() {
                return null;
            }

            @Override
            public void close() throws IOException {

            }
        };
    }






    private static AsyncTimeout timeout(final Socket socket) {
        return new AsyncTimeout(){

            @Override
            public void timeout() {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
