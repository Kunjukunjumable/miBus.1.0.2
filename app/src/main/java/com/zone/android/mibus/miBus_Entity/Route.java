package com.zone.android.mibus.miBus_Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Inspiron on 05-11-2018.
 */


@Entity
public class Route {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "route_name")
    private String routeName;

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    @ColumnInfo(name = "route_id")
    private String routeId;



    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }




}
