package com.zone.android.mibus.miBus_Presenter;


import android.content.Context;

import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Model.routeModelClass;
import com.zone.android.mibus.miBus_Model.routeModelInterface;
import com.zone.android.mibus.miBus_View.routeViewInterface;

import java.util.List;

/**
 * Created by Inspiron on 07-09-2018.
 */

public class routePresClass implements routePresInterface {

    routeViewInterface routeViewInterface;
    routeModelInterface routeModelInterface;


    public routePresClass(routeViewInterface routeViewInterface){

        this.routeViewInterface = routeViewInterface;

        this.routeModelInterface = new routeModelClass();

    }



    @Override
    public void getRoutes(Context context) {

        routeModelInterface.getRoutes(context,this);
    }

    @Override
    public void setRoutes(List<Route> routeList) {
         routeViewInterface.setRoutes(routeList);

    }

    @Override
    public void getRouteId(Context context, String routeName) {

        routeModelInterface.getRouteId(context,this,routeName);
    }

    @Override
    public void setRouteId(List<Route> routeList) {
        routeViewInterface.setRouteId(routeList);
    }



    @Override
    public void getRoutePoints(Context context, String routeId) {
       routeModelInterface.getRoutePoints(context,routeId,this);
    }

    @Override
    public void getTableDelete(Context context) {

        routeModelInterface.getTableDelete(context,this);
    }

    @Override
    public void setMessage(int msgId) {
        routeViewInterface.setMessage(msgId);

    }

    @Override
    public void setError(int errorId) {

        routeViewInterface.setError(errorId);
    }


}
