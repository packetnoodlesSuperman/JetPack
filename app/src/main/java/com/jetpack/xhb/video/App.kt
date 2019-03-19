package com.jetpack.xhb.video

import android.app.Application
import android.content.Context
import kotlin.properties.Delegates

class App : Application() {


    /**
     * Delegates.notNull
     * lateinit
     */
    companion object {

        /**
         * kotlin的类委托
         * Kotlin 通过关键字 by 实现委托
         *  interface Base { fun print() }  // 接口
         *  class BaseImpl(val x: Int) : Base {
         *      override fun print() { print(x) }
         *  }                              //  实现此接口的被委托的类
         *
         *  class Derived(b: Base) : Base by b  // 通过关键字 by 建立委托类
         *
         *  fun main(args: Array<String>) {
         *       val b = BaseImpl(10)
         *       Derived(b).print() // 输出 10
         *  }
         *
         *
         *  属性委托
         *  val/var <属性名>: <类型> by <表达式>    //var/val：属性类型(可变/只读)
         */
        var context: Context by Delegates.notNull()
            private set //setter()访问器的私有化，并且它拥有kotlin的默认实现

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

}