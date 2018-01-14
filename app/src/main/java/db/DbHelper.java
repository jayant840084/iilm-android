/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jayant Singh on 19/2/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "outPass.db";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        dropAllTables(db);
        db.execSQL(SchemaOutPass.CREATE_ENTRIES);
        db.execSQL(SchemaOutPassSigned.CREATE_ENTRIES);
        db.execSQL(SchemaOutPassToSign.CREATE_ENTRIES);
        db.execSQL(SchemaLeavingToday.CREATE_ENTRIES);
        db.execSQL(SchemaYetToReturn.CREATE_ENTRIES);
        db.execSQL(SchemaReturnedToday.CREATE_ENTRIES);
        db.execSQL(SchemaLeftToday.CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void dropAllTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + SchemaOutPass.OutPassColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SchemaOutPassSigned.OutPassColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SchemaOutPassToSign.OutPassColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SchemaLeavingToday.LeavingTodayColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SchemaYetToReturn.YetToReturnColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SchemaReturnedToday.ReturnedTodayColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SchemaLeftToday.LeftTodayColumns.TABLE_NAME);
    }
}
