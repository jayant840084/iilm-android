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

public class CrudLeftToday {

    private final String[] projection = {
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_UID,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_NAME,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_PHONE_NUMBER,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_BRANCH,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_YEAR,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ROOM_NUMBER,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_TIME_LEAVE,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_TIME_RETURN,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_REASON_VISIT,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_STUDENT_REMARK,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_VISITING_ADDRESS,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_SIGNED,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_REMARK,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_SIGNED,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_REMARK,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_REMARK,
            SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN,
            SchemaLeftToday.LeftTodayColumns.COLUMN_OUT_PASS_TYPE
    };

    private DbHelper helper;
    private Context context;

    public CrudLeftToday(Context context, DbHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void addOrUpdateOutPass(final List<OutPassModel> data, final CrudLeftToday.AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPasses());
        else {
            new Thread(() -> {

                SQLiteDatabase database = helper.getWritableDatabase();
                List<String> outPassIdList = getIdList();
                database.beginTransaction();

                String selection;

                ContentValues values;
                for (int i = 0; i < data.size(); i++) {
                    OutPassModel outPass = data.get(i);
                    if (!outPassIdList.contains(outPass.getId())) {
                        values = packValues(outPass);
                        database.insert(SchemaLeftToday.LeftTodayColumns.TABLE_NAME, null, values);
                    } else {
                        selection = SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID + " = ?";
                        String[] selectionArgs = {outPass.getId()};
                        values = packValues(outPass);
                        database.update(SchemaLeftToday.LeftTodayColumns.TABLE_NAME, values, selection, selectionArgs);
                    }
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPasses());
            }).start();
        }
    }

    public void deleteAllAndAdd(final List<OutPassModel> data, final CrudLeftToday.AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPasses());
        else {
            new Thread(() -> {

                deleteOutPasses();

                SQLiteDatabase database = helper.getWritableDatabase();
                database.beginTransaction();

                ContentValues values;
                for (int i = 0; i < data.size(); i++) {
                    OutPassModel outPass = data.get(i);
                    values = packValues(outPass);
                    database.insert(SchemaLeftToday.LeftTodayColumns.TABLE_NAME, null, values);
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPasses());
            }).start();
        }
    }

    public OutPassModel getOutPass(String passId) {
        SQLiteDatabase database = helper.getReadableDatabase();

        String selection = SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {
                passId
        };

        String sortOrder = SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaLeftToday.LeftTodayColumns.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        cursor.moveToFirst();
        return cursorGetOutPass(cursor);
    }

    public List<OutPassModel> getOutPasses() {
        SQLiteDatabase database = helper.getReadableDatabase();

        String sortOrder = SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaLeftToday.LeftTodayColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<OutPassModel> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            OutPassModel outPass = cursorGetOutPass(cursor);
            list.add(outPass);
        }
        cursor.close();
        return list;
    }

    public void deleteOutPasses() {
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(SchemaLeftToday.LeftTodayColumns.TABLE_NAME, null, null);

        database.close();
    }

    private List<String> getIdList() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID,
        };

        String sortOrder = SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_TIME_LEAVE + " DESC";

        Cursor cursor = database.query(
                SchemaLeftToday.LeftTodayColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<String> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID)));
        }
        cursor.close();

        List<String> data = new ArrayList<>(list.size());
        data.addAll(list);
        return data;
    }

    private ContentValues packValues(OutPassModel outPass) {
        ContentValues values = new ContentValues();
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID, outPass.getId());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_UID, outPass.getUid());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_NAME, outPass.getName());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_YEAR, outPass.getYear());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_STUDENT_REMARK, outPass.getStudentRemark());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_REMARK, outPass.getWardenRemark());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT, outPass.getWardenTalkedToParent());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_REMARK, outPass.getHodRemark());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT, outPass.getHodTalkedToParent());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_REMARK, outPass.getDirectorRemark());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN, outPass.getDirectorPrioritySign());
        values.put(SchemaLeftToday.LeftTodayColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
        return values;
    }

    private OutPassModel cursorGetOutPass(Cursor cursor) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ID)));
        outPass.setUid(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_UID)));
        outPass.setName(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_NAME)));
        outPass.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_PHONE_NUMBER)));
        outPass.setBranch(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_BRANCH)));
        outPass.setYear(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_YEAR)));
        outPass.setRoomNumber(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_ROOM_NUMBER)));
        outPass.setTimeLeave(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_TIME_LEAVE)));
        outPass.setTimeReturn(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_TIME_RETURN)));
        outPass.setPhoneNumberVisiting(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_PHONE_NUMBER_VISITING)));
        outPass.setReasonVisit(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_REASON_VISIT)));
        outPass.setStudentRemark(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_STUDENT_REMARK)));
        outPass.setVisitingAddress(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_VISITING_ADDRESS)));
        outPass.setWardenSigned(
                cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_SIGNED)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_SIGNED)).equals("1")
        );
        outPass.setWardenRemark(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_REMARK)));
        outPass.setWardenTalkedToParent(
                cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT)).equals("1")
        );
        outPass.setHodSigned(
                cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_SIGNED)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_SIGNED)).equals("1")
        );
        outPass.setHodRemark(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_REMARK)));
        outPass.setHodTalkedToParent(
                cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT)).equals("1")
        );
        outPass.setDirectorSigned(
                cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_SIGNED)).equals("1")
        );
        outPass.setDirectorRemark(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_REMARK)));
        outPass.setDirectorPrioritySign(
                cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN)).equals("1")
        );
        outPass.setOutPassType(cursor.getString(cursor.getColumnIndex(SchemaLeftToday.LeftTodayColumns.COLUMN_OUT_PASS_TYPE)));
        return outPass;
    }

    public interface AddOrUpdateCallback {
        void done(List<OutPassModel> outPasses);
    }
}
