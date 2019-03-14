package com.jetpack.xhb.okio.kotlin

/**
 * object 关键字可以表达两种含义：一种是对象表达式,另一种是 对象声明
 *
 * 对象声明
 *      用object 修饰的类为静态类，里面的方法和变量都为静态的
 *      Java调用为  SegmentPool.INSTANCE.take();  （注意使用INSTANCE）
 */
object SegmentPool {

    const val MAX_SIZE = 64 * 1024L

    @JvmField var next: Segment? = null

    @JvmField var byteCount = 0L

    /**
     * let 操作符
     * let 函数(处理可空表达式 替换断语句)
     *
     * 1. let 表达式，和 ‘ ?. ’运算符一起使用，很方便
     * 如果 next != null 在lambda中it 或者自己定义的（result） 非空
     * 如果 next == null 什么都不会发生
     */
    @JvmStatic fun take(): Segment {
        synchronized(this) {
            next?.let {
                result ->
                next = result.next
                result.next = null
                byteCount -= Segment.SIZE
                return result
            }
        }
        return Segment()
    }

}