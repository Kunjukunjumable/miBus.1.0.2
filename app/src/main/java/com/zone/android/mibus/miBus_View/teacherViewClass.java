package com.zone.android.mibus.miBus_View;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.zone.android.mibus.R;
import com.zone.android.mibus.miBus_Adapter.CustomView;
import com.zone.android.mibus.miBus_Adapter.absentadapter;
import com.zone.android.mibus.miBus_Adapter.examCreationFragment;
import com.zone.android.mibus.miBus_Adapter.markFragment;
import com.zone.android.mibus.miBus_Adapter.scheduleadapter;
import com.zone.android.mibus.miBus_Entity.Person_det;
import com.zone.android.mibus.miBus_Presenter.PagerPresClass;
import com.zone.android.mibus.miBus_Presenter.PagerPresInterface;
import com.zone.android.mibus.miBus_Utility.Constants;
import com.zone.android.mibus.miBus_Utility.Methods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static com.zone.android.mibus.miBus_View.teacherViewClass.coursefragment.progressBar;

public class teacherViewClass extends AppCompatActivity {

    DemoCollectionPagerAdapter viePagerAdapter;
    ViewPager mViewPager;
   static GridView dridschedule;
    static ArrayList<String> mStringList= new ArrayList<String>();
    static Button btn_login;

    static  scheduleadapter scheduleadapter;
    TabLayout tabLayout;
  // static Spinner course_spinner,  batch_spinner,term_spinner,div_spinner,sub_spinner,sche_spinner;

    static HashMap<String, String> hashMap_Student = new HashMap<String, String>();

    static List<String> studentList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

      /*  actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.mipmap.homenew);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Inbox");*/

