package com.zone.android.mibus.miBus_Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.zone.android.mibus.miBus_Entity.User;

import java.util.List;

/**
 * Created by Inspiron on 04-01-2018.
 */

@Dao
public interface user_Dao {
    @Query("SELECT * FROM User")
    List<User> getAll();

    @Insert
    void insertAll(User... users);

    @Query("DELETE FROM User")
    public abstract int DeleteToken();

}
