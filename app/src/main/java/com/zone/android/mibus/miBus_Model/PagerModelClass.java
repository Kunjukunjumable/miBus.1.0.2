package com.zone.android.mibus.miBus_Model;

import android.content.Context;
import android.util.Log;

import com.zone.android.mibus.miBus_Db.AppDatabase;
import com.zone.android.mibus.miBus_Presenter.PagerPresInterface;
import com.zone.android.mibus.miBus_Utility.Methods;

import java.util.List;

/**
 * Created by Inspiron on 17-05-2018.
 */

public class PagerModelClass implements PagerModelInterface {
    @Override
    public void getAttList(final PagerPresInterface pagerPresInterface, final Context context, final String stdId) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                AppDatabase appdb = AppDatabase.getAppDatabase(context);

                Log.e("called","getAttributes");


         //       List<Attributes> attList= appdb.attribute_dao().getAttsStudent(stdId);

               // pagerPresInterface.setAttList(attList);

            }
        }).start();
    }

    @Override
    public void getCourseList(Context context, PagerPresInterface pagerPresInterface) {
        Methods.getCourseList(context,pagerPresInterface);

    }

    @Override
    public void getCourseBatchList(Context context, String courseId, PagerPresInterface pagerPresInterface) {
        Methods.getCourseBatchList(context,courseId,pagerPresInterface);

    }

    @Override
    public void getCourseBatchTermList(Context context, String courseBatchId, PagerPresInterface pagerPresInterface) {
        Methods.getCourseBatchTermList(context,courseBatchId,pagerPresInterface);

    }

    @Override
    public void getCourseBatchTermDivList(Context context, String courseBatchId, PagerPresInterface pagerPresInterface) {

        Methods.getCourseBatchTermDivList( context,  courseBatchId,  pagerPresInterface);
    }

    @Override
    public void getSubList(Context context, String courseBatchId, PagerPresInterface pagerPresInterface) {

        Methods.getSubList(context,  courseBatchId,  pagerPresInterface);
    }

    @Override
    public void getSubScheduleList(Context context, String courseBatchId, PagerPresInterface pagerPresInterface) {

        Methods.getSubScheduleList(context,  courseBatchId,  pagerPresInterface);
    }

    @Override
    public void getDivStdList(Context context, String courseBatchId,PagerPresInterface pagerPresInterface) {

        Methods.getDivStdList(context,  courseBatchId,  pagerPresInterface);
    }

    @Override
    public void getDivScheduleList(Context context, String courseBatchId, PagerPresInterface pagerPresInterface) {

        Methods.getDivScheduleList(context,courseBatchId,pagerPresInterface);
    }

    @Override
    public void getCourseBatchExamList(Context context, String courseBatchId, PagerPresInterface pagerPresInterface) {
        Methods.getCourseBatchExamList(context,courseBatchId,pagerPresInterface);
    }

    @Override
    public void setCourseBatchExamList(List<String> courseList) {

    }


}
