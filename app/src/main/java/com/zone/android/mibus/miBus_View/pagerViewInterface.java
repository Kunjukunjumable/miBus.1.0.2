package com.zone.android.mibus.miBus_View;



import com.zone.android.mibus.miBus_Entity.Person_det;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Inspiron on 17-05-2018.
 */

public interface pagerViewInterface {

    void setAttList(List<Person_det> attList);
    void setCourseList(HashMap<String, String> courseList);
    void setCourseBatchList(HashMap<String, String> courseList);
    void setCourseBatchTermList( HashMap<String,String> courseList);
    void setCourseBatchTermDivList( HashMap<String,String> courseList);
    void setSubList( HashMap<String,String> courseList);
    void setSubScheduleList( List<String> courseList);
    void setSubTypelist(HashMap<String,String> courseList);

    void setDivStdList( HashMap<String,String> courseList);
    void setDivScheduleList( List<String> courseList);
    void setMessage(int msg);

    void setMessageNew(String msg);

    void setCourseBatchExamList( HashMap<String,String> courseList);

    void setCoursebatchtermdivisionsubjectExamList(HashMap<String,String> courseList);




}