        getSupportActionBar().setTitle("Teacher");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viePagerAdapter =
                new DemoCollectionPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(viePagerAdapter);

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager, true);

       // createTabIcons();

    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter  {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            switch (i){
                case 0: return new coursefragment();
                case 1: return new messagefragment();
                case 2:return  new examCreationFragment();
                case 3:return  new markFragment();
            }



           /* Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);*/
            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0: return "Attend";
                case 1: return "Message";
                case 2: return "Exam";
                case 3: return "Mark";
                default: return null;
            }
        }

    }


    public  static class coursefragment extends android.support.v4.app.Fragment implements pagerViewInterface {

        PagerPresInterface pagerPresInterface =new PagerPresClass(coursefragment.this);

         SharedPreferences courseselpreference;
        RecyclerView recyclerView;
        Spinner course_spinner,  batch_spinner,term_spinner,div_spinner,sub_spinner,sche_spinner;

        HashMap<String, String> hashMap_Course = new HashMap<String, String>();

        public static ProgressDialog progressBar;
        private Context mContext;
        LinearLayout primaryLinear;
        Button btn_login;


        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            final View rootview= inflater.inflate(R.layout.activity_viewpager,container,false);

             dridschedule=(GridView)rootview.findViewById(R.id.gridAttendance);
            courseselpreference= getContext().getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);

            final GridView dridabsent=(GridView)rootview.findViewById(R.id.gridabsent);
            primaryLinear=(LinearLayout)rootview.findViewById(R.id.primaryLinear);

             course_spinner=(Spinner)rootview.findViewById(R.id.course_spinner);

           // course_spinner.setOnItemSelectedListener(this);
             batch_spinner=(Spinner)rootview.findViewById(R.id.batch_spinner);
             term_spinner=(Spinner)rootview.findViewById(R.id.term_spinner);

             div_spinner=(Spinner)rootview.findViewById(R.id.div_spinner);
             sub_spinner=(Spinner)rootview.findViewById(R.id.sub_spinner);
             sche_spinner=(Spinner)rootview.findViewById(R.id.sche_spinner);

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
            //selectsubid
            editor.commit();




            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String selectschedid =courseselpreference.getString("selectschedid","");

                    //selectsubid
                    final String subId =courseselpreference.getString("coursebatchtermdivisionsubjectid","");


                    if(!selectschedid.equals("")&&!selectschedid.equals("Schedule")){
                        Log.e("selectschedid ","selectschedid "+selectschedid+" "+studentList.size()+" " +mStringList.size());

                      if(mStringList.size()>0) {
                          /////////////////////////////////////


                          // custom dialog
                          final Dialog dialog = new Dialog(getContext());
                          dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                          dialog.setContentView(R.layout.activity_confirmabsence);

                          GridView gridabsent = (GridView) dialog.findViewById(R.id.gridabsent);
                          TextView btnLogout = (TextView) dialog.findViewById(R.id.btnLogout);
                          TextView btnCancel = (TextView) dialog.findViewById(R.id.btnCancel);
                          dialog.show();
                          dialog.setCancelable(false);

                          absentadapter absentadapter = new absentadapter(getContext(), mStringList);

                          gridabsent.setAdapter(absentadapter);

                          gridabsent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                              @Override
                              public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                  Log.e("clcileeee", "ccccccdfdf");

                                //  Toast.makeText(getContext(), "You Clicked at " + mStringList.get(position), Toast.LENGTH_SHORT).show();

                                  mStringList.remove(position);

                                  final absentadapter absentadapter = new absentadapter(getContext(), mStringList);

                                  dridabsent.setAdapter(absentadapter);
                              }

                              @Override
                              public void onNothingSelected(AdapterView<?> parent) {

                              }
                          });

                          btnLogout.setOnClickListener(new View.OnClickListener() {
                              @Override
                              public void onClick(View view) {
                                  //  Methods.testLogOut(getApplicationContext(),MapsActivity.this);

                                  String absentees="";
                                   for (int i=0;i<mStringList.size();i++){

                                       absentees=mStringList.get(i);

                                       if(i!=mStringList.size()-1){
                                           absentees= absentees+",";
                                       }
                                   }

                                if(subId.equals("")||subId.equals(" ")||subId.equals(null)||subId.equals("null"))
                                  {

                                      Methods.markDivAttendance(getContext(), coursefragment.this, absentees);
                                  }else{

                                       Methods.markSubAttendance(getContext(), coursefragment.this, absentees);

                                  }

                                  progressBar = new ProgressDialog(getContext());
                                  progressBar.setCancelable(true);//you can cancel it by pressing back button
                                  progressBar.setMessage("Sending");
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

                      }else{

                          Toast.makeText(getContext(),"Mark Absentees",Toast.LENGTH_SHORT).show();
                      }

                      //////////////////////////////////


                    }else {
                        Toast.makeText(getContext(),"Choose Schedule",Toast.LENGTH_SHORT).show();
                    }



                    Log.e("selectsubid ","selectsubid "+subId);




//////////////////////////////////////////////////

                }
            });




            final int[] imageId = {
                   1,2,34,56,34,56,11,15,24,36,45

            };


             final int[] absentId = {
            };


           /* final scheduleadapter scheduleadapter = new scheduleadapter(getContext(),imageId);
            dridschedule.setAdapter(scheduleadapter);

            final ArrayList<Integer> mStringList= new ArrayList<Integer>();


            dridschedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Toast.makeText(getContext(), "You Clicked at " +imageId[+ position], Toast.LENGTH_SHORT).show();
/////////////////////////////////////////

                 *//*   mStringList.add(imageId[+ position]);
                  //  mStringList.add("john");

                    final int[] ints = new int[mStringList.size()];

                    int i = 0;
                    for (Integer n : mStringList) {
                        ints[i++] = n;
                    }

                    final absentadapter absentadapter = new absentadapter(getContext(),mStringList);

                    dridabsent.setAdapter(absentadapter);
*//*


                    int selectedIndex = scheduleadapter.selectedPositions.indexOf(position);
                    if (selectedIndex > -1) {
                        scheduleadapter.selectedPositions.remove(selectedIndex);

                     //   mStringList.remove(imageId[+ position]);

                        ((CustomView)view).display(false);
                    } else {
                        scheduleadapter.selectedPositions.add(position);
                        ((CustomView)view).display(true);
                    }
                    ///////////////////////////////////////////////////////

                }
            });

*/



           /* dridabsent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Log.e("clcileeee","ccccccdfdf");

                    Toast.makeText(getContext(), "You Clicked at " +mStringList.get(position), Toast.LENGTH_SHORT).show();

                    mStringList.remove(position);

                    final absentadapter absentadapter = new absentadapter(getContext(),mStringList);

                    dridabsent.setAdapter(absentadapter);

                    //  absentadapter.removeItem(position);

                }
            });
*/



            //  PieView pieView = (PieView) rootview.findViewById(R.id.pieView);


            Bundle args = getArguments();
         //   String stdId= args.getString("stdId");

            //   List<Attributes>attsList= Methods.getAttributes(container.getContext(),stdId);
           // pagerPresInterface.getAttList(container.getContext(),stdId);


            return rootview;
        }

        @Override
        public void setAttList(final List<Person_det> attList) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                  /*  recyclerView.setHasFixedSize(true);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(MyLayoutManager);
                    RecyclerView.Adapter adapter = new informationAdapter(getContext(),attList);
                    // slidingPanelAdapter=new slidingPanelAdapter(personList);
                    recyclerView.setAdapter(adapter);*/

                }
            });


        }

        @Override
        public void setCourseList(final HashMap<String, String> courseList) {
            progressBar.dismiss();

            batch_spinner.setAdapter(null);
            term_spinner.setAdapter(null);
            div_spinner.setAdapter(null);
            sub_spinner.setAdapter(null);
            sche_spinner.setAdapter(null);
            dridschedule.setAdapter(null);

          /*  Iterator iterator = courseList.keySet().iterator();
            while( iterator. hasNext() ){
                Toast.makeText(getContext(), courseList.get(iterator.next().toString()),
                        Toast.LENGTH_SHORT).show();
            }*/

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
            sche_spinner.setAdapter(null);
            dridschedule.setAdapter(null);


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
            sche_spinner.setAdapter(null);
            dridschedule.setAdapter(null);


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
            sche_spinner.setAdapter(null);
            dridschedule.setAdapter(null);


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
                    sub_spinner.setAdapter(null);
                    sche_spinner.setAdapter(null);

                    if(!item.contains("Div")){

                        btn_login.setEnabled(true);
                        // btn_login.setBackgroundResource(R.id.btn_login);

                        //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
                        btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));


                        //  coursebatchtermdivisionid

                        SharedPreferences.Editor editor = courseselpreference.edit();
                        editor.putString("coursebatchtermdivisionid",courseId);
                        editor.commit();

                       pagerPresInterface.getSubList(getContext(),courseId);

                        pagerPresInterface.getDivStdList(getContext(),courseId);

                        pagerPresInterface.getDivScheduleList(getContext(),courseId);

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
        public void setSubList(final HashMap<String, String> courseList) {
            progressBar.dismiss();

           /* btn_login.setEnabled(true);
            // btn_login.setBackgroundResource(R.id.btn_login);

            //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
            btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));
*/
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
                     //   sche_spinner.setAdapter(null);
                        pagerPresInterface.getSubScheduleList(getContext(),courseId);

                        progressBar = new ProgressDialog(getContext());
                        progressBar.setCancelable(true);//you can cancel it by pressing back button
                        progressBar.setMessage("loading");
                        progressBar.setIndeterminate(true);

                        progressBar.show();

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
            progressBar.dismiss();

            Log.e("setSubScheduleList","setSubScheduleList "+courseList.size());

            Log.e("setSubScheduleList","setSubScheduleList "+courseList.get(0));
             String one=courseList.get(0);




           /* btn_login.setEnabled(true);
            // btn_login.setBackgroundResource(R.id.btn_login);

            //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
            btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));


*/

                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courseList);

                // Drop down layout style - list view with radio button
                dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

                // attaching data adapter to spinner
                courseList.add(0, "Schedule");
                sche_spinner.setAdapter(dataAdapter);

                sche_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item = parent.getItemAtPosition(position).toString();


                        Log.e("mapkey5 ", "mapkey5 " + item);

                        SharedPreferences.Editor editor = courseselpreference.edit();
                        editor.putString("selectschedid", item);
                        editor.commit();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


        }

        @Override
        public void setSubTypelist(HashMap<String, String> courseList) {

        }

        @Override
        public void setDivStdList(HashMap<String,String> courseList) {
            if (progressBar.isShowing()){
                progressBar.dismiss();
            }

          /*  btn_login.setEnabled(true);
           // btn_login.setBackgroundResource(R.id.btn_login);

            //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
            btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));
*/

          Log.e("setDivStdList ","setDivStdList "+courseList.size());
    studentList = new ArrayList<String>();

            for ( String key : courseList.keySet() ) {
                System.out.println( "keyss" +key );

                String key1=key;
                //  Toast.makeText(getContext(),key1, Toast.LENGTH_SHORT).show();
                studentList.add(key1);

            }

            scheduleadapter = new scheduleadapter(getContext(),studentList);
            dridschedule.setAdapter(scheduleadapter);



            dridschedule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 //   Toast.makeText(getContext(), "You Clicked at " +studentList.get(position), Toast.LENGTH_SHORT).show();
/////////////////////////////////////////

                 /*   mStringList.add(imageId[+ position]);
                  //  mStringList.add("john");

                    final int[] ints = new int[mStringList.size()];

                    int i = 0;
                    for (Integer n : mStringList) {
                        ints[i++] = n;
                    }

                    final absentadapter absentadapter = new absentadapter(getContext(),mStringList);

                    dridabsent.setAdapter(absentadapter);
*/


                    int selectedIndex = scheduleadapter.selectedPositions.indexOf(position);
                    if (selectedIndex > -1) {
                        scheduleadapter.selectedPositions.remove(selectedIndex);

                       mStringList.remove(studentList.get(position) );

                        ((CustomView)view).display(false);
                    } else {
                        scheduleadapter.selectedPositions.add(position);
                        ((CustomView)view).display(true);
                        mStringList.add(studentList.get(position)) ;
                    }
                    ///////////////////////////////////////////////////////

                }
            });



        }

        @Override
        public void setDivScheduleList(List<String> courseList) {

if(progressBar.isShowing()){
    progressBar.dismiss();
}

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, courseList);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

            // attaching data adapter to spinner
            courseList.add(0,"Schedule");
            sche_spinner.setAdapter(dataAdapter);

            sche_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item = parent.getItemAtPosition(position).toString();

                    btn_login.setEnabled(true);
                    // btn_login.setBackgroundResource(R.id.btn_login);

                    //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
                    btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));



                    Log.e("mapkey5 ","mapkey5 "+item);

                    SharedPreferences.Editor editor = courseselpreference.edit();
                    editor.putString("selectschedid",item);
                    editor.commit();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        @Override
        public void setMessage(int msg) {

            if(msg==Constants.PASS_SERVICE){
                progressBar.dismiss();

                SharedPreferences.Editor editor = courseselpreference.edit();
                editor.putString("selectschedid","");
                editor.putString("selectsubid","");
                editor.putString("coursebatchtermdivisionsubjectid","");
                editor.putString("coursebatchtermdivisionid","");
                //selectsubid
                editor.commit();
                batch_spinner.setAdapter(null);
                term_spinner.setAdapter(null);
                div_spinner.setAdapter(null);
                sub_spinner.setAdapter(null);
                sche_spinner.setAdapter(null);
                course_spinner.setAdapter(null);
                mStringList.clear();
                studentList.clear();
                scheduleadapter.notifyDataSetChanged();
                pagerPresInterface.getCourseList(getContext());

                progressBar = new ProgressDialog(getContext());
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("loading");
                progressBar.setIndeterminate(true);

                progressBar.show();


                Snackbar snackbar = Snackbar
                        .make(primaryLinear, "Attendance saved successfully", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }

        @Override
        public void setMessageNew(String msg) {

        }

        @Override
        public void setCourseBatchExamList(HashMap<String, String> courseList) {

        }

        @Override
        public void setCoursebatchtermdivisionsubjectExamList(HashMap<String, String> courseList) {

        }


    }



    public static class messagefragment extends android.support.v4.app.Fragment implements pagerViewInterface {

        PagerPresInterface pagerPresInterface =new PagerPresClass(messagefragment.this);
        Spinner course_spinner, batch_spinner,term_spinner,div_spinner,sub_spinner,sche_spinner;

        SharedPreferences courseselpreference;
        RecyclerView recyclerView;

        EditText tv_title,edit_replycontent;
        HashMap<String, String> hashMap_Course = new HashMap<String, String>();

        public static ProgressDialog progressBar;
        private Context mContext;
        LinearLayout primaryLinear;

        Button btn_login;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);

            View rootview= inflater.inflate(R.layout.activity_diary,container,false);


            courseselpreference= getContext().getSharedPreferences(Constants.COURSE_SEL_PREFERENCE,Context.MODE_PRIVATE);

            primaryLinear=(LinearLayout)rootview.findViewById(R.id.primaryLinear);

            tv_title=(EditText)rootview.findViewById(R.id.tv_title);
            edit_replycontent=(EditText)rootview.findViewById(R.id.edit_replycontent);


          // course_spinner=(Spinner)rootview.findViewById(R.id.course_spinner1);

            // course_spinner.setOnItemSelectedListener(this);
            batch_spinner=(Spinner)rootview.findViewById(R.id.batch_spinner);
            term_spinner=(Spinner)rootview.findViewById(R.id.term_spinner);

            course_spinner=(Spinner)rootview.findViewById(R.id.course_spinner);


            div_spinner=(Spinner)rootview.findViewById(R.id.div_spinner);


            //spinner.getBackground().setColorFilter(getResources().getColor(R.color.red), PorterDuff.Mode.SRC_ATOP);

            //sub_spinner=(Spinner)rootview.findViewById(R.id.sub_spinner);
            //sche_spinner=(Spinner)rootview.findViewById(R.id.sche_spinner);

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
            //selectsubid
            editor.commit();




            Bundle args = getArguments();

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String subject=tv_title.getText().toString();
                    String text=edit_replycontent.getText().toString();

                    if(isUserNameValid(subject)){
                        if(isUserNameValid(text)){

                            progressBar = new ProgressDialog(getContext());
                            progressBar.setCancelable(true);//you can cancel it by pressing back button
                            progressBar.setMessage("Sending");
                            progressBar.setIndeterminate(true);

                            progressBar.show();

                            Methods.addDiaryNote(getContext(),messagefragment.this,subject,text);

                        }else {
                            edit_replycontent.requestFocus();
                        }
                    }else{
                        tv_title.requestFocus();
                    }
                }
            });


            return rootview;
        }

        @Override
        public void setAttList(final List<Person_det> attList) {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {

                  /*  recyclerView.setHasFixedSize(true);
                    LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(MyLayoutManager);
                    RecyclerView.Adapter adapter = new informationAdapter(getContext(),attList);
                    // slidingPanelAdapter=new slidingPanelAdapter(personList);
                    recyclerView.setAdapter(adapter);*/

                }
            });


        }

        @Override
        public void setCourseList(final HashMap<String, String> courseList) {
           // course_spinner=(Spinner)rootview.findViewById(R.id.course_spinner1);

            progressBar.dismiss();
            batch_spinner.setAdapter(null);
            term_spinner.setAdapter(null);
            div_spinner.setAdapter(null);


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

                        btn_login.setEnabled(true);
                        // btn_login.setBackgroundResource(R.id.btn_login);

                        //iv.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.img));
                        btn_login.setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.loginxml));



                    }




                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }


        @Override
        public void setSubList(HashMap<String, String> courseList) {

        }

        @Override
        public void setSubScheduleList(List<String> courseList) {

        }

        @Override
        public void setSubTypelist(HashMap<String, String> courseList) {

        }

        @Override
        public void setDivStdList(HashMap<String,String> courseList) {

        }

        @Override
        public void setDivScheduleList(List<String> courseList ) {

        }

        @Override
        public void setMessage(int msg) {

            if(msg==Constants.DAIRY_ADD){

                progressBar.dismiss();

                SharedPreferences.Editor editor = courseselpreference.edit();
                editor.putString("selectschedid","");
                editor.putString("selectsubid","");
                editor.putString("coursebatchtermdivisionsubjectid","");
                editor.putString("coursebatchtermdivisionid","");
                //selectsubid
                editor.commit();
                batch_spinner.setAdapter(null);
                term_spinner.setAdapter(null);
                div_spinner.setAdapter(null);
            //    sub_spinner.setAdapter(null);

                course_spinner.setAdapter(null);
                tv_title.setText("");
                edit_replycontent.setText("");

                pagerPresInterface.getCourseList(getContext());
                progressBar = new ProgressDialog(getContext());
                progressBar.setCancelable(true);//you can cancel it by pressing back button
                progressBar.setMessage("loading");
                progressBar.setIndeterminate(true);

                progressBar.show();


                Snackbar snackbar = Snackbar
                        .make(primaryLinear, "Message sent successfully", Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }

        @Override
        public void setMessageNew(String msg) {

        }

        @Override
        public void setCourseBatchExamList(HashMap<String, String> courseList) {

        }

        @Override
        public void setCoursebatchtermdivisionsubjectExamList(HashMap<String, String> courseList) {

        }

    }

    private int[] buildIntArray(List<Integer> integers) {
        int[] ints = new int[integers.size()];
        int i = 0;
        for (Integer n : integers) {
            ints[i++] = n;
        }
        return ints;
    }

    private void createTabIcons() {

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Attendance");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sub, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Homework");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_lib, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {


            case android.R.id.home:
                //   mDrawerLayout.openDrawer(GravityCompat.START);

                teacherViewClass.this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);
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
