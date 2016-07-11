package com.mylelojobs.android.mylelojobs;

import android.provider.BaseColumns;

/**
 * Created by Pegasus Amazing on 7/7/2016.
 */
public class dbcontract {
    public class rootJobs implements BaseColumns{
        public static final String TABLE_NAME ="rootJobs";
        public static final String COL_ID = "_id";
        public static final String COL_JOB_ID = "jobId";
        public static final String COL_NAME = "jobName";
        public static final String COL_LOGO = "companyLogo";
        public static final String COL_JOBS = "subJobs";
    }

    public class jobDetail implements BaseColumns{
        public static final String TABLE_NAME = "jobDetail";
        public static final String COL_ID = "_id";
        public static final String COL_NAME = "jobName";
        public static final String COL_COMPANY = "companyName";
        public static final String COL_WEB = "companyWebsite";
        public static final String COL_DATE = "expiryDate";
        public static final String COL_DETAILS = "jobDetails";
        public static final String COL_SAVE = "saved";
        public static final String COL_LOGO = "companyLogo";
        public static final String COL_JOBS = "subJobs";
    }

    public class subJobs implements BaseColumns{
        public static final String TABLE_NAME = "subJobs";
        public static final String COL_ID = "_id";
        public static final String COL_JOB_ID = "jobId";
        public static final String COL_NAME = "subName";
        public static final String COL_DATE = "expiryDate";
        public static final String COL_DETAILS = "jobDetails";
        public static final String COL_SAVE = "saved";
        public static final String COL_LOGO = "companyLogo";
    }

    public class listTips implements BaseColumns{
        public static final String TABLE_NAME = "listTips";
        public static final String COL_ID = "_id";
        public static final String COL_TIP_ID = "tipId";
        public static final String COL_TITLE = "tipTitle";
        public static final String COL_CAT = "tipCategory";
    }

    public class tipDetail implements BaseColumns{
        public static final String TABLE_NAME = "tipDetail";
        public static final String COL_ID = "_id";
        public static final String COL_TITLE = "tipTitle";
        public static final String COL_BODY = "tipBody";
        public static final String COL_CAT = "tipCategory";
    }

    public class jobIndustry implements BaseColumns{
        public static final String TABLE_NAME = "jobIndustry";
        public static final String COL_ID = "_id";
        public static final String COL_TITLE = "industryTitle";
    }

    public class jobSpecialisation implements BaseColumns{
        public static final String TABLE_NAME = "jobSpecialisation";
        public static final String COL_ID = "_id";
        public static final String COL_TITLE = "title";
    }

    public class jobLocation implements BaseColumns{
        public static final String TABLE_NAME = "jobLocation";
        public static final String COL_ID = "_id";
        public static final String COL_TITLE = "title";
    }
}
