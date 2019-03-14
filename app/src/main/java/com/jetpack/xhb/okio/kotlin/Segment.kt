package com.jetpack.xhb.okio.kotlin

class Segment {

    companion object {
        const val SIZE = 8192
        const val SHARE_MINIMUM = 1024
    }

    /**
     * @JvmField
     */
    @JvmField val data: ByteArray
    @JvmField var pos: Int = 0
    @JvmField var limit: Int = 0

    @JvmField var shared: Boolean = false

    @JvmField var owner: Boolean = false

    @JvmField var next: Segment? = null

    @JvmField var prev: Segment? = null

    /**
     * 主构造参数与次构造参数
     */
    constructor() {
        this.data = ByteArray(SIZE)
    }

    constructor(data: ByteArray, pos: Int, limit: Int, share: Boolean, owner: Boolean) {
        this.data = data
        this.pos = pos
        this.limit = limit
        this.shared = shared
        this.owner = owner
    }

    /**
     * !! 操作符 （非空断言）） 表示当前对象不为空的情况下执行
     *                          如果对象为null，那么系统一定会报异常
     * ? 表示当前对象可以为空 系统在任何情况不会报它的空指针异常
     */
    fun push(segment: Segment): Segment {
        segment.prev = this
        segment.next = next
        next!!.prev = segment
        next = segment
        return segment
    }

    /**
     * !==
     * 在kotlin中，‘==‘表示比较值，‘===‘表示比较两个对象的地址是否相等
     */
    fun pop(): Segment? {
        val result = if (next !== this) next else null
        prev!!.next = next
        next!!.prev = prev
        next = null
        prev = null
        return result
    }

}