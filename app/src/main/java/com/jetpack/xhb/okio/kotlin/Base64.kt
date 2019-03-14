package com.jetpack.xhb.okio.kotlin

val MAP ="ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/".encodeUtf8().data

fun ByteArray.encode(map: ByteArray = MAP): String {
   //编码后的长度
    val length = (size * 2) / 3 * 4
    val out = ByteArray(length)

    var index = 0
    var end = size - size%3
    var i = 0

    while (i < end) {

    }

    when(size - end) {
        1 -> {

        }
        2 -> {

        }
    }
    return out.toString()
}

