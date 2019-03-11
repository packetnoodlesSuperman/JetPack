package com.jetpack.xhb.recyclerview;

public class OpReorderer {

    final Callback mCallback;

    public OpReorderer(Callback callback) {
        mCallback = callback;
    }

    static interface Callback {

        AdapterHelper.UpdateOp obtainUpdateOp(int cmd, int startPosition, int itemCount, Object payload);

        void recycleUpdateOp(AdapterHelper.UpdateOp op);
    }

}
