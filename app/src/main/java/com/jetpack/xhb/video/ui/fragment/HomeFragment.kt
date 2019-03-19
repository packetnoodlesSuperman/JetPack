package com.jetpack.xhb.video.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.jetpack.xhb.R
import com.jetpack.xhb.video.base.BaseFragment
import com.jetpack.xhb.video.ui.adapter.HomeAdapter


class HomeFragment : BaseFragment(){

    /**
     * 属性委托
     */
    private val linearLayoutLayoutManager by lazy {
        LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
    }

    override fun getLayoutId(): Int = R.layout.fragment_home

    private var mHomeAdapter: HomeAdapter? = null

    companion object {
        fun getInstance(title: String): HomeFragment {
            val fragment = HomeFragment()
            return fragment
        }
    }

}