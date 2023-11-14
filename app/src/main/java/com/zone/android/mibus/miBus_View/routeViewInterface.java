package com.zone.android.mibus.miBus_View;

import android.content.Context;

import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;

import java.util.List;

/**
 * Created by Inspiron on 07-09-2018.
 */

public interface routeViewInterface {
    void setRoutes(List<Route> routeList);
    void setRouteId(List<Route> routeList);

    void setRoutePoints(List<RoutePoint> routePointList);

    void setMessage(int msgId);
    void setError(int errorId);
}
