package com.zone.android.mibus.miBus_Adapter;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Entity.Person_det;
import com.zone.android.mibus.miBus_Presenter.PagerPresClass;
import com.zone.android.mibus.miBus_Presenter.PagerPresInterface;
import com.zone.android.mibus.miBus_Utility.Constants;
import com.zone.android.mibus.miBus_Utility.Methods;
import com.zone.android.mibus.miBus_View.pagerViewInterface;
import com.zone.android.mibus.miBus_View.teacherViewClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class examCreationFragment extends android.support.v4.app.Fragment implements pagerViewInterface {

    PagerPresInterface pagerPresInterface =new PagerPresClass(examCreationFragment.this);

    SharedPreferences courseselpreference;
    RecyclerView recyclerView;
    Button btn_login;
    Spinner course_spinner,  batch_spinner,term_spinner,div_spinner,sub_spinner,exe_spinner;

    HashMap<String, String> hashMap_Course = new HashMap<String, String>();

    public static ProgressDialog progressBar;
    private Context mContext;
    LinearLayout primaryLinear;
    TextView textStartDate,textStartTime,textEndDate,textEndTime;
    private int mYear, mMonth, mDay, mHour, mMinute,mSecond;
    EditText   examName;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        final View rootview= inflater.inflate(R.layout.activity_examcreation,container,false);


        courseselpreference= getContext().getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);

        final GridView dridabsent=(GridView)rootview.findViewById(R.id.gridabsent);
        primaryLinear=(LinearLayout)rootview.findViewById(R.id.primaryLinear);

        course_spinner=(Spinner)rootview.findViewById(R.id.course_spinner);

        textStartDate = (TextView) rootview.findViewById(R.id.textStartDate);
        textStartTime = (TextView) rootview.findViewById(R.id.textStartTime);

        textEndDate = (TextView) rootview.findViewById(R.id.textEndDate);
        textEndTime = (TextView) rootview.findViewById(R.id.textEndTime);
        examName=(EditText)rootview.findViewById(R.id.examName);


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm:ss");
        String currentDate = sdf.format(new Date());

        String currentTime = sdf1.format(new Date());

        textStartDate.setText(currentDate);
        textStartTime.setText(currentTime);

        textEndDate.setText(currentDate);
        textEndTime.setText(currentTime);


        // course_spinner.setOnItemSelectedListener(this);
        batch_spinner=(Spinner)rootview.findViewById(R.id.batch_spinner);
        term_spinner=(Spinner)rootview.findViewById(R.id.term_spinner);

        div_spinner=(Spinner)rootview.findViewById(R.id.div_spinner);
        sub_spinner=(Spinner)rootview.findViewById(R.id.sub_spinner);
        exe_spinner=(Spinner)rootview.findViewById(R.id.exe_spinner);

        pagerPresInterface.getCourseList(getContext());

        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("loading");
        progressBar.setIndeterminate(true);

        progressBar.show();

        btn_login=(Button)rootview.findViewById(R.id.btn_login);

        btn_login.setEnabled(false);
        btn_login.setBackgroundColor(Color.LTGRAY);


        SharedPreferences.Editor editor = courseselpreference.edit();
        editor.putString("selectschedid","");
        editor.putString("selectsubid","");
        editor.putString("coursebatchtermdivisionsubjectid","");
        editor.putString("coursebatchtermdivisionid","");
        editor.putString("coursebatchid","");

        //coursebatchexamgroupid

        editor.putString("coursebatchexamgroupid","");
        //selectsubid
        editor.commit();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String starttimeendtime=textStartDate.getText().toString()+" "+textStartTime.getText().toString();

                String endtimeendtime=textEndDate.getText().toString()+" "+textEndTime.getText().toString();
                String content=examName.getText().toString();
                String finaS=starttimeendtime+"-"+endtimeendtime;

                progressBar = new ProgressDialog(getContext());
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("Saving");
                progressBar.setIndeterminate(true);

                progressBar.show();

                String coursebatchexamgroupid=courseselpreference.getString("coursebatchexamgroupid","");

                if(isUserNameValid(content)) {
                    Methods.createExam(getContext(), finaS, examCreationFragment.this,coursebatchexamgroupid);
                }

            }
        });


        textStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String sDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");

                                Date date = null;

                                try {
                                    date = format1.parse(sDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //  System.out.println(format2.format(date));

                                textStartDate.setText(format2.format(date));

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });






        textStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                //  mSecond=c.get(Calendar.SECOND);
                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                textStartTime.setText(hourOfDay + ":" + minute+":"+"00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });

        textEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                String sDate=dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");

                                Date date = null;

                                try {
                                    date = format1.parse(sDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                //  System.out.println(format2.format(date));

                                textEndDate.setText(format2.format(date));


                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }
        });

        textEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),R.style.DialogTheme,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                textEndTime.setText(hourOfDay + ":" + minute+":"+"00");
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();


            }
        });


        return  rootview;
    }

    @Override
    public void setAttList(List<Person_det> attList) {

    }

    @Override
    public void setCourseList(final HashMap<String, String> courseList) {
        progressBar.dismiss();

        batch_spinner.setAdapter(null);
        term_spinner.setAdapter(null);
        div_spinner.setAdapter(null);
        sub_spinner.setAdapter(null);
        exe_spinner.setAdapter(null);

        List<String> courses = new ArrayList<String>();
        courses.add(0,"Course");
        for ( String key : courseList.keySet() ) {
            System.out.println( key );

            String key1=key;
            //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
            courses.add(key1);

        }


      try {
          ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courses);

          // Drop down layout style - list view with radio button
          dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

          // attaching data adapter to spinner
          course_spinner.setAdapter(dataAdapter);


          course_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
              @Override
              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                  String item = parent.getItemAtPosition(position).toString();

                  String courseId = courseList.get(item);

                  Log.e("mapkey ", "mapkey " + courseId);

                  if (!item.contains("Course")) {
                      pagerPresInterface.getCourseBatchList(getContext(), courseId);

                      progressBar = new ProgressDialog(getContext());
                      progressBar.setCancelable(true);//you can cancel it by pressing back button
                      progressBar.setMessage("loading");
                      progressBar.setIndeterminate(true);

                      progressBar.show();
                  }


              }

              @Override
              public void onNothingSelected(AdapterView<?> parent) {

              }
          });

      }catch (Exception e){
            e.printStackTrace();
      }


    }

    @Override
    public void setCourseBatchList(final HashMap<String, String> courseList) {

        progressBar.dismiss();
        term_spinner.setAdapter(null);
        div_spinner.setAdapter(null);
        sub_spinner.setAdapter(null);
        exe_spinner.setAdapter(null);

        List<String> courses = new ArrayList<String>();
        courses.add(0,"Batch");
        for ( String key : courseList.keySet() ) {
            System.out.println( key );

            String key1=key;
            //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
            courses.add(key1);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // attaching data adapter to spinner
        batch_spinner.setAdapter(dataAdapter);

        batch_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                String item = parent.getItemAtPosition(position).toString();

                String courseId=courseList.get(item);

                Log.e("mapkey1courseId ","mapkey1courseId "+courseId);

                if(!item.contains("Batch")){
                    pagerPresInterface.getCourseBatchTermList(getContext(),courseId);

                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(true);//you can cancel it by pressing back button
                    progressBar.setMessage("loading");
                    progressBar.setIndeterminate(true);

                    progressBar.show();

                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("coursebatchid",courseId);
                    editor.commit();
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public void setCourseBatchTermList(final HashMap<String, String> courseList) {

        progressBar.dismiss();

        div_spinner.setAdapter(null);
        sub_spinner.setAdapter(null);
        exe_spinner.setAdapter(null);

        List<String> courses = new ArrayList<String>();
        courses.add(0,"Term");
        for ( String key : courseList.keySet() ) {
            System.out.println( key );

            String key1=key;
            //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
            courses.add(key1);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // attaching data adapter to spinner
        term_spinner.setAdapter(dataAdapter);
        term_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                String courseId=courseList.get(item);

                Log.e("mapkey1 ","mapkey1 "+courseId);

                if(!item.equals("Term")){
                    pagerPresInterface.getCourseBatchTermDivList(getContext(),courseId);

                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(true);//you can cancel it by pressing back button
                    progressBar.setMessage("loading");
                    progressBar.setIndeterminate(true);

                    progressBar.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void setCourseBatchTermDivList(final HashMap<String, String> courseList) {
        progressBar.dismiss();

        sub_spinner.setAdapter(null);
        exe_spinner.setAdapter(null);

        List<String> courses = new ArrayList<String>();
        courses.add(0,"Div");
        for ( String key : courseList.keySet() ) {
            System.out.println( key );

            String key1=key;
            //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
            courses.add(key1);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // attaching data adapter to spinner
        div_spinner.setAdapter(dataAdapter);

        div_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                String courseId=courseList.get(item);

                Log.e("mapkeydiv ","mapkeydiv "+item);

                if(!item.contains("Div")){

                    //  coursebatchtermdivisionid

                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("coursebatchtermdivisionid",courseId);
                    editor.commit();

                    pagerPresInterface.getSubList(getContext(),courseId);

                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(true);//you can cancel it by pressing back button
                    progressBar.setMessage("loading");
                    progressBar.setIndeterminate(true);

                    progressBar.show();

                  // pagerPresInterface.getDivStdList(getContext(),courseId);

                   // pagerPresInterface.getDivScheduleList(getContext(),courseId);



                }




            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void setSubList(final HashMap<String, String> courseList) {

        progressBar.dismiss();

        exe_spinner.setAdapter(null);

        List<String> courses = new ArrayList<String>();
        courses.add(0,"Sub");

        for ( String key : courseList.keySet() ) {
            System.out.println( key );

            String key1=key;
            //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
            courses.add(key1);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // attaching data adapter to spinner
        sub_spinner.setAdapter(dataAdapter);

        sub_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                String courseId=courseList.get(item);

                Log.e("mapkey1 ","mapkey1 "+courseId);

                if(!item.contains("Sub")){
                    pagerPresInterface.getSubScheduleList(getContext(),courseId);

                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("selectsubid",courseId);
                    editor.putString("coursebatchtermdivisionsubjectid",courseId);
                    editor.commit();


                    String coursebatchid=courseselpreference.getString("coursebatchid","");
                    pagerPresInterface.getCourseBatchExamList(getContext(),coursebatchid);

                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(true);//you can cancel it by pressing back button
                    progressBar.setMessage("loading");
                    progressBar.setIndeterminate(true);

                    progressBar.show();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    @Override
    public void setSubScheduleList(List<String> courseList) {

    }

    @Override
    public void setSubTypelist(HashMap<String, String> courseList) {

    }

    @Override
    public void setDivStdList(HashMap<String, String> courseList) {




    }

    @Override
    public void setDivScheduleList(List<String> courseList) {

    }

    @Override
    public void setMessage(int msg) {


    }

    @Override
    public void setMessageNew(String msg) {


        progressBar.dismiss();

        SharedPreferences.Editor editor = courseselpreference.edit();
        editor.putString("selectschedid","");
        editor.putString("selectsubid","");
        editor.putString("coursebatchtermdivisionsubjectid","");
        editor.putString("coursebatchtermdivisionid","");
        editor.putString("coursebatchexamgroupid","");

        //selectsubid
        editor.commit();
        batch_spinner.setAdapter(null);
        term_spinner.setAdapter(null);
        div_spinner.setAdapter(null);
        sub_spinner.setAdapter(null);
        //    sub_spinner.setAdapter(null);

        course_spinner.setAdapter(null);
        exe_spinner.setAdapter(null);
        examName.setText("");

        pagerPresInterface.getCourseList(getContext());

        progressBar = new ProgressDialog(getContext());
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("loading");
        progressBar.setIndeterminate(true);

        progressBar.show();


        Snackbar snackbar = Snackbar
                .make(primaryLinear, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void setCourseBatchExamList(final HashMap<String, String> courseList) {

progressBar.dismiss();

        List<String> courses = new ArrayList<String>();
        courses.add(0,"Exam");

        for ( String key : courseList.keySet() ) {
            System.out.println( key );

            String key1=key;
            //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
            courses.add(key1);

        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courses);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // attaching data adapter to spinner
        exe_spinner.setAdapter(dataAdapter);

        exe_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                String courseId=courseList.get(item);

                Log.e("mapkey1 ","mapkey1Exam "+courseId);

             //   btn_login.setEnabled(true);
                // btn_login.setBackgroundResource(R.id.btn_login);

                //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
               // btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));



                if(!item.equals("Exam")){
                   // pagerPresInterface.getSubScheduleList(getContext(),courseId);


                    btn_login.setEnabled(true);
                    // btn_login.setBackgroundResource(R.id.btn_login);

                    //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
                    btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));


                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("coursebatchexamgroupid",courseId);
                    editor.commit();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void setCoursebatchtermdivisionsubjectExamList(HashMap<String, String> courseList) {

    }


    public static boolean isUserNameValid( String username){
        boolean validString = true;
        if(username.equals(null)||username.equals("")||username.equals("null")){
            validString = false;
        }else{
            validString = true;
        }

        return validString;
    }
}
