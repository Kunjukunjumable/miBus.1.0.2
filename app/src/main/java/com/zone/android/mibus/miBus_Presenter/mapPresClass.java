package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;

import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Model.mapModelClass;
import com.zone.android.mibus.miBus_Model.mapModelInterface;
import com.zone.android.mibus.miBus_View.mapViewInterface;

import java.util.List;

public class mapPresClass implements mapPresInterface {
    mapViewInterface mapViewInterface;
    mapModelInterface mapModelInterface;

    public mapPresClass(mapViewInterface mapViewInterface){
        this.mapViewInterface=mapViewInterface;
        this.mapModelInterface= new mapModelClass();
    }

    @Override
    public void getRoutes(Context context) {

        mapModelInterface.getRoutes(context,this);
    }

    @Override
    public void setRoutes(List<Route> routeList, Context context) {
        mapViewInterface.setRoutes(routeList,context);

    }

    @Override
    public void getRoutePoints(Context context) {
        mapModelInterface.getRoutePoints(context,this);

    }

    @Override
    public void setRoutePoints(List<RoutePoint> routePointList) {

        mapViewInterface.setRoutePoints(routePointList);

    }

    @Override
    public void getIndexValues(Context context, int index) {
        mapModelInterface.getIndexValues(context,this,index);
    }

    @Override
    public void setIndexValues(List<RoutePoint> routePointList) {
mapViewInterface.setIndexValues(routePointList);
    }


}
