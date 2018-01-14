/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.models.OutPassModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jayant Singh on 7/1/18.
 */

public class CrudLeavingToday {

    private DbHelper helper;
    private Context context;

    public CrudLeavingToday(Context context, DbHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void addOrUpdateOutPass(final List<OutPassModel> data, final CrudLeavingToday.AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPasses());
        else {
            new Thread(() -> {

                SQLiteDatabase database = helper.getWritableDatabase();
                List<String> outPassIdList = getIdList();
                database.beginTransaction();

                String selection;

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outPass = data.get(i);
                    if (!outPassIdList.contains(outPass.getId())) {
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID, outPass.getId());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_UID, outPass.getUid());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_NAME, outPass.getName());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_YEAR, outPass.getYear());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
                        database.insert(SchemaLeavingToday.LeavingTodayColumns.TABLE_NAME, null, values);
                    } else {
                        selection = SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID + " = ?";
                        String[] selectionArgs = {outPass.getId()};
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID, outPass.getId());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_NAME, outPass.getName());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_YEAR, outPass.getYear());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
                        values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
                        database.update(SchemaLeavingToday.LeavingTodayColumns.TABLE_NAME, values, selection, selectionArgs);
                    }
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPasses());
            }).start();
        }
    }

    public void deleteAllAndAdd(final List<OutPassModel> data, final CrudLeavingToday.AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPasses());
        else {
            new Thread(() -> {

                deleteOutPasses();

                SQLiteDatabase database = helper.getWritableDatabase();
                database.beginTransaction();

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outPass = data.get(i);
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID, outPass.getId());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_UID, outPass.getUid());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_NAME, outPass.getName());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_YEAR, outPass.getYear());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
                    values.put(SchemaLeavingToday.LeavingTodayColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
                    database.insert(SchemaLeavingToday.LeavingTodayColumns.TABLE_NAME, null, values);
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPasses());
            }).start();
        }
    }

    public List<OutPassModel> getOutPasses() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_UID,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_NAME,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_BRANCH,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_YEAR,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ROOM_NUMBER,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_LEAVE,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_RETURN,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_REASON_VISIT,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_VISITING_ADDRESS,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_WARDEN_SIGNED,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_HOD_SIGNED,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED,
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_OUT_PASS_TYPE
        };

        String sortOrder = SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_RETURN + " DESC";

        Cursor cursor = database.query(
                SchemaLeavingToday.LeavingTodayColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<OutPassModel> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            OutPassModel outPass = new OutPassModel();
            outPass.setId(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID)));
            outPass.setUid(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_UID)));
            outPass.setName(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_NAME)));
            outPass.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER)));
            outPass.setBranch(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_BRANCH)));
            outPass.setYear(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_YEAR)));
            outPass.setRoomNumber(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ROOM_NUMBER)));
            outPass.setTimeLeave(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_LEAVE)));
            outPass.setTimeReturn(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_TIME_RETURN)));
            outPass.setPhoneNumberVisiting(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING)));
            outPass.setReasonVisit(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_REASON_VISIT)));
            outPass.setVisitingAddress(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_VISITING_ADDRESS)));
            outPass.setWardenSigned(
                    cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_WARDEN_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_WARDEN_SIGNED)).equals("1")
            );
            outPass.setHodSigned(
                    cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_HOD_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_HOD_SIGNED)).equals("1")
            );
            outPass.setDirectorSigned(
                    cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED)).equals("1")
            );
            outPass.setOutPassType(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_OUT_PASS_TYPE)));
            list.add(outPass);
        }
        cursor.close();
        return list;
    }

    public void deleteOutPasses() {
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(SchemaLeavingToday.LeavingTodayColumns.TABLE_NAME, null, null);

        database.close();
    }

    private List<String> getIdList() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID,
        };

        String sortOrder = SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaLeavingToday.LeavingTodayColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<String> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(SchemaLeavingToday.LeavingTodayColumns.COLUMN_NAME_ID)));
        }
        cursor.close();

        List<String> data = new ArrayList<>(list.size());
        data.addAll(list);
        return data;
    }

    public interface AddOrUpdateCallback {
        void done(List<OutPassModel> outPasses);
    }
}