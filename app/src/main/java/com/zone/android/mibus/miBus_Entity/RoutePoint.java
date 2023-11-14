package com.zone.android.mibus.miBus_Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class RoutePoint {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "route_key")
    private String routeKey;


    public String getRouteKey() {
        return routeKey;
    }

    public void setRouteKey(@NonNull String routeKey) {
        this.routeKey = routeKey;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getPointId() {
        return pointId;
    }

    public void setPointId(String pointId) {
        this.pointId = pointId;
    }

    public int getRouteIndex() {
        return routeIndex;
    }

    public void setRouteIndex(int routeIndex) {
        this.routeIndex = routeIndex;
    }

    public String getRouteLat() {
        return routeLat;
    }

    public void setRouteLat(String routeLat) {
        this.routeLat = routeLat;
    }

    public String getRouteLong() {
        return routeLong;
    }

    public void setRouteLong(String routeLong) {
        this.routeLong = routeLong;
    }

    @ColumnInfo(name = "route_name")
    private String routeName;



    @ColumnInfo(name = "point_id")
    private String pointId;


    @ColumnInfo(name = "route_index")
    private int routeIndex;




    @ColumnInfo(name = "route_lat")
    private String routeLat;


    @ColumnInfo(name = "route_long")
    private String routeLong;

}


