package com.jetpack.xhb.jetpack;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.util.Log;
// implementation "android.arch.lifecycle:runtime:1.1.1"
public class MainPresenter implements LifecycleObserver{

    UserModel userModel;

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void create(){

        Log.e("JectPackMainPresenter", "OnCreate");

        userModel = new UserModel();
        userModel.getName().setValue("UserModel");
        userModel.getAge().setValue(12);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void resume(){

        Log.e("JectPackMainPresenter", "OnResume");

    }

}
