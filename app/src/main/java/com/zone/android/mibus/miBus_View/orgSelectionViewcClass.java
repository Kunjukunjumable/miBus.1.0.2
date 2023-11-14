package com.zone.android.mibus.miBus_View;

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
import android.widget.GridView;
import android.widget.TextView;

import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Entity.Org;
import com.zone.android.mibus.miBus_Presenter.orgSelPresClass;
import com.zone.android.mibus.miBus_Presenter.orgSelPresInter;
import com.zone.android.mibus.miBus_Utility.Constants;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class orgSelectionViewcClass extends AppCompatActivity implements orgSelectionViewInter {

    SharedPreferences studentPreference,orgSelPreference;

    public static ProgressDialog progressBar;
    orgSelPresInter orgSelPresInter;

    TextView textName;
NiceSpinner nice_spinner;

public  static String orgName;

public static List<Org> finalOrgList;

Button btnSave;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_orgselection);

        textName=(TextView)findViewById(R.id.textName);
        nice_spinner=(NiceSpinner)findViewById(R.id.nice_spinner);

        btnSave=(Button)findViewById(R.id.btnSave);

        studentPreference= getApplicationContext().getSharedPreferences(Constants.STUDENT_SELECTION_PREFERENCE,Context.MODE_PRIVATE);

        orgSelPreference= getApplicationContext().getSharedPreferences(Constants.ORG_SEL_PREFERENCE,Context.MODE_PRIVATE);

        String studentName=studentPreference.getString("studentname","");

        String upperString = studentName.substring(0,1).toUpperCase() + studentName.substring(1);

        textName.setText(upperString);

        orgSelPresInter=new orgSelPresClass(this);


        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Downloading organizations");
        progressBar.setIndeterminate(true);

        progressBar.show();
        orgSelPresInter.getOrganization(getApplicationContext());



        nice_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                try{

                    String item = parent.getItemAtPosition(position-1).toString();

                    orgName=item;

                    Log.e("hostname ","hostname "+finalOrgList.get(position-1).getHostName().toString());

                }catch (Exception e){
                    e.printStackTrace();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressBar = new ProgressDialog(orgSelectionViewcClass.this);
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("Saving");
                progressBar.setIndeterminate(true);

                progressBar.show();
                orgSelPresInter.getHostName(getApplicationContext(),orgName);
            }
        });



    }

    @Override
    public void setOrganizations(final List<Org> organizations) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                orgName=organizations.get(0).getOrgName();

                List<Org> orgList = new ArrayList<>();

               Org org1= new Org();
               org1.setOrgName("St Mary's");
               org1.setHostName("www.stmarys.com");

               orgList.add(org1);



                Org org2= new Org();
                org2.setOrgName("St Pauls's");
                org2.setHostName("www.pauls.com");

                orgList.add(org2);

                finalOrgList=orgList;

                List<String> orhname= new ArrayList<>();

                for(int i=0;i<organizations.size();i++){
                    orhname.add(organizations.get(i).getOrgName());
                }


                List<String> dataset = new LinkedList<>(Arrays.asList("Perumbavoor", "Kolenchery", "Muvattupuzha", "Aluva", "Kothamangalam"));
                nice_spinner.attachDataSource(orhname);
                progressBar.dismiss();

            }
        });
    }

    @Override
    public void setHostName(final List<Org> organizations) {

runOnUiThread(new Runnable() {
    @Override
    public void run() {

       // progressBar.dismiss();
        SharedPreferences.Editor editor = orgSelPreference.edit();
        editor.putString("orgName", organizations.get(0).getOrgName());
        editor.putString("hostName", organizations.get(0).getHostName());
        editor.commit();

        Log.e("setHostName ","setHostName "+organizations.get(0).getHostName());
        orgSelPresInter.getRoles(getApplicationContext(),organizations.get(0).getHostName());



    }
});



    }

    @Override
    public void showMessage(int message) {

        if(message==Constants.PASS_SERVICE){


            Intent intent= new Intent(getApplicationContext(), rolesSelectionViewClass.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

            progressBar.dismiss();
        }
    }
}
