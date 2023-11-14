package com.zone.android.mibus.miBus_Model;

import android.content.Context;

import com.zone.android.mibus.miBus_Db.AppDatabase;
import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Presenter.routePresInterface;
import com.zone.android.mibus.miBus_Utility.Methods;

import java.util.List;

/**
 * Created by Inspiron on 07-09-2018.
 */

public class routeModelClass implements routeModelInterface {

    @Override
    public void getRoutes(final Context context, final routePresInterface routePresInterface) {

     new Thread(new Runnable() {
         @Override
         public void run() {

             List<Route> routeList;
             AppDatabase appdb = AppDatabase.getAppDatabase(context);
             routeList = appdb.route_dao().getAll();

             routePresInterface.setRoutes(routeList);

         }
     }).start();

    }

    @Override
    public void getRouteId(final Context context, final routePresInterface routePresInterface, final String routeName) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Route> routeList;
                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                routeList = appdb.route_dao().selRouteId(routeName);
                routePresInterface.setRouteId(routeList);

            }
        }).start();
    }

    @Override
    public void getRoutePoints(Context context,String routeID, routePresInterface routePresInterface) {

       Methods.getRoutePoints(context,routeID,routePresInterface);
    }

    @Override
    public void getTableDelete(Context context, routePresInterface routePresInterface) {

        Methods.deleteRoutePoints(context,routePresInterface);
    }
}
