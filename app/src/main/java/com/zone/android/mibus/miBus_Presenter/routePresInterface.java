package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;

import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;

import java.util.List;

/**
 * Created by Inspiron on 07-09-2018.
 */

public interface routePresInterface {
    void getRoutes(Context context);
    void setRoutes(List<Route> routeList);

    void getRouteId(Context context, String routName);
    void setRouteId(List<Route> routeList);

    void getRoutePoints(Context context,String routeId);
    void getTableDelete(Context context);

    void setMessage(int msgId);
    void setError(int errorId);


}
