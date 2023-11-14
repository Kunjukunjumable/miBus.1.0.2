package com.zone.android.mibus.miBus_Model;

import android.content.Context;

import com.zone.android.mibus.miBus_Presenter.loginPresInterface;


/**
 * Created by Inspiron on 13-11-2017.
 */

public interface loginModelInterface {

    void getToken(String userName, String passWord, String uud, loginPresInterface loginPresInterface, Context context);

    void getLogin(String userName, String passWord, loginPresInterface loginPresInterface, Context context);

    void getAccessToken(String userName, String passWord, loginPresInterface loginPresInterface, Context context);

    void getServices(String userName, String passWord, loginPresInterface loginPresInterface, Context context);

    void getTestServices(String userName, String passWord, loginPresInterface loginPresInterface, Context context);
    void postLogin(String userName, String passWord, String url);

    void getTablesDelete(Context context,loginPresInterface loginPresInterface);

    void getOrList(Context context,loginPresInterface loginPresInterface);
}
