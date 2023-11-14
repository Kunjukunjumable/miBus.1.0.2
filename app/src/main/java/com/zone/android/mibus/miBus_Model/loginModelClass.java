package com.zone.android.mibus.miBus_Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.zone.android.mibus.miBus_Presenter.loginPresInterface;
import com.zone.android.mibus.miBus_Utility.Constants;
import com.zone.android.mibus.miBus_Utility.Methods;


/**
 * Created by Inspiron on 10-11-2017.
 */

public class loginModelClass implements loginModelInterface {


    @Override
    public void getToken(String userName, String passWord, String uud, loginPresInterface loginPresInterface, Context context) {
       // Methods.getToken(userName,passWord,uud,context);
    }

    @Override
    public void getLogin(String userName, String passWord, loginPresInterface loginPresInterface, Context context) {

         boolean isUserNameValid = Constants.isUserNameValid(userName);
         boolean isPassWordvalid = Constants.isPassWordValid(passWord);

        Log.e("OnClick Model ","OnClick Model");

         if(!isUserNameValid){

             Log.e("OnClickError Model ","OnClickError Model");
             loginPresInterface.showError(Constants.ERROR_USER_NAME_NULL);
         }

         else if(!isPassWordvalid){

             Log.e("OnClickError Model ","OnClickError Model");
             loginPresInterface.showError(Constants.ERROR_PASS_WORD_NULL);

         }
         else if(!isUserNameValid && !isPassWordvalid){
             loginPresInterface.showError(Constants.ERROR_USER_NAME_NULL);
         }
         else{

             loginPresInterface.showMessage(Constants.PASS_VALIDATION);

         }
    }

    @Override
    public void getAccessToken(String userName, String passWord, loginPresInterface loginPresInterface, Context context) {

        Methods.testLogin(userName,passWord,loginPresInterface,context);

    }


    @Override
    public void getServices(String userName, String passWord, loginPresInterface loginPresInterface, Context context) {

        SharedPreferences servicePreference = context.getSharedPreferences(Constants.SERVICE_STATUS_PREFERENCE, Context.MODE_PRIVATE);
      //  int lastcalledservice = Methods.serviceStausCheck(context);

        int lastcalledservice = servicePreference.getInt("service",0);

        if(lastcalledservice==0){
            /*int intStatusCode=  Methods.getToken(userName,passWord,Methods.id(context),context);

            switch(intStatusCode){

                      case 0:
                          loginPresInterface.showError(Constants.ERROR_SERVICE);
                          break;

                      case 1:
                          loginPresInterface.showMessage(Constants.ERROR_CREDENTIALS);
                          break;

                      case 2:

                          SharedPreferences.Editor editor = servicePreference.edit();
                          editor.putInt("service", 1);
                          editor.commit();
                        //  callRemainingServices(context,loginPresInterface);
                          break;*/

                //  }


          }else{

              //callRemainingServices(context,loginPresInterface);

          }

       }


    @Override
    public void getTestServices(String userName, String passWord, loginPresInterface loginPresInterface, Context context) {

        boolean isConnectionAvailable= Methods.hasActiveInternetConnection(context);

        if(isConnectionAvailable){

          //  Methods.getConfigRequst(context);
            loginPresInterface.showMessage(Constants.PASS_NETWORK);


        }else{
            loginPresInterface.showError(Constants.ERROR_NETWORK);
        }
    }

    @Override
    public void postLogin(String userName, String passWord, String url) {

    }

    @Override
    public void getTablesDelete(Context context, loginPresInterface loginPresInterface) {
        Methods.deleteOrd(context,loginPresInterface);
    }

    @Override
    public void getOrList(Context context, loginPresInterface loginPresInterface) {
        Methods.getOrganizationList(loginPresInterface,context);

    }

}
