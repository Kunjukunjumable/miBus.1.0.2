package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;
import android.util.Log;

import com.zone.android.mibus.miBus_Model.loginModelClass;
import com.zone.android.mibus.miBus_Model.loginModelInterface;
import com.zone.android.mibus.miBus_View.loginViewInterface;


/**
 * Created by Inspiron on 13-11-2017.
 */

public class loginPresClass implements loginPresInterface {
  loginViewInterface loginViewInterface;
  loginModelInterface loginModelInterface;



    public loginPresClass(loginViewInterface loginViewInterface){

        this.loginViewInterface = loginViewInterface;

        this.loginModelInterface = new loginModelClass();

    }



    @Override
    public void getToken(String userName, String passWord, String uuid, Context context) {

        loginModelInterface.getAccessToken(userName,passWord,loginPresClass.this,context);
    }

    @Override
    public void getLogin(String userName, String passWord, Context context) {
        Log.e("OnClick Presenter ","OnClick Presenter");
        loginModelInterface.getLogin(userName,passWord,this, context);
    }

    @Override
    public void getServices(String userName, String passWord, Context context) {

    }

    @Override
    public void getAccessToken(String userName, String passWord, Context context) {

        loginModelInterface.getAccessToken(userName,passWord,loginPresClass.this,context);
    }

    @Override
    public void getTestServices(String userName, String passWord, Context context) {

    }

    @Override
    public void setNavigation() {

    }

    @Override
    public void getTablesDelete(Context context) {
        loginModelInterface.getTablesDelete(context,this);
    }

    @Override
    public void getOrList(Context context) {
        loginModelInterface.getOrList(context,this);

    }

    @Override
    public void showError(int errorCode) {

        loginViewInterface.showError(errorCode);
    }

    @Override
    public void showMessage(int code) {
        loginViewInterface.showMessage(code);

    }
}
