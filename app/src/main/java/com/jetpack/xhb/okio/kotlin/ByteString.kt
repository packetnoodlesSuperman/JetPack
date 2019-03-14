package com.jetpack.xhb.okio.kotlin

import com.jetpack.xhb.okio.Base64
import java.io.Serializable

/**
 * 定义扩展方法
 * Kotlin 中类的扩展方法并不是在原类的内部进行拓展，通过反编译为Java代码，
 * 可以发现，其原理是使用装饰模式，对源类实例的操作和包装，其实际相当于我们在Java中定义的工具类方法，
 * 并且该工具类方法是使用调用者为第一个参数的，然后在工具方法中操作该调用者；
 * 该调用者在Kotlin中使用this关键字表示
 */
fun String.encodeUtf8(): ByteString {
    val byteString = ByteString(data = toByteArray(Charsets.UTF_8))
    byteString.utf8 = this;
    return byteString;
}

class ByteString constructor(var data: ByteArray) : Serializable, Comparable<ByteString> {

    /**
     * 数组方法
     */
    private val HEX_DIGIST = charArrayOf(
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    )

    @Transient internal var utf8: String? = null

    companion object {
        /**
         * const 必须修饰val
         * const 只允许在top-level级别和object中声明 如下
         *      const val THOUSAND = 1000
         *      object myObject {
         *          const val constNameObject: String = "constNameObject"
         *      }
         *
         *      class MyClass {
         *          companion object Factory {
         *              const val constNameCompanionObject: String = "constNameCompanionObject"
         *          }
         *      }
         *
         * const val 可见性为public final static，可以直接访问。
         * val 可见性为private final static，并且val 会生成方法getNormalObject()，通过方法调用访问。
         *
         * 当定义常量时，出于效率考虑，我们应该使用const val方式，避免频繁函数调用
         */
        private const val serialVersionUID = 1L;

        val EMPRY: ByteString = ByteString.of()

        /**
         * vararg 可变长参数
         */
        fun of(vararg data: Byte): ByteString = ByteString(data.clone())

        /**
         * @JvmName 这个注解的主要用途就是告诉编译器生成的Java类或者方法的名称
         *
         * @JvmStatic只能在object类或者伴生对象companion object中使用
         * @JvmStatic一般用于修饰方法，使方法变成真正的静态方法；如果修饰变量不会消除变量的getter与setter方法，
         * 但会使getter与setter方法和变量都变成静态
         */
        @JvmName("encodeUtf8")
        @JvmStatic
        fun encodeUtf8(string: String): ByteString = string.encodeUtf8()

    }

    override fun compareTo(other: ByteString): Int {
        return  0
    }

    open fun write(buffer: Buffer) {
        buffer.write(data, 0, data.size)
    }



}