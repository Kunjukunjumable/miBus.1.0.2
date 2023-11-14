package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;


import com.zone.android.mibus.miBus_Entity.Person_det;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Inspiron on 17-05-2018.
 */

public interface PagerPresInterface {
    void getAttList(Context context, String StdId);
    void setAttList(List<Person_det> attList);

    void getCourseList(Context context);
    void setCourseList( HashMap<String,String> courseList);

    void getCourseBatchList(Context context,String courseId);
    void setCourseBatchList( HashMap<String,String> courseList);

    void getCourseBatchTermList(Context context,String courseBatchId);
    void setCourseBatchTermList( HashMap<String,String> courseList);

    void getCourseBatchExamList(Context context,String courseBatchId);
    void setCourseBatchExamList( HashMap<String,String> courseList);

    void getCourseBatchTermDivList(Context context,String courseBatchId);
    void setCourseBatchTermDivList( HashMap<String,String> courseList);


    void getSubList(Context context,String courseBatchId);
    void setSubList( HashMap<String,String> courseList);

    void getDivScheduleList(Context context,String courseBatchId);
    void setDivScheduleList(List<String> courseList);

    void getSubScheduleList(Context context,String courseBatchId);
    void setSubScheduleList( List<String> courseList);



    void getDivStdList(Context context,String courseBatchId);
    void setDivStdList( HashMap<String,String> courseList);




}
