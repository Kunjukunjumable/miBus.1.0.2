package com.zone.android.mibus.miBus_View;

import android.content.Context;

import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;

import java.util.List;

public interface mapViewInterface {
    void setRoutes(List<Route> routeList, Context context);
    void setRoutePoints(List<RoutePoint> routePointList);

    void setIndexValues(List<RoutePoint> routePointList);
    void logOut();
}
