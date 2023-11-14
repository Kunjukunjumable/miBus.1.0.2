package com.zone.android.mibus.miBus_Utility;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.zone.android.mibus.miBus_Db.AppDatabase;
import com.zone.android.mibus.miBus_Entity.Mark;
import com.zone.android.mibus.miBus_Entity.Org;
import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Entity.User;
import com.zone.android.mibus.miBus_Presenter.PagerPresInterface;
import com.zone.android.mibus.miBus_Presenter.loginPresInterface;
import com.zone.android.mibus.miBus_Presenter.orgSelPresInter;
import com.zone.android.mibus.miBus_Presenter.routePresInterface;
import com.zone.android.mibus.miBus_View.mapViewInterface;
import com.zone.android.mibus.miBus_View.pagerViewInterface;
import com.zone.android.mibus.miBus_View.rolesSelViewInter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;


/**
 * Created by Inspiron on 29-01-2018.
 */

public class Methods {

    Context context;
    static boolean  netwrk;
    String url;

    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";
   static CallWebservice Callweb =        new CallWebservice();
  //static CallWebservice Callweb;


   static SharedPreferences lastTokenPreference,orgSelPreference;


    public Methods(Context context){
         this.context=context;
    }


    public synchronized static String id(Context context) {

            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(PREF_UNIQUE_ID, uniqueID);
                editor.commit();
            }

