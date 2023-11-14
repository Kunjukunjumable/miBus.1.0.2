package com.zone.android.mibus.miBus_Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Inspiron on 04-01-2018.
 */
@Entity
public class User {

    @PrimaryKey @ColumnInfo(name = "user_id")
    @NonNull
    String  userId;

    @ColumnInfo(name = "user_name")
    private String userName;

    @ColumnInfo(name = "user_route")
    private String userRoute;

    public String getUserRoute() {
        return userRoute;
    }

    public void setUserRoute(String userRoute) {
        this.userRoute = userRoute;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }




}
