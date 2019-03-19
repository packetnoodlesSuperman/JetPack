package com.jetpack.xhb.video.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.jetpack.xhb.R
import com.jetpack.xhb.video.App
import com.jetpack.xhb.video.mvp.bean.HomeBean
import com.jetpack.xhb.video.ui.view.ViewHolder

class HomeAdapter(context: Context = App.context, data: ArrayList<HomeBean.Issue.Item>)
    : CommonAdapter<HomeBean.Issue.Item>(context, data, -1){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            ITEM_TYPE_BANNER
            -> ViewHolder(inflaterView(R.layout.item_home_banner, parent))
            else
            -> ViewHolder(inflaterView(R.layout.item_home_content, parent))
        }
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    /**
     * 加载布局
     */
    private fun inflaterView(mLayoutId: Int, parent: ViewGroup): View {
        //创建view
        val view = mInflater?.inflate(mLayoutId, parent, false)
        return view ?: View(parent.context)
    }

    companion object {
        private const val ITEM_TYPE_BANNER = 1    //Banner 类型
        private const val ITEM_TYPE_TEXT_HEADER = 2   //textHeader
        private const val ITEM_TYPE_CONTENT = 3    //item
    }

}