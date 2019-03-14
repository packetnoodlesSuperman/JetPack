package com.jetpack.xhb.okio.kotlin

import java.io.Closeable
import java.io.IOException

interface Source : Closeable {

    @Throws(IOException::class)
    fun read(sink: Buffer, byteCount: Long): Long

    fun timeout(): Timeout

    @Throws(IOException::class)
    override fun close()

}