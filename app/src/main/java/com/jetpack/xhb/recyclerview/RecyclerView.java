package com.jetpack.xhb.recyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.ScrollingView;
import android.support.v7.widget.DefaultItemAnimator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

public class RecyclerView extends ViewGroup implements ScrollingView, NestedScrollingChild {

    private static final int[] CLIP_TO_PADDING_ATTR = {android.R.attr.clipToPadding};

    //默认情况下，clipToPadding为true，表示会裁剪子view并且resize边界效果
    //padding 代表的是recyclerView的padding值是否随控件一起滚动
    boolean mClipToPadding;

    //  获取touchSlop （系统 滑动距离的最小值，大于该值可以认为滑动）
    private int mTouchSlop;
    //  获得允许执行fling （抛）的最小速度值
    private final int mMinFlingVelocity;
    //  获得允许执行fling （抛）的最大速度值
    private final int mMaxFlingVelocity;

    AdapterHelper mAdapterHelper;

    ChildHelper mChildHelper;

    public RecyclerView(Context context) {
        this(context, null);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, CLIP_TO_PADDING_ATTR, defStyle, 0);

            mClipToPadding = a.getBoolean(0, true);
            a.recycle();
        } else {
            mClipToPadding = true;
        }

        //对于键盘弹出时界面的显示效果是有影响的
        setScrollContainer(true);
        //显然是针对触屏情况下的，也就是我们点击屏幕的上的某个控件时，不要立即执行相应的点击逻辑，而是先显示焦点（即控件被选中），再点击才执行逻辑
        //对比android:focusable与android:focusableInTouchMode
        setFocusableInTouchMode(true);

        //ViewConfiguration 包含一些UI方法的标准量，包括超时、大小和距离
        final ViewConfiguration vc = ViewConfiguration.get(context);

        //  获取touchSlop （系统 滑动距离的最小值，大于该值可以认为滑动）
        mTouchSlop = vc.getScaledTouchSlop();
        //  获得允许执行fling （抛）的最小速度值
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        //  获得允许执行fling （抛）的最大速度值
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();

        // Overscroll（边界回弹）效果-- android2.3新增的功能，也就是当滑动到边界的时候，如果再滑动，就会有一个边界就会有一个发光效果。
        // ViewGroup默认情况下，出于性能考虑，会被设置成WILL_NOT_DROW，这样，ondraw就不会被执行了
        // 如果我们想要重写onDraw，就要调用setWillNotDraw（false）
        setWillNotDraw(getOverScrollMode() == View.OVER_SCROLL_NEVER);

        //mItemAnimator.setListener(mItemAnimatorListener);

        initAdapterManager();
    }

    void initAdapterManager() {
        mAdapterHelper = new AdapterHelper(new AdapterHelper.Callback() {
            @Override
            public ViewHolder findViewHolder(int position) {
                final ViewHolder vh = findViewHolderForPosition(position, true);
                return null;
            }

            @Override
            public void offsetPositionsForRemovingInvisible(int positionStart, int itemCount) {

            }

            @Override
            public void offsetPositionsForRemovingLaidOutOrNewView(int positionStart, int itemCount) {

            }

            @Override
            public void markViewHoldersUpdated(int positionStart, int itemCount, Object payloads) {

            }

            @Override
            public void onDispatchFirstPass(AdapterHelper.UpdateOp updateOp) {

            }

            @Override
            public void onDispatchSecondPass(AdapterHelper.UpdateOp updateOp) {

            }

            @Override
            public void offsetPositionsForAdd(int positionStart, int itemCount) {

            }

            @Override
            public void offsetPositionsForMove(int from, int to) {

            }
        });
    }

    ViewHolder findViewHolderForPosition(int position, boolean checkNewPosition) {
        final int childCount = mChildHelper.getUnfilteredChildCount();

        ViewHolder hidden = null;
        for (int i = 0; i < childCount; i++) {

        }

        return hidden;
    };

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public int computeHorizontalScrollRange() {
        return 0;
    }

    @Override
    public int computeHorizontalScrollOffset() {
        return 0;
    }

    @Override
    public int computeHorizontalScrollExtent() {
        return 0;
    }

    @Override
    public int computeVerticalScrollRange() {
        return 0;
    }

    @Override
    public int computeVerticalScrollOffset() {
        return 0;
    }

    @Override
    public int computeVerticalScrollExtent() {
        return 0;
    }


    public static abstract class ItemAnimator {

        private ItemAnimatorListener mListener = null;

        interface ItemAnimatorListener {
            void onAnimationFinished(ViewHolder item);
        }

        void setListener(ItemAnimatorListener listener) {
            mListener = listener;
        }
    }

    public static abstract class ViewHolder {

    }
}
