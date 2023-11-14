package com.zone.android.mibus.miBus_Model;

import android.content.Context;

import com.zone.android.mibus.miBus_Presenter.PagerPresInterface;

import java.util.List;


/**
 * Created by Inspiron on 17-05-2018.
 */

public interface PagerModelInterface {
    void getAttList(PagerPresInterface pagerPresInterface, Context context, String StdId);
    void getCourseList(Context context,PagerPresInterface pagerPresInterface);

    void getCourseBatchList(Context context,String courseId,PagerPresInterface pagerPresInterface);

    void getCourseBatchTermList(Context context,String courseBatchId,PagerPresInterface pagerPresInterface);
    void getCourseBatchTermDivList(Context context,String courseBatchId,PagerPresInterface pagerPresInterface);

    void getSubList(Context context,String courseBatchId,PagerPresInterface pagerPresInterface);
    void getSubScheduleList(Context context,String courseBatchId,PagerPresInterface pagerPresInterface);
    void getDivStdList(Context context,String courseBatchId,PagerPresInterface pagerPresInterface);

    void getDivScheduleList(Context context,String courseBatchId,PagerPresInterface pagerPresInterface);

    void getCourseBatchExamList(Context context,String courseBatchId,PagerPresInterface pagerPresInterface);
    void setCourseBatchExamList( List<String> courseList);

}
