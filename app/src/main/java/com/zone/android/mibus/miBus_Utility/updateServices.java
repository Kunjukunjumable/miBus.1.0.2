package com.zone.android.mibus.miBus_Utility;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;


/**
 * Created by Inspiron on 09-06-2018.
 */

public class updateServices extends IntentService {

    SharedPreferences  updatePreferences;
    SharedPreferences locationPreferences;


    public updateServices() {
        super("updateServices");
    }

    public updateServices(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        updatePreferences = getApplicationContext().getSharedPreferences("update_location_preference",0);

        locationPreferences=getApplicationContext().getSharedPreferences(Constants.LOCATION_PREFERENCE,0);


Methods.updateGPS(getApplicationContext());


    }

    /*public  void updatePosition(){

        String  REQUEST_TAG = "volleyStringRequest";

        Log.e("updatePosition  ", "updatePosition");

        String url = updatePreferences.getString("url","null");

        Log.e("updatePosition  ", "updatePosition "+url);
        StringRequest strReq = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("TagStringrsp  ", response.toString());

                try {



                }catch (Exception e){
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("TagStringrsp  ", "Error: " + error.getMessage());
            }
        });
        // Adding String request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq, REQUEST_TAG);
    }

*/
}
