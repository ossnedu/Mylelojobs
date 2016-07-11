package com.mylelojobs.android.mylelojobs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.mylelojobs.android.mylelojobs.dbcontract.*;

/**
 * Created by Pegasus Amazing on 7/7/2016.
 */
public class dbhelper extends SQLiteOpenHelper {

    public dbhelper(Context context){
        super(context,"mylelojobs.db",null,6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists " + rootJobs.TABLE_NAME+
                "("+rootJobs.COL_ID+" integer PRIMARY KEY,"+
                " "+rootJobs.COL_JOB_ID+" int not null,"+
                " "+rootJobs.COL_NAME+" varchar(255),"+" companyName varchar(200),"+
                " "+rootJobs.COL_JOBS+" text, " +
                " "+rootJobs.COL_LOGO+" varchar(200))");

        /*db.execSQL("create table if not exists " + jobDetail.TABLE_NAME+
                "("+jobDetail.COL_ID+" int not null,"+
                " "+jobDetail.COL_NAME+" varchar(255),"+
                " "+jobDetail.COL_COMPANY+" varchar(200),"+
                " "+jobDetail.COL_DETAILS+" text,"+
                " "+jobDetail.COL_DATE+" varchar(100),"+
                " "+jobDetail.COL_WEB+" varchar(100),"+
                " "+jobDetail.COL_LOGO+" varchar(100),"+
                " "+jobDetail.COL_SAVE+" int(2),"+
                " "+jobDetail.COL_JOBS+" text,"+
                "primary key(_id))");*/

        db.execSQL("create table if not exists " + subJobs.TABLE_NAME+
                "("+subJobs.COL_ID+" integer PRIMARY KEY not null,"+
                " "+subJobs.COL_JOB_ID+" int not null,"+
                " "+subJobs.COL_NAME+" varchar(255),"+
                " "+subJobs.COL_DETAILS+" text,"+
                " "+subJobs.COL_DATE+" varchar(100),"+
                " "+subJobs.COL_LOGO+" varchar(100),"+
                " "+subJobs.COL_SAVE+" int(2))");

         db.execSQL("create table if not exists " + listTips.TABLE_NAME+
                 "("+listTips.COL_ID+"integer PRIMARY KEY not null,"+
                 " "+listTips.COL_TIP_ID+" int,"+
                 " "+listTips.COL_TITLE+" varchar(255),"+
                 " "+listTips.COL_CAT+" varchar(2))");

        db.execSQL("create table if not exists " + tipDetail.TABLE_NAME+
                "("+tipDetail.COL_ID+" integer PRIMARY KEY not null,"+
                " "+tipDetail.COL_TITLE+" varchar(255),"+
                " "+tipDetail.COL_BODY+" text,"+
                " "+tipDetail.COL_CAT+" varchar(100))");

        db.execSQL("create table if not exists " + jobIndustry.TABLE_NAME+
                "("+jobIndustry.COL_ID+" integer PRIMARY KEY not null,"+
                " "+jobIndustry.COL_TITLE+" varchar(255))");

        db.execSQL("create table if not exists " + jobSpecialisation.TABLE_NAME+
                "("+jobIndustry.COL_ID+" integer PRIMARY KEY not null,"+
                " "+jobIndustry.COL_TITLE+" varchar(255))");

        db.execSQL("create table if not exists " + jobLocation.TABLE_NAME+
                "("+jobIndustry.COL_ID+"integer PRIMARY KEY not null,"+
                " "+jobIndustry.COL_TITLE+" varchar(255))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+rootJobs.TABLE_NAME+"");
        db.execSQL("drop table if exists "+jobDetail.TABLE_NAME+"");
        db.execSQL("drop table if exists "+listTips.TABLE_NAME+"");
        db.execSQL("drop table if exists "+tipDetail.TABLE_NAME+"");
        db.execSQL("drop table if exists "+subJobs.TABLE_NAME+"");
        db.execSQL("drop table if exists "+jobIndustry.TABLE_NAME+"");
        db.execSQL("drop table if exists "+jobLocation.TABLE_NAME+"");
        db.execSQL("drop table if exists "+jobSpecialisation.TABLE_NAME+"");
        onCreate(db);
    }
}
