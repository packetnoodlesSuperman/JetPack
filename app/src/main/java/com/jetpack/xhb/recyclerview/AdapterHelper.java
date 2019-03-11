package com.jetpack.xhb.recyclerview;

public class AdapterHelper implements OpReorderer.Callback {

    final Callback mCallback;

    AdapterHelper(Callback callback) {
        this(callback, false);
    }

    AdapterHelper(Callback callback, boolean disableRecycler) {
        mCallback = callback;
    }


    @Override
    public UpdateOp obtainUpdateOp(int cmd, int startPosition, int itemCount, Object payload) {
        return null;
    }

    @Override
    public void recycleUpdateOp(UpdateOp op) {

    }

    static class UpdateOp {

        static final int ADD = 1;

        static final int REMOVE = 1 << 1;

        static final int UPDATE = 1 << 2;

        static final int MOVE = 1 << 3;

        static final int POOL_SIZE = 30;
    }

    /**
     * 怎么理解Callback机制
     * 由我们自己实现的，但是预留给系统调用的函数，回调函数就是预留给系统调用的函数，而且我们往往知道该函数被调用的时机
     *
     * 有两点需要注意：
     * 第一点，我们写回调函数不是给自己调用的，而是准备给系统在将来某一时刻调用的；
     * 第二点，我们应该知道系统在什么情形下会调用我们写的回调函数
     *
     * 例子：
     * 1.事件监听器，这其实就是"回调"最常见的应用场景之一。我们自己不会显示地去调用 onClick方法。
     *              用户触发了该按钮的点击事件后，它会由Android系统来自动调用
     * 2. Android系统会自动调用相应的方法(onCreate, onPause, onResume,onDestroy等等)
     */
    interface Callback {

        RecyclerView.ViewHolder findViewHolder(int position);

        void offsetPositionsForRemovingInvisible(int positionStart, int itemCount);

        void offsetPositionsForRemovingLaidOutOrNewView(int positionStart, int itemCount);

        void markViewHoldersUpdated(int positionStart, int itemCount, Object payloads);

        void onDispatchFirstPass(UpdateOp updateOp);

        void onDispatchSecondPass(UpdateOp updateOp);

        void offsetPositionsForAdd(int positionStart, int itemCount);

        void offsetPositionsForMove(int from, int to);

    }
}
