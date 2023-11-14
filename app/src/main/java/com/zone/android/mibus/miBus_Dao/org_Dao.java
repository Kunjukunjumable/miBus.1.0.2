package com.zone.android.mibus.miBus_Dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.zone.android.mibus.miBus_Entity.Org;
import com.zone.android.mibus.miBus_Entity.Route;

import java.util.List;

@Dao
public interface org_Dao {

    @Query("SELECT * FROM Org")
    List<Org> getAll();

    @Query("SELECT * FROM Org where org_name= :orgname")
    List<Org> selHostName(String orgname);


    @Query("DELETE FROM Org")
    public abstract void DeleteOrg();

    @Insert
    void insertAll(Org... orgs);

}
