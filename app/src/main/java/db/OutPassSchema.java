package db;

import android.provider.BaseColumns;

/**
 * Created by sherlock on 19/2/17.
 */

public final class OutPassSchema {

    static final String CREATE_ENTRIES = "CREATE TABLE " +
            OutpassCoulmns.TABLE_NAME + " (" +
            OutpassCoulmns._ID + " INTEGER PRIMARY KEY," +
            OutpassCoulmns.COLUMN_NAME_ID + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_UID + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_NAME + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_BRANCH + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_YEAR + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_TIME_LEAVE + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_TIME_RETURN + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_REASON_VISIT + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS + " TEXT," +
            OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED + " BOOLEAN null," +
            OutpassCoulmns.COLUMN_NAME_HOD_SIGNED + " BOOLEAN null," +
            OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED + " BOOLEAN null," +
            OutpassCoulmns.COLUMN_OUTPASS_TYPE + " TEXT" + ")";

    private OutPassSchema() {
    }

    public static class OutpassCoulmns implements BaseColumns {
        public static final String TABLE_NAME = "outpass";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PHONE_NUMBER = "phoneNumber";
        public static final String COLUMN_NAME_BRANCH = "branch";
        public static final String COLUMN_NAME_YEAR = "year";
        public static final String COLUMN_NAME_ROOM_NUMBER = "roomNumber";
        public static final String COLUMN_NAME_TIME_LEAVE = "timeLeave";
        public static final String COLUMN_NAME_TIME_RETURN = "timeReturn";
        public static final String COLUMN_NAME_PHONE_NUMBER_VISITING = "phoneNumberVisiting";
        public static final String COLUMN_NAME_REASON_VISIT = "reasonVisit";
        public static final String COLUMN_NAME_VISITING_ADDRESS = "visitingAddress";
        public static final String COLUMN_NAME_WARDEN_SIGNED = "wardenSigned";
        public static final String COLUMN_NAME_HOD_SIGNED = "hodSigned";
        public static final String COLUMN_NAME_DIRECTOR_SIGNED = "directorSigned";
        public static final String COLUMN_OUTPASS_TYPE = "outpassType";
    }
}
