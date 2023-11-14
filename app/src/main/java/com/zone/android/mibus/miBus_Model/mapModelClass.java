package com.zone.android.mibus.miBus_Model;

import android.content.Context;
import android.util.Log;

import com.zone.android.mibus.miBus_Db.AppDatabase;
import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Presenter.mapPresInterface;

import java.util.List;

public class mapModelClass implements mapModelInterface {
    @Override
    public void getRoutes(final Context context, final mapPresInterface mapPresInterface) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Route> routeList;
                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                routeList = appdb.route_dao().getAll();

                mapPresInterface.setRoutes(routeList,context);

            }
        }).start();
    }

    @Override
    public void getRoutePoints(final Context context, final mapPresInterface mapPresInterface) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoutePoint> routeList;

                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                routeList = appdb.route_pointDao().getAll();

                mapPresInterface.setRoutePoints(routeList);

            }
        }).start();
    }

    @Override
    public void getIndexValues(final Context context, final mapPresInterface mapPresInterface, final int index) {
        new Thread(new Runnable() {
              @Override
              public void run() {


                  List<RoutePoint> routeList;

                  AppDatabase appdb = AppDatabase.getAppDatabase(context);
                  routeList = appdb.route_pointDao().getIndexVals(index);

                  Log.e("routeList ","routeList "+routeList.size());

                  mapPresInterface.setIndexValues(routeList);

       }
        }).start();

    }
}
