package com.jetpack.xhb;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class UserModel extends ViewModel {

    public MutableLiveData<String> name;

    public MutableLiveData<Integer> age;

    public MutableLiveData<String> getName() {
        if (name == null) {
            name = new MutableLiveData<>();
        }
        return name;
    }

    public MutableLiveData<Integer> getAge() {
        if (age == null) {
            age = new MutableLiveData<>();
        }
        return age;
    }

}
