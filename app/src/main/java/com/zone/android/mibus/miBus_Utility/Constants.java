package com.zone.android.mibus.miBus_Utility;

/**
 * Created by Inspiron on 20-10-2017.
 */

public final class Constants {

    public static String LOGIN_PREFERENCE = "login_preference";

    public static String LOCATION_PREFERENCE = "location_preference";

    public static String LAST_INDEX = "last_index";
    public static String IS_ROUTE_SEL = "is_route_sel";

    /// "com.example.android.threadsample.BROADCAST";

    // Defines a custom Intent action
    public static final String BROADCAST_ACTION_BUS = "com.zone.android.miskool.BROADCAST_BUS";

    public static final String BROADCAST_ACTION_MSG = "com.zone.android.miskool.BROADCAST_MSG";

    public static final String EXTENDED_DATA_STATUS_MSG =
            "com.zone.android.msg.STATUS";


    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS_BUS = "com.example.android.threadsample.STATUS_BUS";


    public static final String GET_BUSTRACKER_DETAILS= "http://identity.********/login/loginservice/GetBusTrackerDetails/?"
    //bustrack service


    //getting access token latest

    public static final String GET_ACCESS_TOKEN = "http://identity.*******/Services/SchoolApp/Json/GetAccessToken/";
    public static final String GET_ACCESS_TOKEN
    //getting person details

    public static String GET_USER_TOKEN="http://identity.*******/Services/SchoolApp/Json/GetUserWithToken/";
    public static String GET_USER_TOKEN=


    public static final int ERROR_USER_NAME_NULL=1;

    public static final int ERROR_PASS_WORD_NULL=2;

    public static final int ERROR_NETWORK=3;

    public static final int ERROR_USER_NAME=4;

    public static final int ERROR_CREDENTIALS=5;

    public static final int PASS_VALIDATION=6;

    public static final int PASS_NETWORK=7;

    public static final int PASS_SERVICE=8;
    public static final int PASS_SERVICE_TOKEN=10;

    public static final int PASS_SERVICE_DEL=11;
    public  static final int DAIRY_ADD=14;
    public  static final int EXAM_CREATE=14;




    public static final int ERROR_SERVICE=15;

    public static final int NO_ROUTES=16;

    //adding shared preference

    public static String STUDENT_SELECTION_PREFERENCE = "student_selection_preference";

    public static String ROLES_PREFERENCE = "roles_preference";


    public static String DRIVER_SELECTION_PREFERENCE = "driver_selection_preference";


    public static String SERVICE_STATUS_PREFERENCE = "service_status_preference";


    public static String LAST_TOKEN_PREFERENCE= "last_token_preference";

    public static String ROUTE_SEL_PREFERENCE= "route_sel_preference";

    public static String ORG_SEL_PREFERENCE= "org_sel_preference";

    public static String NEXT_ROUTE_PREFERENCE= "next_route_preference";

    public static String COURSE_SEL_PREFERENCE= "course_sel_preference";

      //new services

    public static final String DOMAIN_STATIC = "http://identity.*******/ZGlobal/";

    public static final String DOMAIN_SCHOOL = "http://identity.*******/ZSchool/";

    public static final String HOST_NAME = "fjmhss.edu.via.zone";

    public static final String DOMAIN_STATIC_TRANSPORT = "http://identity.*******/ZTransport/";

    public static final String GET_LOGIN = DOMAIN_STATIC+"json/login/";

    public static final String GET_LOGOUT = DOMAIN_STATIC+"json/logout/";

    public static final String GET_ROUTES=DOMAIN_STATIC_TRANSPORT+"json/transport/route/driver/route/list/";

    public static final String UPDATE_GPS=DOMAIN_STATIC_TRANSPORT+"json/transport/route/driver/update/gps/";

    public static final String GET_ALL_ROUTE_POINTS=DOMAIN_STATIC_TRANSPORT+"json/transport/route/point/all/";

    public static final String GET_ORG_LIST=DOMAIN_STATIC+"json/user/org/list/";

    public static final String GET_ROLES_LIST=DOMAIN_STATIC+"json/user/org/loginusingtoken/";

    public static final String GET_COURSE_LIST=DOMAIN_SCHOOL+"json/school/course/all/";

    public static final String GET_BATCH_LIST=DOMAIN_SCHOOL+"json/school/course/batch/all/";

    public static final String GET_BATCH_TERM_LIST=DOMAIN_SCHOOL+"json/school/course/batch/term/list/";
    public static final String GET_BATCH_EXAM_LIST=DOMAIN_SCHOOL+"json/school/course/batch/examgroup/list/";

    public static final String CREATE_EXAM=DOMAIN_SCHOOL+"json/school/course/batch/term/division/subject/exam/create/";

    public static final String GET_BATCH_TERM_DIV_LIST=DOMAIN_SCHOOL+"json/school/course/batch/term/division/list/";

    public static final String GET_BATCH_TERM_DIV_SUB_LIST=DOMAIN_SCHOOL+"json/school/course/batch/term/division/subject/list/";

    public static final String GET_BATCH_TERM_DIV_SUB_SCHE_LIST=DOMAIN_SCHOOL+"json/school/course/batch/term/division/subject/schedule/list/";

    public static final String GET_DIV_STUDENTS=DOMAIN_SCHOOL+"json/school/course/batch/term/division/student/list/";

    public static final String GET_DIV_STUDENTS_FULL=DOMAIN_SCHOOL+"json/school/course/batch/term/division/student/list/full/";


    public static final String  GET_BATCH_TERM_DIV_SCHE_LIST=DOMAIN_SCHOOL+"json/school/course/batch/term/division/schedule/list/";

    public static final String  MARK_DIV_ABSENCE=DOMAIN_SCHOOL+"json/school/course/batch/term/division/markabsence/";


    public static final String  MARK_SUB_ABSENCE=DOMAIN_SCHOOL+"json/school/course/batch/term/division/subject/markabsence/";

    public static final String  EXAM_LIST=DOMAIN_SCHOOL+"json/school/course/batch/term/division/subject/exam/list/";

    public static final String  ADD_DIARY=DOMAIN_SCHOOL+"json/school/course/batch/term/division/add_diary_note/";

    public static final String  MARK_PAIRS=DOMAIN_SCHOOL+"json/school/course/batch/term/division/subject/exam/mark/pairs/";



    // Defines a custom Intent action
    public static final String BROADCAST_ACTION =
            "com.zone.android.busLocation.BROADCAST";

    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS =
            "com.zone.android.busLocation.STATUS";


    //declaring useful methodsss

    //validating username
    public static boolean isUserNameValid( String username){
        boolean validString = true;
        if(username.equals(null)||username.equals("")||username.equals("null")){
            validString = false;
        }else{
            validString = true;
        }

        return validString;
    }

  //validating passowrd

    public static boolean isPassWordValid( String password){
        boolean validString = true;
        if(password.equals(null)||password.equals("")||password.equals("null")){
            validString = false;
        }else{
            validString = true;
        }

        return validString;
    }




}
