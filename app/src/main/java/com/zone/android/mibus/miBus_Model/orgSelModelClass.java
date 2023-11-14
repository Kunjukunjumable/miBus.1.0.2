package com.zone.android.mibus.miBus_Model;

import android.content.Context;

import com.zone.android.mibus.miBus_Db.AppDatabase;
import com.zone.android.mibus.miBus_Entity.Org;
import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Presenter.orgSelPresInter;
import com.zone.android.mibus.miBus_Utility.Methods;

import java.util.List;

public class orgSelModelClass implements orgSelModelInter {
    @Override
    public void getOrganizations(final Context context, final orgSelPresInter orgSelPresInter) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Org> orgList;
                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                orgList = appdb.org_dao().getAll();

                orgSelPresInter.setOrganizations(orgList);
            }
        }).start();

    }

    @Override
    public void getHostName(final Context context, final orgSelPresInter orgSelPresInter, final String orgName) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                List<Org> orgList;
                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                orgList = appdb.org_dao().selHostName(orgName);

                orgSelPresInter.setHostName(orgList);
            }
        }).start();

    }

    @Override
    public void getRoles(Context context, String hostName, orgSelPresInter orgSelPresInter) {
        Methods.getRoles(context,hostName,orgSelPresInter);
    }
}
