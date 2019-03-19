package com.jetpack.xhb.video.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    /**
     * Android Support 包之一的 support-annotations 是通过静态编译检测来提高代码质量的一个注解工具
     * 关于@LayoutRes 表明该参数、变量或者函数返回值应该是一个 layout 布局文件类型的资源
     * @IdRes 表明该参数、变量或者函数返回值应该是一个资源的 ID 类型
     * @StyleRes 表明该参数、变量或者函数返回值应该是一个 style 类型的资源
     * @Nullable 和 @NonNull 会分别检测一个变量、参数或者函数返回值是否为 null
     * ......
     */
    @LayoutRes
    abstract fun getLayoutId(): Int


}