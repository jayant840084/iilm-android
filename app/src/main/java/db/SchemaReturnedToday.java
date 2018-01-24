/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package db;

import android.provider.BaseColumns;

/**
 * Created by Jayant Singh on 7/1/18.
 */

public class SchemaReturnedToday {

    static final String CREATE_ENTRIES = "CREATE TABLE " +
            ReturnedTodayColumns.TABLE_NAME + " (" +
            ReturnedTodayColumns._ID + " INTEGER PRIMARY KEY," +
            ReturnedTodayColumns.COLUMN_NAME_ID + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_UID + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_NAME + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_PHONE_NUMBER + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_BRANCH + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_YEAR + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_ROOM_NUMBER + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_TIME_LEAVE + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_TIME_RETURN + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_REASON_VISIT + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_STUDENT_REMARK + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_VISITING_ADDRESS + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_WARDEN_SIGNED + " BOOLEAN null," +
            ReturnedTodayColumns.COLUMN_NAME_WARDEN_REMARK + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT + " BOOLEAN null," +
            ReturnedTodayColumns.COLUMN_NAME_HOD_SIGNED + " BOOLEAN null," +
            ReturnedTodayColumns.COLUMN_NAME_HOD_REMARK + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT + " BOOLEAN null," +
            ReturnedTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED + " BOOLEAN null," +
            ReturnedTodayColumns.COLUMN_NAME_DIRECTOR_REMARK + " TEXT," +
            ReturnedTodayColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN + " BOOLEAN null," +
            ReturnedTodayColumns.COLUMN_OUT_PASS_TYPE + " TEXT" + ")";

    private SchemaReturnedToday() {
    }

    static final class ReturnedTodayColumns implements BaseColumns {
        static final String TABLE_NAME = "returnedToday";
        static final String COLUMN_NAME_ID = "id";
        static final String COLUMN_NAME_UID = "uid";
        static final String COLUMN_NAME_NAME = "name";
        static final String COLUMN_NAME_PHONE_NUMBER = "phoneNumber";
        static final String COLUMN_NAME_BRANCH = "branch";
        static final String COLUMN_NAME_YEAR = "year";
        static final String COLUMN_NAME_ROOM_NUMBER = "roomNumber";
        static final String COLUMN_NAME_TIME_LEAVE = "timeLeave";
        static final String COLUMN_NAME_TIME_RETURN = "timeReturn";
        static final String COLUMN_NAME_PHONE_NUMBER_VISITING = "phoneNumberVisiting";
        static final String COLUMN_NAME_REASON_VISIT = "reasonVisit";
        static final String COLUMN_NAME_STUDENT_REMARK = "studentRemark";
        static final String COLUMN_NAME_VISITING_ADDRESS = "visitingAddress";
        static final String COLUMN_NAME_WARDEN_SIGNED = "wardenSigned";
        static final String COLUMN_NAME_WARDEN_REMARK = "wardenRemark";
        static final String COLUMN_NAME_WARDEN_TALKED_TO_PARENT = "wardenTalkParent";
        static final String COLUMN_NAME_HOD_SIGNED = "hodSigned";
        static final String COLUMN_NAME_HOD_REMARK = "hodRemark";
        static final String COLUMN_NAME_HOD_TALKED_TO_PARENT = "hodTalkParent";
        static final String COLUMN_NAME_DIRECTOR_SIGNED = "directorSigned";
        static final String COLUMN_NAME_DIRECTOR_REMARK = "directorRemark";
        static final String COLUMN_NAME_DIRECTOR_PRIORITY_SIGN = "directorPrioritySign";
        static final String COLUMN_OUT_PASS_TYPE = "outPassType";
    }
}
