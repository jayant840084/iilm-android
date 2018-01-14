/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package db;

import android.provider.BaseColumns;

/**
 * Created by Jayant Singh on 7/1/18.
 */

class SchemaLeavingToday {

    static final String CREATE_ENTRIES = "CREATE TABLE " +
            LeavingTodayColumns.TABLE_NAME + " (" +
            LeavingTodayColumns._ID + " INTEGER PRIMARY KEY," +
            LeavingTodayColumns.COLUMN_NAME_ID + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_UID + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_NAME + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_BRANCH + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_YEAR + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_ROOM_NUMBER + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_TIME_LEAVE + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_TIME_RETURN + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_REASON_VISIT + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_VISITING_ADDRESS + " TEXT," +
            LeavingTodayColumns.COLUMN_NAME_WARDEN_SIGNED + " BOOLEAN null," +
            LeavingTodayColumns.COLUMN_NAME_HOD_SIGNED + " BOOLEAN null," +
            LeavingTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED + " BOOLEAN null," +
            LeavingTodayColumns.COLUMN_OUT_PASS_TYPE + " TEXT" + ")";

    private SchemaLeavingToday() {
    }

    static final class LeavingTodayColumns implements BaseColumns {
        static final String TABLE_NAME = "leavingToday";
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
        static final String COLUMN_NAME_VISITING_ADDRESS = "visitingAddress";
        static final String COLUMN_NAME_WARDEN_SIGNED = "wardenSigned";
        static final String COLUMN_NAME_HOD_SIGNED = "hodSigned";
        static final String COLUMN_NAME_DIRECTOR_SIGNED = "directorSigned";
        static final String COLUMN_OUT_PASS_TYPE = "outPassType";
    }
}
