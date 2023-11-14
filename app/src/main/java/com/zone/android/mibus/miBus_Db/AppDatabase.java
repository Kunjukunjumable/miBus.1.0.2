package com.zone.android.mibus.miBus_Db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

import com.zone.android.mibus.miBus_Dao.org_Dao;
import com.zone.android.mibus.miBus_Dao.route_Dao;
import com.zone.android.mibus.miBus_Dao.route_PointDao;
import com.zone.android.mibus.miBus_Dao.token_Dao;
import com.zone.android.mibus.miBus_Dao.user_Dao;
import com.zone.android.mibus.miBus_Entity.Org;
import com.zone.android.mibus.miBus_Entity.Route;
import com.zone.android.mibus.miBus_Entity.RoutePoint;
import com.zone.android.mibus.miBus_Entity.Token_det;
import com.zone.android.mibus.miBus_Entity.User;


/**
 * Created by Inspiron on 04-01-2018.
 */
@Database(entities = {User.class,Token_det.class,Route.class,RoutePoint.class,Org.class}, version = 2,exportSchema = false)


public abstract class AppDatabase extends RoomDatabase {

    public abstract user_Dao user_dao();
    public abstract token_Dao token_dao();
    public abstract route_Dao route_dao();
    public abstract route_PointDao route_pointDao();
    public abstract org_Dao org_dao();


    private static AppDatabase INSTANCE;
    //private constructor.
    public AppDatabase(){}

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "mibus-database").build();

        }
        return INSTANCE;
    }



    public static void destroyInstance() {
        INSTANCE = null;
    }

    //adding migrationsss


  /*  static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `Message_Det` (`inst_id` TEXT, "
                    + "`msg_id` TEXT,`msg_type` TEXT,`msg_time` TEXT, PRIMARY KEY(`inst_id`))");
        }
    };*/

}