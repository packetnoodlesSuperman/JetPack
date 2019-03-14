package com.jetpack.xhb.okio.kotlin

import java.util.concurrent.TimeUnit


/**
 * Timeout类 使用了2种策略来处理是否应该中断等待，一种是 任务处理的时间，
 * 另一种是设定 任务时间的截止点，这两种策略也可以同时存在，判断时会取最近的临界点时间
 *
 * final kotlin中默认类和方法是final
 * open 修饰 类可以继承 方法可以重写
 */
open class Timeout {

    /**
     * companion object 修饰为伴生对象,伴生对象在类中只能存在一个，
     *     类似于java中的静态方法 Java 中使用类访问静态成员，静态方法。
     *
     * object 关键字可以表达两种含义：一种是对象表达式,另一种是 对象声明
     */
    companion object {
        val NONE: Timeout = object : Timeout() {
            override fun timeout(timeout: Long, unit: TimeUnit): Timeout = this;
            override fun deadlineNanoTime(deadlineNanoTime: Long): Timeout = this;
        }
    }


    /**
     * Kotlin的可见修饰符  （和Java不一样 Kotlin默认是public）
     * public 任何地方可见
     * private 只在该类中可见
     * protected
     * internal
     */
    //使用deadline 来 保证所有的任务执行应该在deadlineTime之前执行完成
    private var deadlineNanoTime = 0L
    private var hasDeadline = false
    //等待任务执行完成的最长时间，如果设置为0，相当于会无限等待直到任务执行完成
    private var timeoutNanos = 0L

    open fun timeout(timeout: Long, unit: TimeUnit): Timeout {
        /**
         * require 负责检查输入的参数 如果有问题 抛出IllegalArgumentException
         * check 负责检查自身是否具备执行的条件 如果有问题 抛出IllegalStateException
         * assert 负责确保执行完毕后的结果、内部状态符合预期 如果有问题 抛出AssertionError
         *
         * 例如:
         *     fun execute(sql: String) : Unit {
         *          // 输入参数的检查
         *          require(!sql.isNullOrBlank()) {
         *              "被执行的sql语句不能为空"
         *          }
         *          // 自身状态检查
         *          check(!this.host.isNullOrBlank()) {
         *              "sql server未指定"
         *          }
         *
         *          /*
         *          * conn = ...
         *          * conn.execute(sql)
         *          * conn.disconnect()
         *          */
         *
         *          // 执行完毕后状态检查
         *          assert(!conn.isConnected) {
         *          "   每次执行完毕后都需要释放连接"
         *          }
         *     }
         */
        require(timeout >= 0) {
            "timeout < 0: $timeout"
        }

        timeoutNanos = unit.toNanos(timeout)
        return this
    }

    open fun deadlineNanoTime(deadlineNanoTime: Long): Timeout {
        this.hasDeadline = true
        this.deadlineNanoTime = deadlineNanoTime
        return this
    }

    fun deadline(duration: Long, unit: TimeUnit): Timeout {
        require(duration > 0) {
            "duration <= 0: $duration"
        }

        return deadlineNanoTime(System.nanoTime() + unit.toNanos(duration))
    }

}