/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package db;

import android.provider.BaseColumns;

/**
 * Created by Jayant Singh on 7/1/18.
 */

class SchemaYetToReturn {
    static final String CREATE_ENTRIES = "CREATE TABLE " +
            YetToReturnColumns.TABLE_NAME + " (" +
            YetToReturnColumns._ID + " INTEGER PRIMARY KEY," +
            YetToReturnColumns.COLUMN_NAME_ID + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_UID + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_NAME + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_PHONE_NUMBER + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_BRANCH + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_YEAR + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_ROOM_NUMBER + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_TIME_LEAVE + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_TIME_RETURN + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_PHONE_NUMBER_VISITING + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_REASON_VISIT + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_STUDENT_REMARK + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_VISITING_ADDRESS + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_WARDEN_SIGNED + " BOOLEAN null," +
            YetToReturnColumns.COLUMN_NAME_WARDEN_REMARK + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT + " BOOLEAN null," +
            YetToReturnColumns.COLUMN_NAME_HOD_SIGNED + " BOOLEAN null," +
            YetToReturnColumns.COLUMN_NAME_HOD_REMARK + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT + " BOOLEAN null," +
            YetToReturnColumns.COLUMN_NAME_DIRECTOR_SIGNED + " BOOLEAN null," +
            YetToReturnColumns.COLUMN_NAME_DIRECTOR_REMARK + " TEXT," +
            YetToReturnColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN + " BOOLEAN null," +
            YetToReturnColumns.COLUMN_OUT_PASS_TYPE + " TEXT" + ")";

    private SchemaYetToReturn() {
    }

    static final class YetToReturnColumns implements BaseColumns {
        static final String TABLE_NAME = "yetToReturn";
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
