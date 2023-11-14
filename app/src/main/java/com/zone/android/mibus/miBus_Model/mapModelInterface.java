package com.zone.android.mibus.miBus_Model;

import android.content.Context;

import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Presenter.mapPresInterface;

import java.util.List;


public interface mapModelInterface {
    void getRoutes(Context context, mapPresInterface mapPresInterface);
    void getRoutePoints(Context context,mapPresInterface mapPresInterface);

    void  getIndexValues(Context context,mapPresInterface mapPresInterface, int index);
}
