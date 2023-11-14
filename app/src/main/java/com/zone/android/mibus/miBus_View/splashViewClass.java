package com.zone.android.mibus.miBus_View;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Utility.Constants;


/**
 * Created by Inspiron on 24-01-2018.
 */

public class splashViewClass extends AppCompatActivity {

    SharedPreferences loginPreference,isRouteSelectedPreference;
    private static int SPLASH_TIME_OUT = 500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
     //   setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_splash);
        loginPreference=getApplicationContext().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);

        isRouteSelectedPreference=getApplicationContext().getSharedPreferences(Constants.IS_ROUTE_SEL, Context.MODE_PRIVATE);
       final boolean isLogin =loginPreference.getBoolean("islogin",false);

       final boolean isRouteSel=isRouteSelectedPreference.getBoolean("IsRouteSel",false);

        Log.e("islogin","islogin "+isLogin);


       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {


               if(isLogin){

                   Intent intent = new Intent(getApplicationContext(), rolesSelectionViewClass.class);
                   startActivity(intent);
                   finish();

                  /* if(isRouteSel) {


                       Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                       startActivity(intent);
                       finish();
                   }else{

                       Intent intent = new Intent(getApplicationContext(), routeActivity.class);
                       startActivity(intent);
                       finish();
                   }*/

               }else {

                   Intent intent = new Intent(getApplicationContext(), loginViewClass.class);
                   startActivity(intent);
                   finish();


               }

             /*  Intent intent = new Intent(getApplicationContext(), loginViewClass.class);
               startActivity(intent);
               finish();*/

           }
       },SPLASH_TIME_OUT);



    }
}
