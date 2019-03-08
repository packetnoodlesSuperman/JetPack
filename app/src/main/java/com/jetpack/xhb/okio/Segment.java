package com.jetpack.xhb.okio;

/**
 * 字节序列 8kb
 * 链式结构  双向链表
 */
public class Segment {

    //容量 最大字节数
    static final int SIZE = 8192;

    //最小共享容量
    static final int SHARE_MINIMUM = 1024;

    //构造参数一定创建
    final byte[] data;

    //第一个可读的位置
    int pos;

    //第一个可写的位置，一个Segment的可读数据量为limit - pos
    int limit;

    //当前存储的data数据是其它对象共享的则为真  共享机制
    boolean shared;

    //自己持有标记
    boolean owner;

    //后继
    Segment next;

    //前驱
    Segment prev;

    //空参构造
    Segment() {
        this.data = new byte[SIZE];
        this.owner = true;
        this.shared = false;
    }

    /**
     * 移除改Segment
     * 1. 判断该Segment后继（next）持有的地址 赋值给 前驱（prev）的next上
     * 2. 所以next不能是自己（this）
     */
    public Segment pop() {
        Segment result = next != this ? next : null;
        prev.next = next;
        next = null;
        prev = null;
        return result;
    }

    /**
     * 将该Segment加入该链表
     * 1. 被加入进来的Segment的前驱为 该类 this
     * 2. 被加入进来的Segment的后继为 该类 （this）的后继持有的地址
     * 3. 该类 （this）的后继持有的地址 对应的Segment的前驱 为 被加入进来的Segment
     * 4. 那么 该类 （this）的后继 为 被加入进来的Segment
     *
     */
    public Segment push(Segment segment) {
        segment.prev = this;
        segment.next = next;
        next.prev = segment;
        next = segment;
        return segment;
    }

    /**
     * 写的操作
     * System.arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
     * 这里可以了解浅复制 与 深复制
     *
     * src表示源数组     被复制的数组
     * srcPos表示源数组要复制的起始位置
     * desc表示目标数组   目标数组，也就是要把数据放进来的数组
     * destPos表示目标数组要复制的起始位置
     * length表示要复制的长度
     *
     * 比如：
     * 数组1：int[] arr = { 1, 2, 3, 4, 5 };
     * 数组2：int[] arr2 = { 5, 6,7, 8, 9 };
     * 运行：System.arraycopy(arr, 1, arr2, 0, 3);
     * 得到：
     * int[] arr2 = { 2, 3, 4, 8, 9 };
     *
     */
    public void writeTo(Segment sink, int byteCount) {
        if (!sink.owner) {
            throw new IllegalArgumentException();
        }

        if (sink.limit + byteCount > SIZE) {

            System.arraycopy(sink.data, sink.pos, sink.data, 0, sink.limit - sink.pos);
        }

        System.arraycopy(data, pos, sink.data, sink.limit, byteCount);
    }
}
