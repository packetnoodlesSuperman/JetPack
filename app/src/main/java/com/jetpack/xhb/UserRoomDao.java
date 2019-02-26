package com.jetpack.xhb;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserRoomDao {

    @Query("SELECT * FROM userroom")
    List<UserRoom> getUserRooms();

    @Insert
    void insert(UserRoom user);

    @Insert
    void insertAll(UserRoom... users);
}
