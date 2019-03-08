package com.jetpack.xhb.okio;

import java.io.IOException;

/**
 * 双向链表（Queue）的结构排序，内部使用了一个线程来处理链表，判断是否超时
 */
public class AsyncTimeout extends Timeout {

    private static final int TIMEOUT_WRITE_SIZE = 64 * 1024;

    private static AsyncTimeout head;

    private boolean inQueue;

    private AsyncTimeout next;

    private long timeoutAt;

    public final void enter() {

        if (inQueue) {
            throw new IllegalStateException("Unbalanced enter/exit");
        }

        long timeoutNanos = timeoutNanos();

        boolean hasDeadline = hasDeadline();

        if (timeoutNanos == 0 && !hasDeadline) {
            return;
        }

        //开启轮询链表
        inQueue = true;
        scheduleTimeout(this, timeoutNanos, hasDeadline);

    }

    private void scheduleTimeout(AsyncTimeout node, long timeoutNanos, boolean hasDeadline) {

        //判断了 AsyncTimeout 静态成员变量 head(作为链表头的引用) 是否存在，
        //如果不存在，则说明该类是第一次被实例化，则创建一个 该类 唯一的守护线程 Watchdog
        if (head == null) {
            head = new AsyncTimeout();
            //开启看门狗
            new Watchdog().start();
        }

        long now = System.nanoTime();

        if (timeoutNanos != 0 && hasDeadline) {
            node.timeoutAt = now + Math.min(timeoutNanos, node.deadlineNanoTime() - now);
        } else if (timeoutNanos != 0) {
            node.timeoutAt = now + timeoutNanos;
        } else if (hasDeadline) {
            node.timeoutAt = node.deadlineNanoTime();
        } else {
            throw new AssertionError();
        }

        //入队列
        long remainingNanos = node.remainingNanos(now);

        for (AsyncTimeout prev = head; true; prev = prev.next) {
            if (prev.next == null || remainingNanos < prev.next.remainingNanos(now)) {
                node.next = prev.next;
                prev.next = node;

                if (prev == head) {
                    AsyncTimeout.class.notify();
                }
                break;
            }
        }
    }

    private long remainingNanos(long now) {
        return timeoutAt - now;
    }


    /**
     * 使用者应该 重载 AsyncTimeout 的timeOut方法，以实现在超时时需要做的逻辑操作，比如 关闭 IO流。
     */
    protected void timeout() {
    }

    public final Sink sink(final Sink sink) {
        return new Sink() {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {

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

    /**
     * 看门狗
     * 通过一个死循环 内部 判断 队列首部距离超时的时间，之后 调用 wait(long millis, int nanos) ,
     * 将watchDog线程挂起对应的时间，之后进入下一次循环，再次判断是否超时，如无意外，
     * 此时队首的 AsyncTimeout 已经超时，调用 子类重载的 timeOut 方法
     */
    private static final class Watchdog extends Thread {

        public Watchdog() {
            super("Okio Watchdog");
            setDaemon(true);
        }

        @Override
        public void run() {
            while (true) {

                try {
                    //判断队首的超时时间，无超时则wait对应时间返回null,否则返回超时队首TimeOut
                    AsyncTimeout timedOut = AsyncTimeout.awaitTimeout();
                    //为空表示此时还没有超时节点
                    if (timedOut == null) continue;
                    //timedOut为已超时的节点，子类实现，超时时处理
                    timedOut.timeout();

                } catch (InterruptedException ignored) {

                }

            }
        }
    }

    static synchronized AsyncTimeout awaitTimeout() throws InterruptedException {

        AsyncTimeout node = head.next;

        if (node == null) {
            AsyncTimeout.class.wait();
            return null;
        }

        return node;
    }

}