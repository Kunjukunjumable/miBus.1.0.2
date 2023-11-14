package com.zone.android.mibus.miBus_Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Entity.Mark;
import com.zone.android.mibus.miBus_Entity.Person_det;
import com.zone.android.mibus.miBus_Presenter.PagerPresClass;
import com.zone.android.mibus.miBus_Presenter.PagerPresInterface;
import com.zone.android.mibus.miBus_Utility.Constants;
import com.zone.android.mibus.miBus_Utility.Methods;
import com.zone.android.mibus.miBus_View.pagerViewInterface;
import com.zone.android.mibus.miBus_View.teacherViewClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class markFragment extends  android.support.v4.app.Fragment implements pagerViewInterface {

    PagerPresInterface pagerPresInterface =new PagerPresClass(markFragment.this);
    RecyclerView.Adapter adapter;
    SharedPreferences courseselpreference;
    RecyclerView recyclerView;
    Spinner course_spinner,  batch_spinner,term_spinner,div_spinner,sub_spinner,sche_spinner,type_spinner;

    HashMap<String, String> hashMap_Course = new HashMap<String, String>();

    public static ProgressDialog progressBar;
    private Context mContext;
    LinearLayout primaryLinear;
    Button btn_login;
    studentAdapter studentAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);


        final View rootview= inflater.inflate(R.layout.activity_markexam,container,false);


        courseselpreference= getContext().getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);

        primaryLinear=(LinearLayout)rootview.findViewById(R.id.primaryLinear);

        course_spinner=(Spinner)rootview.findViewById(R.id.course_spinner);

        type_spinner=(Spinner)rootview.findViewById(R.id.type_spinner);

        // course_spinner.setOnItemSelectedListener(this);
        batch_spinner=(Spinner)rootview.findViewById(R.id.batch_spinner);
        term_spinner=(Spinner)rootview.findViewById(R.id.term_spinner);

        div_spinner=(Spinner)rootview.findViewById(R.id.div_spinner);
        sub_spinner=(Spinner)rootview.findViewById(R.id.sub_spinner);
        sche_spinner=(Spinner)rootview.findViewById(R.id.sche_spinner);
        recyclerView=(RecyclerView)rootview.findViewById(R.id.recycler_view);

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
        //coursebatchtermstudentsubjectexamid
        editor.putString("coursebatchtermstudentsubjectexamid","");
        //selectsubid
        editor.commit();

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String  coursebatchtermstudentsubjectexamid=courseselpreference.getString("coursebatchtermstudentsubjectexamid","");

                progressBar = new ProgressDialog(getContext());
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("Sending");
                progressBar.setIndeterminate(true);

                progressBar.show();

                Methods.markExam(getContext(),markFragment.this,coursebatchtermstudentsubjectexamid,studentAdapter.studentList);
            }
        });

      return rootview;
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
        type_spinner.setAdapter(null);
        sche_spinner.setAdapter(null);

        try {

            List<String> courses = new ArrayList<String>();
            courses.add(0, "Course");
            for (String key : courseList.keySet()) {
                System.out.println(key);

                String key1 = key;

                //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
                courses.add(key1);

            }

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
        type_spinner.setAdapter(null);
        sche_spinner.setAdapter(null);

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

                Log.e("mapkey1 ","mapkey1 "+courseId);

                if(!item.contains("Batch")){
                    pagerPresInterface.getCourseBatchTermList(getContext(),courseId);

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
    public void setCourseBatchTermList(final HashMap<String, String> courseList) {
        progressBar.dismiss();

        div_spinner.setAdapter(null);
        sub_spinner.setAdapter(null);
        type_spinner.setAdapter(null);
        sche_spinner.setAdapter(null);

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
        type_spinner.setAdapter(null);
        sche_spinner.setAdapter(null);

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

                 //   pagerPresInterface.getDivStdList(getContext(),courseId);


                 //   pagerPresInterface.getDivScheduleList(getContext(),courseId);



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

        type_spinner.setAdapter(null);
        sche_spinner.setAdapter(null);

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

                Log.e("mapkey1sub ","mapkey1sub "+courseId);

                if(!item.contains("Sub")){


                  //  pagerPresInterface.getSubScheduleList(getContext(),courseId);
                    Methods.getExamType(markFragment.this);
/*
                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(true);//you can cancel it by pressing back button
                    progressBar.setMessage("loading");
                    progressBar.setIndeterminate(true);

                    progressBar.show();*/

                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("selectsubid",courseId);
                    editor.putString("coursebatchtermdivisionsubjectid",courseId);
                    editor.commit();


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
    public void setSubTypelist(final HashMap<String, String> courseList) {

        progressBar.dismiss();
        sche_spinner.setAdapter(null);



        List<String> courses = new ArrayList<String>();
        courses.add(0,"Type");

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
        type_spinner.setAdapter(dataAdapter);
        type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                String typeid=courseList.get(item);

                if(!item.contains("Type")){



                    String courseId=courseselpreference.getString("coursebatchtermdivisionsubjectid","");

                    Methods.getExamList(getContext(),markFragment.this,courseId,typeid);

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
    public void setDivStdList(HashMap<String, String> courseList) {

        if(progressBar.isShowing()){
            progressBar.dismiss();
        }

        Log.e("setDivStdList ","setDivStdList "+courseList.size());
        //    String courseId=courseList.get(item);


        List<String> studentList = new ArrayList<String>();

        for ( String key : courseList.keySet() ) {
            System.out.println( "keyss" +key );


            String key1=key;
            //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
            studentList.add(key1);

        }

        Log.e("setDivStdList ","setDivStdList "+studentList.size());

      //  recyclerView.setHasFixedSize(true);

        ArrayList<Mark>finStList=new ArrayList<Mark>();

        for(int i=0;i<studentList.size();i++){
            Mark mark = new Mark();
            mark.setName(studentList.get(i).toString());

            String courseId=courseList.get(studentList.get(i).toString());
            mark.setpKey(courseId);
            mark.setMark("100");
            Log.e("setDivStdListMarkid ","setDivStdListmarkid "+courseId);


            finStList.add(mark);

        }


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter=new studentAdapter(getContext(),finStList);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setDivScheduleList(List<String> courseList) {

    }

    @Override
    public void setMessage(int msg) {
        progressBar.dismiss();

        SharedPreferences.Editor editor = courseselpreference.edit();
        editor.putString("selectschedid","");
        editor.putString("selectsubid","");
        editor.putString("coursebatchtermdivisionsubjectid","");
        editor.putString("coursebatchtermdivisionid","");
        //coursebatchtermstudentsubjectexamid
        editor.putString("coursebatchtermstudentsubjectexamid","");
        //selectsubid
        editor.commit();

        recyclerView.setAdapter(null);
        batch_spinner.setAdapter(null);
        term_spinner.setAdapter(null);
        div_spinner.setAdapter(null);
        sche_spinner.setAdapter(null);
        sub_spinner.setAdapter(null);
        //    sub_spinner.setAdapter(null);

        course_spinner.setAdapter(null);


        pagerPresInterface.getCourseList(getContext());


        Snackbar snackbar = Snackbar
                .make(primaryLinear, "Mark sent successfully", Snackbar.LENGTH_LONG);
        snackbar.show();            snackbar.show();
        }


    @Override
    public void setMessageNew(String msg) {

    }

    @Override
    public void setCourseBatchExamList(final HashMap<String, String> courseList) {



    }

    @Override
    public void setCoursebatchtermdivisionsubjectExamList(final HashMap<String, String> courseList) {
        progressBar.dismiss();

        Log.e("ExamList","ExamList"+courseList.size());

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
        sche_spinner.setAdapter(dataAdapter);

        sche_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();

                String courseId=courseList.get(item);

                Log.e("mapkey1sub ","mapkey1sub "+courseId);

                if(!item.equals("Exam")){
                   String examId=courseselpreference.getString("coursebatchtermdivisionid","");

                    pagerPresInterface.getDivStdList(getContext(),examId);

                    progressBar = new ProgressDialog(getContext());
                    progressBar.setCancelable(true);//you can cancel it by pressing back button
                    progressBar.setMessage("loading");
                    progressBar.setIndeterminate(true);

                    progressBar.show();

                    btn_login.setEnabled(true);

                    // btn_login.setBackgroundResource(R.id.btn_login);

                    //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
                    btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));

                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("coursebatchtermstudentsubjectexamid",courseId);
                    editor.commit();

/*
                    //  pagerPresInterface.getSubScheduleList(getContext(),courseId);
                    Methods.getExamList(getContext(),markFragment.this,courseId);

                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("coursebatchtermstudentsubjectexamid",courseId);
                    editor.commit();*/
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


}
