package com.jetpack.xhb.okio.kotlin

import java.io.Closeable
import java.io.Flushable
import java.io.IOException

interface Sink : Closeable, Flushable {

    /**
     * 方法采用注解的方式抛出异常。
     *      @Throws(IOException::class)
     *      fun createDirectory(file: File) {
     *          if (file.exists()) {
     *               throw IOException("Directory already exists")
     *          }
     *          file.createNewFile()
     *     }
     */
    @Throws(IOException::class)
    fun write(source: Buffer, byteCount: Long)

    @Throws(IOException::class)
    override fun flush()

    fun timeout(): Timeout

    @Throws(IOException::class)
    override fun close()

}