        return uniqueID;
    }



    public static void testLogin(final String userName, final String passWord, final loginPresInterface loginPresInterface, final Context context){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String url=Constants.GET_LOGIN;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("username", userName);
            postDataParams.put("password", passWord);
            postDataParams.put("hostname", Constants.HOST_NAME);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {
                Log.e("miskool","miskoolAccessTokenresp "+resp.toString());


                try{

                    JSONObject jsonObject = new JSONObject(resp.toString());

                    String status=jsonObject.getString("status");

                    if(status.contains("true")){

                        String accesstoken=jsonObject.getString("token");

                        SharedPreferences.Editor editor = lastTokenPreference.edit();
                        editor.putString("tokenNo",accesstoken);
                        editor.putInt("tokeInc",0);
                        editor.commit();


                        SharedPreferences.Editor editorstudent = studentPreference.edit();
                        editorstudent.putString("studentid", passWord);
                        editorstudent.putString("studentname",userName);
                        editorstudent.commit();

                      //  getRoutes(loginPresInterface,context);

                      //  getOrganizationList(loginPresInterface,context);
                        loginPresInterface.showMessage(Constants.PASS_SERVICE_TOKEN);





                    }else{

                        loginPresInterface.showError(Constants.ERROR_CREDENTIALS);

                    }

                }catch (Exception e){
                    e.printStackTrace();

                    loginPresInterface.showError(Constants.ERROR_SERVICE);
                }
            }

            @Override
            public void ErrorCallbak(String resp) {

                loginPresInterface.showError(Constants.ERROR_SERVICE);

            }
        });

    }


    public static void getOrganizationList(final loginPresInterface loginPresInterface, final Context context){

     //   deleteOrd(context);
        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");



        String url=Constants.GET_ORG_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {


                Log.e("getOrganizationList ", "getOrganizationList " + resp.toString());

                JSONObject jsonObject = new JSONObject(resp.toString());

                String status = jsonObject.getString("status");
                if (status.contains("true")) {

                    String array = jsonObject.getString("organisations");
                    JSONArray routeArray = new JSONArray(array);
                    int arraysize = routeArray.length();

                    for (int i=0;i<arraysize;i++){

                        Org org = new Org();

                        JSONObject jsonObject1=routeArray.getJSONObject(i);

                        String orgname=jsonObject1.getString("name");
                        String orgid=jsonObject1.getString("orgid");
                        String hostname=jsonObject1.getString("hostname");

                        org.setOrgId(orgid);
                        org.setHostName(hostname);
                        org.setOrgName(orgname);

                        insertOrgs(loginPresInterface,context,i,arraysize,org);
                    }

                }
            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });


    }

    public static void insertOrgs(final loginPresInterface loginPresInterface, final Context context, final int arrayIndex, final int arraSize, final Org org){

        new Thread(new Runnable() {
            @Override
            public void run() {

                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                appdb.org_dao().insertAll(org);
                if(arrayIndex==arraSize-1){
                    loginPresInterface.showMessage(Constants.PASS_SERVICE);
                }

            }
        }).start();
    }


    public static void deleteOrd(final Context context, final loginPresInterface loginPresInterface){
       new Thread(new Runnable() {
           @Override
           public void run() {


               AppDatabase appdb = AppDatabase.getAppDatabase(context);
               appdb.org_dao().DeleteOrg();

               loginPresInterface.showMessage(Constants.PASS_SERVICE_DEL);

           }
       }) .start();

    }

    public static void getRoutes(final  rolesSelViewInter rolesSelViewInter, final Context context){
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");

       // deleteRoute(context);
        String url=Constants.GET_ROUTES;
        JSONObject jsonObject=new JSONObject();

        try {
            jsonObject.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.e("tokeNumber ","tokeNumber "+token);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        Callweb.CallSendPostServices(url, jsonObject, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("miskool","miskoolAccessTokenresp "+resp.toString());

                try{

                    JSONObject jsonObject = new JSONObject(resp.toString());

                    String status=jsonObject.getString("status");
                    if( status.contains("true")){

                        String array=jsonObject.getString("route_driver");
                        JSONArray routeArray = new JSONArray(array);

                        int arraysize=routeArray.length();

                        if(routeArray.length()>0 && !routeArray.equals(null)){

                            for(int i=0;i<routeArray.length();i++){

                                JSONObject json2= routeArray.getJSONObject(i);
                                String route_id=json2.getString("route_id");
                                String route_name=json2.getString("route_name");

                                Route route=new Route();
                                route.setRouteId(route_id);
                                route.setRouteName(route_name);

                                insertRoutes(route,context,rolesSelViewInter,i,arraysize);


                            }

                        }else{
                            rolesSelViewInter.showMessage(Constants.NO_ROUTES);
                        }


                    }else{
                        rolesSelViewInter.showMessage(Constants.ERROR_SERVICE);
                    }



                }catch (Exception e){
                   e.printStackTrace();
                    rolesSelViewInter.showMessage(Constants.ERROR_SERVICE);

                }




            }

            @Override
            public void ErrorCallbak(String resp) {

                rolesSelViewInter.showMessage(Constants.ERROR_SERVICE);

            }
        });


    }

    public static void deleteRoute(final Context context, final rolesSelViewInter rolesSelViewInter){

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("delteTables1 ","delteTables1 ");
                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                appdb.route_dao().DeleteRoute();

                rolesSelViewInter.showMessage(Constants.PASS_VALIDATION);



            }
        }).start();
    }





    public static void insertRoutes(final Route route, final Context context, final rolesSelViewInter rolesSelViewInter, final int intex, final int arrayLength){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("insertTables1 ","insertTables1 ");


                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                appdb.route_dao().insertAll(route);

                if(intex==arrayLength-1){
                    //report back to login service

                    rolesSelViewInter.showMessage(Constants.PASS_SERVICE);
                }

            }
        }).start();

    }



      public static void deleteFromtables(final Context context){

       new Thread(new Runnable() {
           @Override
           public void run() {

               Log.e("delteTables1 ","delteTables1 ");
               AppDatabase appdb = AppDatabase.getAppDatabase(context);
               appdb.user_dao().DeleteToken();


           }
       }).start();
      }






    public static boolean checknewtwork( Context context)
    {
        // TODO Auto-generated method stub
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService( Context.CONNECTIVITY_SERVICE );
        // NetworkInfo ni = cm.getActiveNetworkInfo();
        NetworkInfo ni_wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo ni_mob = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);


        if ( ni_wifi != null && ni_wifi.isAvailable() && ni_wifi.isConnected()){
            netwrk = true;
        }
        else{
            if(ni_mob != null && ni_mob.isAvailable() && ni_mob.isConnected() )
            {
                netwrk = true;
            }
            else{
                netwrk = false;
            }


        }

        return netwrk;
    }


    public static boolean hasActiveInternetConnection( Context context )
    {
        Log.e( "", "hasActiveInternetConnection............." );
        if (checknewtwork(context) )
        {

            try
            {
                // HttpURLConnection urlc = (HttpURLConnection) (new
                // URL("http://www.google.com").openConnection());
                // String url_Update_preparedstock = "http://" + Ip_Add + Variables.str_GetBank_Details;
                //Log.e( "", "Checking Active network " + url_Update_preparedstock );
                HttpURLConnection urlc = (HttpURLConnection) ( new URL( "https://identity.01.via.zone/admin/"
                ).openConnection() );
                urlc.setRequestProperty( "User-Agent", "Test" );
                urlc.setRequestProperty( "Connection", "close" );
                urlc.setConnectTimeout( 1500 );
                urlc.connect();
                Log.e( "", "HttpURLConnection..setRequestProperty..........." );
                return ( urlc.getResponseCode() == 200 );
            }
            catch ( IOException e )
            {
                Log.e( "", "Error checking internet connection", e );
            }
        }
        else
        {
            Log.d( "", "No network available!" );
        }
        return false;
    }




    static String converISO8601stamp(String isoDate){

      //  String isodate="2018-03-11T16:57:04Z";
        String finalDate="";

        String isodate=isoDate;

        long timestamp = 0;

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {

            Date date = dateformat.parse(isodate);
            timestamp = date.getTime();

            // DateFormat sdf = new SimpleDateFormat("hh:mm:ss yyyy-MM-dd");

            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date netDat = new Date(timestamp);

             finalDate = dateFormatter.format(netDat);

            Log.e("netDate ", "netDate " + finalDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return finalDate;
    }

    public static   void copyFile(Context context)
    {
        try
        {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            /////////////////////////////////////////////////////////////////////////
            File outDir = new File( sd.getAbsolutePath() + File.separator + "MiBus" );
            if ( !outDir.isDirectory() )
            {
                outDir.mkdir();
            }


            OutputStreamWriter outStreamWriter = null;
            FileOutputStream outStream = null;

            File out;


            SimpleDateFormat sdf = new SimpleDateFormat( "dd-MM-yyyy" );
            SimpleDateFormat TimeFormat = new SimpleDateFormat( "HH:mm" );
            Calendar cal = Calendar.getInstance();
            String Currentdate = sdf.format( cal.getTime() );
            String CurrentTime = TimeFormat.format( cal.getTime() );

            // out = new File( new File( outDir.getAbsolutePath() ), "miskool_db" + "_" + Currentdate );

            out =  new File( outDir.getAbsolutePath());

            if ( out.exists() == false )
            {
                out.createNewFile();
            }


            if (sd.canWrite())
            {
                String currentDBPath = "//data//"+context.getPackageName()+"//databases//"+"mibus-database";

                String backupDBPath = "mibus-database";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(out, backupDBPath+"_"+Currentdate );

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }




               /* if(bool == true)
                {
                    Toast.makeText(Settings.this, "Backup Complete", Toast.LENGTH_SHORT).show();
                    bool = false;
                }*/
            }
        }
        catch (Exception e) {
            Log.w("Settings Backup", e);

            e.printStackTrace();
        }
    }

    public static void updateGPS(Context context){

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences locationPreference =context.getSharedPreferences(Constants.LOCATION_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences routePreferences =context.getSharedPreferences(Constants.ROUTE_SEL_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences lastIndexPreference =context.getSharedPreferences(Constants.LAST_INDEX, Context.MODE_PRIVATE);

        String latitude=locationPreference.getString("latitude","");
        String longitude=locationPreference.getString("longitude","");

        final String token=lastTokenPreference.getString("tokenNo","");

        String routeId=routePreferences.getString("routeId","");

        int lastIndex=lastIndexPreference.getInt("lastindex",0);

        String url=Constants.UPDATE_GPS;

        JSONObject jsonObject= new JSONObject();

        try{
            jsonObject.put("token",token);
            jsonObject.put("routeid",routeId);
            jsonObject.put("lat",latitude);
            jsonObject.put("lng",longitude);
            jsonObject.put("last_index",String.valueOf(lastIndex));


        }catch (Exception e){
            e.printStackTrace();
        }

        Log.e("updateGPSInp ","updateGPSInp "+jsonObject.toString());

        Callweb.CallSendPostServices(url, jsonObject, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("updateGPS ","updateGPS "+resp.toString());
            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }



    public static void testLogOut(Context context, final mapViewInterface mapViewInterface){

      SharedPreferences  loginPreference = context.getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);


        final String token=lastTokenPreference.getString("tokenNo","");
        final int tokenInc=lastTokenPreference.getInt("tokeInc",0)+1;

        String url=Constants.GET_LOGOUT;

        JSONObject jsonLogout = new JSONObject();

        try {
            jsonLogout.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Callweb.CallSendPostServices(url, jsonLogout, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("logoutresp ","logoutresp "+resp.toString());

                String response=resp.toString();
                mapViewInterface.logOut();

            }

            @Override
            public void ErrorCallbak(String resp) {


            }
        });

    }


    public static void testLogOutNew(Context context, final rolesSelViewInter mapViewInterface){

        SharedPreferences  loginPreference = context.getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);


        final String token=lastTokenPreference.getString("tokenNo","");
        final int tokenInc=lastTokenPreference.getInt("tokeInc",0)+1;

        String url=Constants.GET_LOGOUT;

        JSONObject jsonLogout = new JSONObject();

        try {
            jsonLogout.put("token",token);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Callweb.CallSendPostServices(url, jsonLogout, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("logoutresp ","logoutresp "+resp.toString());

                String response=resp.toString();
                mapViewInterface.logOut();

            }

            @Override
            public void ErrorCallbak(String resp) {


            }
        });

    }

    public static void getRoutePoints(final Context context, String routeId, final routePresInterface routePresInterface){

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        final String token=lastTokenPreference.getString("tokenNo","");

        String url=Constants.GET_ALL_ROUTE_POINTS;

        JSONObject jsonObject1 = new JSONObject();

        try{

            jsonObject1.put("routeid",routeId);
            jsonObject1.put("token",token);

        }catch (Exception e){
            e.printStackTrace();
        }

        Callweb.CallSendPostServices(url, jsonObject1, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

              try{
                  Log.e("getRoutePoints ","getRoutePoints "+resp.toString());

                  JSONObject jsonObject = new JSONObject(resp.toString());

                  String status=jsonObject.getString("status");
                  if( status.contains("true")) {

                      String array = jsonObject.getString("routepoints");
                      JSONArray routeArray = new JSONArray(array);

                      int arraysize = routeArray.length();

                      if(routeArray.length()>0 && !routeArray.equals(null)){

                          for (int i=0;i<routeArray.length();i++) {
                              JSONObject jsonObject2 = routeArray.getJSONObject(i);


                              String lng = jsonObject2.getString("lng");
                              int index = Integer.parseInt(jsonObject2.getString("index"));
                              String pointid = jsonObject2.getString("pointid");
                              String name = jsonObject2.getString("name");
                              String pkey = jsonObject2.getString("pkey");
                              String lat = jsonObject2.getString("lat");

                              RoutePoint routePoint = new RoutePoint();
                              routePoint.setRouteLong(lng);
                              routePoint.setRouteIndex(index);
                              routePoint.setPointId(pointid);
                              routePoint.setRouteName(name);
                              routePoint.setRouteKey(pkey);
                              routePoint.setRouteLat(lat);

                              insertRoutePoints(context, routePoint, routePresInterface, i, arraysize);


                          }



                      }else{
                          routePresInterface.setError(Constants.ERROR_SERVICE);
                      }


                  }

              }catch (Exception e){
                  e.printStackTrace();
              }
            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });




    }


    public static void deleteRoutePoints(final Context context, final routePresInterface routePresInterface){

        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.e("delteTables1 ","delteTables1 ");
                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                appdb.route_pointDao().DeleteRoute();

                routePresInterface.setMessage(Constants.PASS_VALIDATION);


            }
        }).start();
    }


    public static void insertRoutePoints(final Context context, final RoutePoint routePoint, final routePresInterface routePresInterface, final int intext, final int arraySize){

        new Thread(new Runnable() {
            @Override
            public void run() {

                AppDatabase appdb = AppDatabase.getAppDatabase(context);
                appdb.route_pointDao().insertAll(routePoint);

                if(intext==arraySize-1){
                    routePresInterface.setMessage(Constants.PASS_SERVICE);
                }


            }
        }).start();

    }

    public static void getRoles(final Context context, String hostName, final orgSelPresInter orgSelPresInter){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");



        String url=Constants.GET_ROLES_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("hostname", hostName);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());


        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {


                Log.e("getRoles ","getRoles "+resp.toString());

                JSONObject jsonResposne= new JSONObject(resp.toString());

                String status=jsonResposne.getString("status");
                if(status.contains("true")){


                    String jsonString =jsonResposne.getString("new_data");
                    JSONObject jsonnew_data=new JSONObject(jsonString);

                    String array = jsonnew_data.getString("roles");
                    JSONArray rolesArray = new JSONArray(array);
                    int arraysize = rolesArray.length();

                    ArrayList<String> rolesList=new ArrayList<>();

                    for(int i=0;i<arraysize;i++){


                        String name=rolesArray.get(i).toString();
                        rolesList.add(name);


                    }

                    //getting new token

                    String new_token=jsonnew_data.getString("new_token");

                    SharedPreferences.Editor editor = lastTokenPreference.edit();
                    editor.putString("tokenNo",new_token);
                    editor.putInt("tokeInc",0);
                    editor.commit();

                    saveArrayList(rolesList,"rolelist",context,orgSelPresInter);


                }

            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });
    }


    public static void saveArrayList(ArrayList<String> list, String key, Context context,orgSelPresInter orgSelPresInter){
        SharedPreferences prefs =context.getSharedPreferences(Constants.ROLES_PREFERENCE,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();

        orgSelPresInter.showMessage(Constants.PASS_SERVICE);// This line is IMPORTANT !!!
    }

    public static void getCourseList(final Context context, final PagerPresInterface pagerPresInterface){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");

        orgSelPreference= context.getSharedPreferences(Constants.ORG_SEL_PREFERENCE,Context.MODE_PRIVATE);

String hostname=orgSelPreference.getString("hostName","");

        String url=Constants.GET_COURSE_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);


            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

                Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("courses");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String pkey = jsonObject.getString("pkey");

                        hashMap_Course.put(name,pkey);


                    }

                    pagerPresInterface.setCourseList(hashMap_Course);
                }
            }
            @Override
            public void ErrorCallbak(String resp) {

            }
        });



    }

    public static void getCourseBatchList(final Context context,String courseId, final PagerPresInterface pagerPresInterface) {

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_BATCH_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("courseid", courseId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());


        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("getCourseBatchListNew ", "getCourseBatchListNew " + resp.toString());

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

              //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("coursebatchs");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String name = jsonObject.getString("batchname");
                        String pkey = jsonObject.getString("pkey");

                        hashMap_Course.put(name,pkey);


                    }

                    pagerPresInterface.setCourseBatchList(hashMap_Course);
                }

            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });
    }

    public static void getCourseBatchTermList(final Context context,String courseBatchId, final PagerPresInterface pagerPresInterface){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_BATCH_TERM_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchid", courseBatchId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("getCourseBatchList ", "getCourseBatchList " + resp.toString());

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("coursebatchterms");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String name = jsonObject.getString("termname");
                        String pkey = jsonObject.getString("pkey");

                        hashMap_Course.put(name,pkey);


                    }

                    pagerPresInterface.setCourseBatchTermList(hashMap_Course);
                }

            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }


    public static void getCourseBatchExamList(final Context context,String courseBatchId, final PagerPresInterface pagerPresInterface){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_BATCH_EXAM_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchid", courseBatchId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params","paramssss"+postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("getCourseBatchList ", "getCourseBatchList " + resp.toString());

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("examgroups");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String pkey = jsonObject.getString("pkey");

                        hashMap_Course.put(name,pkey);


                    }

                    pagerPresInterface.setCourseBatchExamList(hashMap_Course);
                }

            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }


    public static void getCourseBatchTermDivList(final Context context,String courseBatchId, final PagerPresInterface pagerPresInterface) {

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_BATCH_TERM_DIV_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermid", courseBatchId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("getCourBathTermDivList", "getCourBathTermDivList " + resp.toString());

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("coursebatchtermdivisions");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String name = jsonObject.getString("divisionname");
                        String pkey = jsonObject.getString("pkey");

                        hashMap_Course.put(name,pkey);


                    }

                    pagerPresInterface.setCourseBatchTermDivList(hashMap_Course);
                }






            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });


    }

    public static void getSubList(final Context context,String courseBatchId, final PagerPresInterface pagerPresInterface) {
        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_BATCH_TERM_DIV_SUB_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionid", courseBatchId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("getSubList ", "getSubList " + resp.toString());

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("coursebatchtermdivisionsubjects");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String name = jsonObject.getString("name");
                        String pkey = jsonObject.getString("pkey");

                        hashMap_Course.put(name,pkey);


                    }

                    pagerPresInterface.setSubList(hashMap_Course);
                }


            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });


    }

    public static void getDivStdList(final Context context,String courseBatchId, final PagerPresInterface pagerPresInterface) {
        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_DIV_STUDENTS_FULL;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionid", courseBatchId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("params",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("getCourseBatchList ", "getCourseBatchList " + resp.toString());

                Log.e("getSubList ", "getSubList " + resp.toString());

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                   // String courses = jsonResposne.getString("coursebatchtermdivisionstudents");
                    String courses = jsonResposne.getString("students");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String firstname = jsonObject.getString("firstname");
                        String lastname = jsonObject.getString("lastname");
                        String name = jsonObject.getString("username");
                        String pkey = jsonObject.getString("roll_no");

                        hashMap_Course.put(pkey,pkey);


                    }

                    pagerPresInterface.setDivStdList(hashMap_Course);
                }



            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });



    }


    public static void getDivScheduleList(final Context context,String courseBatchId, final PagerPresInterface pagerPresInterface){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_BATCH_TERM_DIV_SCHE_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionid", courseBatchId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("SubScheduleListparams",postDataParams.toString());

        Log.e("getDivScheduleList ", "getDivScheduleList ");


        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {


                Log.e("getDivScheduleList ", "getDivScheduleList " + resp.toString());

                Log.e("getSubList ", "getSubList " + resp.toString());

              List<String> schedules=new ArrayList<String>();
                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("schedules");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                       String schedule=rolesArray.get(i).toString();
                       schedules.add(schedule);


                    }

                    pagerPresInterface.setDivScheduleList(schedules);
                }



            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }

    public static void getSubScheduleList(final Context context,String courseBatchId, final PagerPresInterface pagerPresInterface) {

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");


        String url=Constants.GET_BATCH_TERM_DIV_SUB_SCHE_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionsubjectid", courseBatchId);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("SubScheduleListparams",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("getCourseBatchList ", "getCourseBatchList " + resp.toString());

                List<String> schedules=new ArrayList<String>();
                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("schedules");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        String schedule=rolesArray.get(i).toString();
                        schedules.add(schedule);


                    }

                    pagerPresInterface.setSubScheduleList(schedules);
                }



            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });
    }



    public static void markDivAttendance(Context context, final pagerViewInterface pagerViewInterface, String absentees){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

       SharedPreferences courseselpreference= context.getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");
        String coursebatchtermdivisionid=courseselpreference.getString("coursebatchtermdivisionid","");
        String schedulestring_key=courseselpreference.getString("selectschedid","");



        String url=Constants.MARK_DIV_ABSENCE;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionid", coursebatchtermdivisionid);
            postDataParams.put("schedulestring_key", schedulestring_key);
            postDataParams.put("absenties", absentees);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("SubScheduleListparams",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("markDivAttendance ","markDivAttendance "+resp.toString());
                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {
                    pagerViewInterface.setMessage(Constants.PASS_SERVICE);
                }


            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }



    public static void markSubAttendance(Context context, final pagerViewInterface pagerViewInterface, String absentees){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        SharedPreferences courseselpreference= context.getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");
        String coursebatchtermdivisionsubjectid=courseselpreference.getString("coursebatchtermdivisionsubjectid","");
        String schedulestring_key=courseselpreference.getString("selectschedid","");



        String url=Constants.MARK_SUB_ABSENCE;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionsubjectid", coursebatchtermdivisionsubjectid);
            postDataParams.put("schedulestring_key", schedulestring_key);
            postDataParams.put("absenties", absentees);

            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("SubScheduleListparams",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("markDivAttendance ","markDivAttendance "+resp.toString());

                Log.e("markDivAttendance ","markDivAttendance "+resp.toString());
                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {
                    pagerViewInterface.setMessage(Constants.PASS_SERVICE);
                }

            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }

    public static void addDiaryNote( Context context,final pagerViewInterface pagerViewInterface,String subject,String text){
        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        SharedPreferences courseselpreference= context.getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");
        String coursebatchtermdivisionid=courseselpreference.getString("coursebatchtermdivisionid","");

        String url=Constants.ADD_DIARY;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionid", coursebatchtermdivisionid);
            postDataParams.put("subject", subject);
            postDataParams.put("text", text);
            postDataParams.put("notify", "false");


            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("SubScheduleListparams",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("SubScheduleListparams",resp.toString());
                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {
                    pagerViewInterface.setMessage(Constants.DAIRY_ADD);
                }
            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }


    public static void createExam(final Context context,String schedid, final pagerViewInterface pagerViewInterface,String examId) {

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        SharedPreferences courseselpreference= context.getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");
        String coursebatchtermdivisionsubjectid=courseselpreference.getString("coursebatchtermdivisionsubjectid","");
        String coursebatchexamgroupid =courseselpreference.getString("coursebatchexamgroupid","");


        String url=Constants.CREATE_EXAM;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionsubjectid", coursebatchtermdivisionsubjectid);
            postDataParams.put("coursebatchexamgroupid", coursebatchexamgroupid);
            postDataParams.put("schedule_string", schedid);



            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("createExam1",postDataParams.toString());
        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {
                Log.e("SubScheduleListparams",resp.toString());
                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {
                   String exam=jsonResposne.getString("exam");
                    pagerViewInterface.setMessageNew(exam);
                }

            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }

    public static void getExamType(final pagerViewInterface pagerViewInterface){

        HashMap<String, String> hashMap_Course = new HashMap<String, String>();

       hashMap_Course.put("Test","true");
       hashMap_Course.put("Exam","false");


        List<String> courseList= new ArrayList<>();
        courseList.add(0,"Type");
        courseList.add(1,"Exam");
        courseList.add(2,"Test");
        pagerViewInterface.setSubTypelist(hashMap_Course);


    }

    public static void getExamList(final Context context, final pagerViewInterface pagerViewInterface,String coursebatchtermdivisionsubjectid, String type) {

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        SharedPreferences courseselpreference= context.getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");
      //  String coursebatchtermdivisionsubjectid=courseselpreference.getString("coursebatchtermdivisionsubjectid","");


        String url=Constants.EXAM_LIST;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("coursebatchtermdivisionsubjectid", coursebatchtermdivisionsubjectid);

            //homeworktype
            postDataParams.put("homeworktype", type);


            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("createExam",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("createExam",resp.toString());

                HashMap<String, String> hashMap_Course = new HashMap<String, String>();

                //  Log.e("getCourseList ", "getCourseList " + resp.toString());

                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {


                    String courses = jsonResposne.getString("exams");

                    JSONArray rolesArray = new JSONArray(courses);

                    for (int i = 0; i < rolesArray.length(); i++) {

                        JSONObject jsonObject = rolesArray.getJSONObject(i);

                        String name = jsonObject.getString("examgroupname");
                        String schedString=jsonObject.getString("schedule_string");
                      //  String combined=schedString.substring(0,);
                        String finalS=name;

                        String pkey = jsonObject.getString("pkey");

                        hashMap_Course.put(finalS,pkey);


                    }

                    pagerViewInterface.setCoursebatchtermdivisionsubjectExamList(hashMap_Course);
                }

            }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }

    public static void markExam(final Context context, final pagerViewInterface pagerViewInterface, String coursebatchtermdivisionsubjectexamid, ArrayList<Mark> markList){

        final SharedPreferences studentPreference= context.getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        SharedPreferences courseselpreference= context.getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);
        lastTokenPreference = context.getSharedPreferences(Constants.LAST_TOKEN_PREFERENCE,Context.MODE_PRIVATE);

        String token = lastTokenPreference.getString("tokenNo", "");
        //  String coursebatchtermdivisionsubjectid=courseselpreference.getString("coursebatchtermdivisionsubjectid","");


        JSONArray jsonArray=new JSONArray();



        try{

            for (int i=0;i<markList.size();i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", markList.get(i).getpKey());
                jsonObject.put("mark_obtained", markList.get(i).getMark());
                jsonObject.put("notes", "N/A");

                jsonArray.put(jsonObject);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        //strigify json array

        String stringarray=jsonArray.toString();

        Log.e("stringarray","stringarray "+stringarray);

        String url=Constants.MARK_PAIRS;
        JSONObject postDataParams = new JSONObject();

        try {
            postDataParams.put("token", token);
            postDataParams.put("examid", coursebatchtermdivisionsubjectexamid);
            postDataParams.put("student_mark_pair", stringarray);
            postDataParams.put("published", "true");
            postDataParams.put("notify", "true");



            //hostname


        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.e("createExam",postDataParams.toString());

        Callweb.CallSendPostServices(url, postDataParams, new ServiceCallback() {
            @Override
            public void SuccessCallbak(String resp) throws JSONException {

                Log.e("createExam",resp.toString());
                JSONObject jsonResposne = new JSONObject(resp.toString());
                String status = jsonResposne.getString("status");
                if (status.contains("true")) {

                    pagerViewInterface.setMessage(Constants.PASS_SERVICE);

                }
                }

            @Override
            public void ErrorCallbak(String resp) {

            }
        });

    }
    }




