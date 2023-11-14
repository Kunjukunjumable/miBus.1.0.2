package com.zone.android.mibus.miBus_Presenter;

import android.content.Context;


import com.zone.android.mibus.miBus_Entity.Person_det;
import com.zone.android.mibus.miBus_Model.PagerModelClass;
import com.zone.android.mibus.miBus_Model.PagerModelInterface;
import com.zone.android.mibus.miBus_View.pagerViewInterface;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Inspiron on 17-05-2018.
 */

public class PagerPresClass implements PagerPresInterface {
    pagerViewInterface pagerViewInterface;
    PagerModelInterface pagerModelInterface;

    public PagerPresClass(pagerViewInterface pagerViewInterface ){

        this.pagerViewInterface=pagerViewInterface;
        this.pagerModelInterface=new PagerModelClass();
    }


    @Override
    public void getAttList(Context context,String stdId) {
        pagerModelInterface.getAttList(this,context,stdId);
    }

    @Override
    public void setAttList(List<Person_det> attList) {

        pagerViewInterface.setAttList(attList);
    }

    @Override
    public void getCourseList(Context context) {
        pagerModelInterface.getCourseList(context,this);

    }

    @Override
    public void setCourseList(HashMap<String, String> courseList) {
pagerViewInterface.setCourseList(courseList);
    }

    @Override
    public void getCourseBatchList(Context context, String courseId) {

        pagerModelInterface.getCourseBatchList(context,courseId,this);
    }

    @Override
    public void setCourseBatchList(HashMap<String, String> courseList) {

        pagerViewInterface.setCourseBatchList(courseList);
    }

    @Override
    public void getCourseBatchTermList(Context context, String courseBatchId) {
        pagerModelInterface.getCourseBatchTermList(context,courseBatchId,this);
    }

    @Override
    public void setCourseBatchTermList(HashMap<String, String> courseList) {

        pagerViewInterface.setCourseBatchTermList(courseList);

    }

    @Override
    public void getCourseBatchExamList(Context context, String courseBatchId) {
        pagerModelInterface.getCourseBatchExamList(context,courseBatchId,this);

    }

    @Override
    public void setCourseBatchExamList(HashMap<String,String> courseList) {
        pagerViewInterface.setCourseBatchExamList(courseList);
    }

    @Override
    public void getCourseBatchTermDivList(Context context, String courseBatchId) {
        pagerModelInterface.getCourseBatchTermDivList(context,courseBatchId,this);
    }

    @Override
    public void setCourseBatchTermDivList(HashMap<String, String> courseList) {

        pagerViewInterface.setCourseBatchTermDivList(courseList);

    }

    @Override
    public void getSubList(Context context, String courseBatchId) {
        pagerModelInterface.getSubList(context,courseBatchId,this);
    }

    @Override
    public void setSubList(HashMap<String, String> courseList) {
        pagerViewInterface.setSubList(courseList);


    }

    @Override
    public void getDivScheduleList(Context context, String courseBatchId) {
        pagerModelInterface.getDivScheduleList(context,courseBatchId,this);
    }

    @Override
    public void setDivScheduleList(List<String> courseList) {

        pagerViewInterface.setDivScheduleList(courseList);

    }

    @Override
    public void getSubScheduleList(Context context, String courseBatchId) {

        pagerModelInterface.getSubScheduleList(context,courseBatchId,this);
    }

    @Override
    public void setSubScheduleList(List<String> courseList) {

        pagerViewInterface.setSubScheduleList(courseList);

    }

    @Override
    public void getDivStdList(Context context, String courseBatchId) {

        pagerModelInterface.getDivStdList(context,courseBatchId,this);

    }

    @Override
    public void setDivStdList(HashMap<String,String> courseList) {
        pagerViewInterface.setDivStdList(courseList);


    }


}
