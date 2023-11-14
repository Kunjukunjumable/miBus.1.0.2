package com.zone.android.mibus.miBus_Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zone.android.mibus.miBus_Entity.Route;


import java.util.List;

/**
 * Created by Inspiron on 05-11-2018.
 */

@Dao
public interface route_Dao {

    @Query("SELECT * FROM Route")
    List<Route> getAll();

    @Query("SELECT * FROM Route where route_name= :routeName")
    List<Route> selRouteId(String routeName);


    @Query("DELETE FROM Route")
    public abstract void DeleteRoute();

    @Insert
    void insertAll(Route... routes);


}
