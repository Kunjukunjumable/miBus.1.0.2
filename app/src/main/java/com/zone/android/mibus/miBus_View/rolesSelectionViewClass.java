package com.zone.android.mibus.miBus_View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Adapter.rolesadapter;
import com.zone.android.mibus.miBus_Utility.Constants;
import com.zone.android.mibus.miBus_Utility.ContentFragment;
import com.zone.android.mibus.miBus_Utility.Methods;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class rolesSelectionViewClass extends AppCompatActivity implements rolesSelViewInter, ViewAnimator.ViewAnimatorListener ,Toolbar.OnMenuItemClickListener{

    SharedPreferences studentPreference,orgSelPreference,loginPreference;

    TextView textName,textOrg;
    GridView grid;

    public static ProgressDialog progressBar;

    //yalantis side menu

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_roleselection);





        textName=(TextView)findViewById(R.id.textName);
        textOrg=(TextView)findViewById(R.id.textOrg);
        grid=(GridView)findViewById(R.id.grid);

        contentFragment = ContentFragment.newInstance(R.drawable.content_music);
       /* getSupportFragmentManager().beginTransaction()
                .replace(R.id.sliding_layout, contentFragment)
                .commit();
*/
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



        drawerLayout.setScrimColor(Color.TRANSPARENT);

        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(rolesSelectionViewClass.this, list, contentFragment, drawerLayout, this);



        loginPreference = getApplicationContext().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);


        studentPreference= getApplicationContext().getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        orgSelPreference= getApplicationContext().getSharedPreferences(Constants.ORG_SEL_PREFERENCE,Context.MODE_PRIVATE);

  String studentName=studentPreference.getString("studentname","");

        String upperString = studentName.substring(0,1).toUpperCase() + studentName.substring(1);

        textName.setText(upperString);


        String orgname=orgSelPreference.getString("orgName","");

        String upperString1 = orgname.substring(0,1).toUpperCase() + orgname.substring(1);

        textOrg.setText(upperString1);


        ArrayList<String> rolesList=new ArrayList<>();
        rolesList=getArrayList("rolelist");

        Log.e("rolelist ","rolelist "+ rolesList.size());

        rolesadapter rolesadapter = new rolesadapter(getApplicationContext(),rolesList);
        grid.setAdapter(rolesadapter);

        final ArrayList<String> finalRolesList = rolesList;

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              //  Toast.makeText(getApplicationContext(), "You Clicked at " + finalRolesList.get(position), Toast.LENGTH_SHORT).show();

       String role=finalRolesList.get(position);

       if(role.contains("TEACHER")){

           Intent intent =new Intent(rolesSelectionViewClass.this,teacherViewClass.class);
           startActivity(intent);
       }else if(role.contains("DRIVER")){

           //need to get routs

           progressBar = new ProgressDialog(rolesSelectionViewClass.this);
           progressBar.setCancelable(true);//you can cancel it by pressing back button
           progressBar.setMessage("Loading routes");
           progressBar.setIndeterminate(true);

           progressBar.show();

           Methods.deleteRoute(getApplicationContext(),rolesSelectionViewClass.this);


       }


            }
        });


    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = getSharedPreferences(Constants.ROLES_PREFERENCE,Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(key, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);

        /*SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.SHOP, R.drawable.ic_account);
        list.add(menuItem5);

        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.ic_set);
        list.add(menuItem2);
     *//*   SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.PAINT, R.drawable.icn_3);
        list.add(menuItem3);*//*
         */
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.LOGOUT, R.drawable.icn_logout);
        list.add(menuItem);

        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.SWITCH_ROUTE, R.drawable.ic_ref);
        list.add(menuItem4);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }



    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
                return screenShotable;

            case ContentFragment.LOGOUT:
                // Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_LONG).show();
                confirmLogout();
                return screenShotable;



            case ContentFragment.SWITCH_ROUTE:
                // Toast.makeText(getApplicationContext(),"hii",Toast.LENGTH_LONG).show();
                // confirmLogout();

                return screenShotable;


        }
        return null;
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }



    public void confirmLogout(){

// custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_logout);

        TextView textName = (TextView) dialog.findViewById(R.id.txtNme);
        ImageView image = (ImageView) dialog.findViewById(R.id.imgstudent);
        image.setImageResource(R.drawable.imgb);

        TextView btnLogout = (TextView) dialog.findViewById(R.id.btnLogout);
        TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
        dialog.show();
        dialog.setCancelable(false);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.testLogOutNew(getApplicationContext(),rolesSelectionViewClass.this);

                progressBar = new ProgressDialog(rolesSelectionViewClass.this);
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("Logging out");
                progressBar.setIndeterminate(true);

                progressBar.show();
                dialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void logOut() {

        progressBar.dismiss();
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putBoolean("islogin", false);
        editor.commit();


        Log.e("logoutcalled","logoutcalled ");
        Intent intent = new Intent(rolesSelectionViewClass.this, loginViewClass.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMessage(int message) {
        if(message==Constants.PASS_VALIDATION){

            Methods.getRoutes(this,getApplicationContext());
        }else if(message==Constants.PASS_SERVICE){
            progressBar.dismiss();
            Intent intent =new Intent(rolesSelectionViewClass.this,routeActivity.class);
            startActivity(intent);

        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }



}
