package com.zone.android.mibus.miBus_View;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;


import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Presenter.routePresClass;
import com.zone.android.mibus.miBus_Presenter.routePresInterface;
import com.zone.android.mibus.miBus_Utility.Constants;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Inspiron on 07-09-2018.
 */

public class routeActivity extends Activity implements routeViewInterface {
    SharedPreferences routePreferences,isRouteSelectedPreference,lastIndexPreference;
    NiceSpinner spinner1; Button btnSave;
    TextView textName;
    routePresInterface routePresInterface;
    static String routeName, routeId;
     SharedPreferences studentPreference;

    static List<Route> routeListMain;
    public static ProgressDialog progressBar;






    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routeselection);

        spinner1=(NiceSpinner) findViewById(R.id.nice_spinner);
        btnSave=(Button)findViewById(R.id.btnSave);

        textName=(TextView)findViewById(R.id.textName);
        routePreferences=getApplicationContext().getSharedPreferences(Constants.ROUTE_SEL_PREFERENCE, Context.MODE_PRIVATE);

        isRouteSelectedPreference=getApplicationContext().getSharedPreferences(Constants.IS_ROUTE_SEL, Context.MODE_PRIVATE);
        studentPreference= getApplicationContext().getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        lastIndexPreference=getApplicationContext().getSharedPreferences(Constants.LAST_INDEX, Context.MODE_PRIVATE);


        routePresInterface=new routePresClass(this);

        String studentName=studentPreference.getString("studentname","");

        String upperString = studentName.substring(0,1).toUpperCase() + studentName.substring(1);


        textName.setText(upperString);

        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Downloading routes");
        progressBar.setIndeterminate(true);

        progressBar.show();

       routePresInterface.getRoutes(getApplicationContext());

       // String dbname=getApplicationContext().getDatabasePath(DataBaseHelper.dbName).toString;

        //Methods.copyFile(getApplicationContext());




      /*  List routes = new ArrayList();
        routes.add("Perumbavoor");
        routes.add("Kolenchery");
        routes.add("Muvattupuzha");
        routes.add("Aluva");

        List<String> dataset = new LinkedList<>(Arrays.asList("Perumbavoor", "Kolenchery", "Muvattupuzha", "Aluva", "Kothamangalam"));
        spinner1.attachDataSource(dataset);
        spinner1.attachDataSource(dataset);*/

      /*  ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,routes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner1.setAdapter(arrayAdapter);
*/


       spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {

              try{

                  String item = adapterView.getItemAtPosition(pos-1).toString();

                  routeName=item;
                 // routeId=routeListMain.get(pos-1).getRouteId().toString();

                 // routeId=routeListMain.get(pos-1).getRouteId().toString();

              }catch (Exception e){
                  e.printStackTrace();
              }



               // Showing selected spinner item
              // Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {


               progressBar = new ProgressDialog(routeActivity.this);
               progressBar.setCancelable(true);//you can cancel it by pressing back button
               progressBar.setMessage("Downloading route points");
               progressBar.setIndeterminate(true);

               progressBar.show();
               routePresInterface.getRouteId(getApplicationContext(),routeName);

           }
       });

    }

    @Override
    public void setRoutes(final List<Route> routeList) {

routeActivity.this.runOnUiThread(new Runnable() {
    @Override
    public void run() {

        routeListMain=routeList;

                routePreferences=getApplicationContext().getSharedPreferences(Constants.ROUTE_SEL_PREFERENCE, Context.MODE_PRIVATE);


                Log.e("routeList ","routeList "+routeList.size());
                List routes = new ArrayList();

               routeName =routeList.get(0).getRouteName();
              // routeId=routeList.get(0).getRouteId();



               /* SharedPreferences.Editor editor = routePreferences.edit();
                editor.putString("routeName", defRoute);
                editor.commit();
*/

              for(int i=0;i<routeList.size();i++){
                  routes.add(routeList.get(i).getRouteName());
              }


                List<String> dataset = new LinkedList<>(Arrays.asList("Perumbavoor", "Kolenchery", "Muvattupuzha", "Aluva", "Kothamangalam"));
                spinner1.attachDataSource(routes);
                progressBar.dismiss();


    }
});



    }

    @Override
    public void setRouteId(List<Route> routeList) {

        SharedPreferences.Editor editor = routePreferences.edit();
        editor.putString("routeName", routeList.get(0).getRouteName());
        editor.putString("routeId", routeList.get(0).getRouteId());
        editor.commit();


       routePresInterface.getTableDelete(getApplicationContext());
        //isRouteSelectedPreference


    }

    @Override
    public void setRoutePoints(List<RoutePoint> routePointList) {

    }

    @Override
    public void setMessage(int msgId) {


        if(msgId==Constants.PASS_SERVICE){

            SharedPreferences.Editor editor1=isRouteSelectedPreference.edit();
            editor1.putBoolean("IsRouteSel",true);
            editor1.commit();


            SharedPreferences.Editor editor = lastIndexPreference.edit();
            editor.putInt("lastindex", -1);
            editor.commit();

            Intent intent= new Intent(getApplicationContext(), MapsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

            progressBar.dismiss();

        }else if(msgId==Constants.PASS_VALIDATION){

            String routeId=routePreferences.getString("routeId","");
            routePresInterface.getRoutePoints(getApplicationContext(),routeId);
        }

    }

    @Override
    public void setError(int errorId) {

        if(errorId==Constants.ERROR_SERVICE){

            progressBar.dismiss();
        }
    }
}
