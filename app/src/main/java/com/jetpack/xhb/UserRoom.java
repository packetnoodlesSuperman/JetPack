package com.jetpack.xhb;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "userroom")
public class UserRoom {

    public UserRoom(long userId, String name, int age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
    }

    @PrimaryKey
    public long userId;

    @ColumnInfo(name = "userName")
    public String name;

    @ColumnInfo
    public int age;

}
