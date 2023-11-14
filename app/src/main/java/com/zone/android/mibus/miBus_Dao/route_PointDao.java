package com.zone.android.mibus.miBus_Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;

import java.util.List;

@Dao
public interface route_PointDao {

    @Query("SELECT * FROM RoutePoint order by route_index asc")
    List<RoutePoint> getAll();


    @Query("SELECT * FROM RoutePoint where route_index= :index or route_index= :index+1 order by route_index asc")
    List<RoutePoint> getIndexVals(int index);



    @Query("DELETE FROM RoutePoint")
    public abstract void DeleteRoute();

    @Insert
    void insertAll(RoutePoint... routePoints);
}
