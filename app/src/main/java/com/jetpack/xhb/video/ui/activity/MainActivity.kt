package com.jetpack.xhb.video.ui.activity

import android.support.v4.app.FragmentTransaction
import com.flyco.tablayout.listener.CustomTabEntity
import com.jetpack.xhb.R
import com.jetpack.xhb.video.base.BaseActivity
import com.jetpack.xhb.video.mvp.bean.TabEntity
import com.jetpack.xhb.video.ui.fragment.DiscoveryFragment
import com.jetpack.xhb.video.ui.fragment.HomeFragment
import com.jetpack.xhb.video.ui.fragment.HotFragment
import com.jetpack.xhb.video.ui.fragment.MineFragment
import java.util.ArrayList

class MainActivity : BaseActivity() {

    private val mTitles = arrayOf("每日精选", "发现", "热门", "我的")
    private val mTabEntities = ArrayList<CustomTabEntity>()
    // 未被选中的图标
    private val mIconUnSelectIds = intArrayOf(R.mipmap.ic_home_normal, R.mipmap.ic_discovery_normal, R.mipmap.ic_hot_normal, R.mipmap.ic_mine_normal)
    // 被选中的图标
    private val mIconSelectIds = intArrayOf(R.mipmap.ic_home_selected, R.mipmap.ic_discovery_selected, R.mipmap.ic_hot_selected, R.mipmap.ic_mine_selected)

    private var mHomeFragment: HomeFragment? = null
    private var mDiscoveryFragment: DiscoveryFragment? = null
    private var mHotFragment: HotFragment? = null
    private var mMineFragment: MineFragment? = null

    override fun layoutId(): Int = R.layout.activity_home

    /**
     * until    a until b”从a到b范围内所有的值，包括a和不包括b  until:半闭区间运算符
     * ..       1..5 左右都是闭区间的
     * in
     * mapTo
     */
    private fun initTab() {

        /**
         * Kotlin的闭包
         *
         *  fun test(a : Int, 参数名 : (参数1 ： 类型，参数2 : 类型, ... ) -> 表达式返回类型){
         *      ...
         *  }
         *
         *  public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapTo(destination: C, transform: (T) -> R): C {
         *      for (item in this)
         *           destination.add(transform(item))
         *      return destination
         *  }
         */
        (0 until mTitles.size)
                .mapTo(mTabEntities) {
                    TabEntity(mTitles[it], mIconSelectIds[it], mIconUnSelectIds[it])
                }
    }

    /**
     * let 关键字
     *
     */
    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)

        when(position) {
            0
            -> mHomeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment.getInstance(mTitles[position]).let {
                mHomeFragment = it
                transaction.add(R.id.fl_container, it, "home")
            }
        }
    }

    /**
     * ?  表示当前对象可以为空
     * ?. 表示当前对象如果为空则不执行 .后面需要跟操作执行代码
     *
     *  val mySize= roomList?.size  ?:  0
     * ?:表示的意思是，当对象A值为null的时候，那么它就会返回后面的对象B
     *
     * "?"加在变量名后，系统在任何情况不会报它的空指针异常。
     * "!!"加在变量名后，如果对象为null，那么系统一定会报异常！
     * !!.表示当前对象如果为空也执行，然后会抛出空异常
     * !!： 表示当前对象不为空的情况下执行
     */
    private fun hideFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let{ transaction.hide(it) }
        mDiscoveryFragment?.let { transaction.hide(it) }
        mHotFragment?.let{ transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

}