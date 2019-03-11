package com.jetpack.xhb.recyclerview;

public class ChildHelper {

    final Callback mCallback;

    ChildHelper(Callback callback) {
        mCallback = callback;
    }

    int getUnfilteredChildCount() {
        return mCallback.getChildCount();
    }

    static interface Callback {
        int getChildCount();
    }

}
