package com.jetpack.xhb.okio.kotlin

class Buffer: BufferedSource, BufferedSink, Cloneable {

    var head: Segment? = null

    fun write(byteString: ByteString): Buffer{
        byteString.write(this)
        return this
    }

    fun write(source: ByteArray, offset: Int, byteCount: Int): Buffer {
        var offset = offset

        val limit = offset + byteCount
        while (offset < limit) {
            val tail = writableSegment(1)

            val toCopy = minOf(limit - offset, Segment.SIZE - tail.limit)
            System.arraycopy(source, offset, tail.data, tail.limit, toCopy)
        }

        return this
    }

    /**
     * 获取可写的Segment
     */
    fun writableSegment(minimumCapacity: Int): Segment {
        require(minimumCapacity >= 1 && minimumCapacity <= Segment.SIZE) {
            "unexpected capacity"
        }

        if (head == null) {
            val result = SegmentPool.take()
            head = result
            result.prev = result
            result.next = result
            return result
        }
        var tail = head!!.prev
        if (tail!!.limit + minimumCapacity > Segment.SIZE || !tail.owner) {
            tail = tail.push(SegmentPool.take())
        }
        return tail
    }

}