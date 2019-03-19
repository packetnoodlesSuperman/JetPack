package com.jetpack.xhb.video.utils

import android.content.Context
import android.content.SharedPreferences
import com.jetpack.xhb.video.App
import java.io.*

class SPUtil<T>(val name: String, private val default: T) {

    companion object {
        private const val fileName = "kotlin_mvp_file"

        /**
         * by lazy
         * by lazy本身是一种属性委托
         * by lazy要求属性声明为val，即不可变变量，在java中相当于被final修饰
         * 这意味着该变量一旦初始化后就不允许再被修改值了(基本类型是值不能被修改，对象类型是引用不能被修改)
         * lazy 应用于单例模式(if-null-then-init-else-return)，而且当且仅当变量被第一次调用的时候，委托方法才会执行
         */
        private val prefs: SharedPreferences by lazy {
            App.context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        }
    }

    /**
     * 内联函数之with
     *   with函数使用的一般结构
     *      with(object){
     *          //...
     *      }
     *   //适用于调用同一个类的多个方法时，可以省去类名重复，直接调用类的方法即可，
     *   //经常用于Android中RecyclerView中onBinderViewHolder中，数据model的属性映射到UI上
     */
    private fun getSharePreferences(name: String, default: T): T = with(prefs) {
        val res: Any = when(default) {
            is Long -> getLong(name, default)
            else -> deSerialization(getString(name,serialize(default)))
        }
        return res as T
    }

    /**
     * as 关键字 智能转换
     * as? 为了避免抛出异常，可以使用安全转换操作符 as?，它可以在失败时返回 null
     *
     * is
     *    在运行时通过使用 is 操作符或其否定形式 !is 来检查对象是否符合给定类型
     */
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun<A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode("UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1"))
        )
        val objectInputStream = ObjectInputStream(byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }

    @Throws(IOException::class)
    private fun<A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }
}