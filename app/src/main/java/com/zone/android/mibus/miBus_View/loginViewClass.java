package com.zone.android.mibus.miBus_View;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Presenter.loginPresClass;
import com.zone.android.mibus.miBus_Presenter.loginPresInterface;
import com.zone.android.mibus.miBus_Utility.Constants;


/**
 * Created by Inspiron on 05-09-2018.
 */

public class loginViewClass extends AppCompatActivity implements loginViewInterface {
    EditText edit_username, edit_password;
    Button btn_login;

   loginPresInterface loginPresInterface;

    ScrollView scrolLayout;
    public static ProgressDialog progressBar;
    SharedPreferences loginPreference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logintest);

        loginPresInterface = new loginPresClass(this);
        loginPreference=getApplicationContext().getSharedPreferences(Constants.LOGIN_PREFERENCE, Context.MODE_PRIVATE);


        scrolLayout =(ScrollView)findViewById(R.id.scrolLayout) ;

        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password = (EditText) findViewById(R.id.edit_password);
        btn_login = (Button) findViewById(R.id.btn_login);

             onclickListneres();

    }


    void onclickListneres() {

        //enababling setonclick listener
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edit_username.getText().toString();
                String passWord = edit_password.getText().toString();

                loginPresInterface.getLogin(userName, passWord, getApplicationContext());


            }
        });

    }


    void nextActivity() {
        SharedPreferences.Editor editor = loginPreference.edit();
        editor.putBoolean("islogin", true);
        editor.commit();


        Intent intent = new Intent(loginViewClass.this, orgSelectionViewcClass.class);
        // intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        progressBar.dismiss();
        startActivity(intent);
        finish();


    }

    @Override
    public void setNavigation() {

    }

    @Override
    public void showMessage(int code) {

     if(code== Constants.PASS_VALIDATION) {

         progressBar = new ProgressDialog(this);
         progressBar.setCancelable(true);//you can cancel it by pressing back button
         progressBar.setMessage("Downloading organizations");
         progressBar.setIndeterminate(true);

         progressBar.show();

         String userName = edit_username.getText().toString();
         String passWord = edit_password.getText().toString();

         loginPresInterface.getAccessToken(userName, passWord, getApplicationContext());

     }else if(code==Constants.PASS_SERVICE_TOKEN){

         loginPresInterface.getTablesDelete(getApplicationContext());
     }

     else if(code==Constants.PASS_SERVICE_DEL){

         loginPresInterface.getOrList(getApplicationContext());
     }


     else if(code==Constants.PASS_SERVICE){

         nextActivity();
     }



    }



    @Override
    public void showError(int errorCode) {

        switch (errorCode) {
            case Constants.ERROR_USER_NAME_NULL:

                // editUser.setError("enter username");
                edit_username.requestFocus();
                return;

            case Constants.ERROR_PASS_WORD_NULL:
                // editPassword.setError("enter password");
                edit_password.requestFocus();
                return;

            case Constants.ERROR_NETWORK:
                //editPassword.setError("enter password");
                //need to show network error......
                return;
            case Constants.ERROR_CREDENTIALS:
                //editPassword.setError("enter password");
                //need to show network error......
                toastMessage("Check credentials");

                edit_username.setText("");
                edit_password.setText("");
                edit_username.requestFocus();

               progressBar.dismiss();

                return;

            case Constants.ERROR_SERVICE:
                progressBar.dismiss();
                toastMessage("Service Error");

                return;

            case Constants.NO_ROUTES:
                progressBar.dismiss();
                toastMessage("No Routes Assigned");
                return;

            default:
                return;

        }
    }

    void toastMessage(String message){

        Snackbar snackbar = Snackbar
                .make(scrolLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();

    }


}
