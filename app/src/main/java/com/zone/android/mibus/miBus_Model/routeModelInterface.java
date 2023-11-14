package com.zone.android.mibus.miBus_Model;

import android.content.Context;

import com.zone.android.mibus.miBus_Presenter.routePresInterface;

/**
 * Created by Inspiron on 07-09-2018.
 */

public interface routeModelInterface  {
    void getRoutes(Context context, routePresInterface routePresInterface);
    void getRouteId(Context context, routePresInterface routePresInterface, String routeName);

    void getRoutePoints(Context context,String routeId,routePresInterface routePresInterface);

    void getTableDelete(Context context,routePresInterface routePresInterface);
}
