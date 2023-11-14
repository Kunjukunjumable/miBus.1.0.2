package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;

/**
 * Created by Inspiron on 13-11-2017.
 */

public interface loginPresInterface {

    //directing calls from view to model

    void getToken(String userName, String passWord, String uuid, Context context);

    void getLogin(String userName, String passWord, Context context);
    void getServices(String userName, String passWord, Context context);

    void getAccessToken(String userName, String passWord, Context context);

    void getTestServices(String userName, String passWord, Context context);


    void setNavigation();

    void getTablesDelete(Context context);
    void getOrList(Context context);

    void showError(int errorCode);
     void showMessage(int code);


}
