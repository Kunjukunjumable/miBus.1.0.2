package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;
import android.widget.LinearLayout;

import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;

import java.util.List;

public interface mapPresInterface {
    void getRoutes(Context context);
    void setRoutes(List<Route> routeList,Context context);

    void getRoutePoints(Context context);
    void setRoutePoints(List<RoutePoint> routePointList);

    void getIndexValues(Context context,int index);
    void setIndexValues(List<RoutePoint> routePointList);
}
