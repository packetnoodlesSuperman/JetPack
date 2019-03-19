package com.jetpack.xhb.video.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater

abstract class CommonAdapter<T>(var mContext: Context, var mData: ArrayList<T>,
                                private var mLayoutId: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    protected var mInflater: LayoutInflater? = null

    /**
     * 类初始化代码块
     * kotlin增加了一个新的关键字init用来处理类的初始化问题
     * init模块中的内容可以直接使用构造函数的参数
     * https://blog.csdn.net/yuzhiqiang_1993/article/details/87863589
     */
    init {
        mInflater = LayoutInflater.from(mContext)
    }

}