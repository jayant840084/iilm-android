/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.models.OutPassModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import constants.OutPassSource;
import utils.UserInformation;

/**
 * Created by Jayant Singh on 29-03-2017.
 */

public class CrudOutPassSigned {

    private static final String[] projection = {
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_UID,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_NAME,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_PHONE_NUMBER,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_BRANCH,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_YEAR,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ROOM_NUMBER,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_TIME_LEAVE,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_TIME_RETURN,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_REASON_VISIT,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_STUDENT_REMARK,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_REMARK,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_SIGNED,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_REMARK,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_REMARK,
            SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN,
            SchemaOutPassSigned.OutPassColumns.COLUMN_OUT_PASS_TYPE
    };

    private DbHelper helper;
    private Context context;

    public CrudOutPassSigned(Context context, DbHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void addOrUpdateOutPass(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPassesSigned());
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
                        database.insert(SchemaOutPassSigned.OutPassColumns.TABLE_NAME, null, values);
                    } else {
                        selection = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID + " = ?";
                        String[] selectionArgs = {outPass.getId()};
                        values = packValues(outPass);
                        database.update(SchemaOutPassSigned.OutPassColumns.TABLE_NAME, values, selection, selectionArgs);
                    }
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPassesSigned());
            }).start();
        }
    }

    public void deleteAllAndAdd(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPassesSigned());
        else {
            new Thread(() -> {

                deleteOutPasses();

                SQLiteDatabase database = helper.getWritableDatabase();
                database.beginTransaction();

                ContentValues values;
                for (int i = 0; i < data.size(); i++) {
                    OutPassModel outPass = data.get(i);
                    values = packValues(outPass);
                    database.insert(SchemaOutPassSigned.OutPassColumns.TABLE_NAME, null, values);
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPassesSigned());
            }).start();
        }
    }

    public OutPassModel getOutPass(String passId) {
        SQLiteDatabase database = helper.getReadableDatabase();

        String selection = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID + " = ?";

        String[] selectionArgs = {
                passId
        };

        String sortOrder = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaOutPassSigned.OutPassColumns.TABLE_NAME,
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

    public List<OutPassModel> getOutPassesSigned() {
        SQLiteDatabase database = helper.getReadableDatabase();

        String selection;

        switch (UserInformation.getString(context, UserInformation.StringKey.SCOPE)) {
            case "director":
                selection = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NOT NULL";
                break;
            case "warden":
                selection = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED + " IS NOT NULL";
                break;
            case "hod":
                selection = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_SIGNED + " IS NOT NULL";
                break;
            default:
                selection = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NOT NULL";
                break;
        }

        String[] selectionArgs = {};

        String sortOrder = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaOutPassSigned.OutPassColumns.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
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
        List<OutPassModel> data = new ArrayList<>();
        data.addAll(list);
        return data;
    }

    public void deleteOutPasses() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete(SchemaOutPassSigned.OutPassColumns.TABLE_NAME, null, null);
        database.close();
    }

    List<String> getIdList() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID,
        };

        String sortOrder = SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaOutPassSigned.OutPassColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<String> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID)));
        }
        cursor.close();

        List<String> data = new ArrayList<>(list.size());
        data.addAll(list);
        return data;
    }

    private ContentValues packValues(OutPassModel outPass) {
        ContentValues values = new ContentValues();
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID, outPass.getId());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_UID, outPass.getUid());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_NAME, outPass.getName());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_YEAR, outPass.getYear());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_STUDENT_REMARK, outPass.getStudentRemark());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_REMARK, outPass.getWardenRemark());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT, outPass.getWardenTalkedToParent());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_REMARK, outPass.getHodRemark());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT, outPass.getHodTalkedToParent());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_REMARK, outPass.getDirectorRemark());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN, outPass.getDirectorPrioritySign());
        values.put(SchemaOutPassSigned.OutPassColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
        return values;
    }

    private OutPassModel cursorGetOutPass(Cursor cursor) {
        OutPassModel outPass = new OutPassModel();
        outPass.setId(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ID)));
        outPass.setUid(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_UID)));
        outPass.setName(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_NAME)));
        outPass.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_PHONE_NUMBER)));
        outPass.setBranch(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_BRANCH)));
        outPass.setYear(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_YEAR)));
        outPass.setRoomNumber(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_ROOM_NUMBER)));
        outPass.setTimeLeave(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_TIME_LEAVE)));
        outPass.setTimeReturn(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_TIME_RETURN)));
        outPass.setPhoneNumberVisiting(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING)));
        outPass.setReasonVisit(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_REASON_VISIT)));
        outPass.setStudentRemark(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_STUDENT_REMARK)));
        outPass.setVisitingAddress(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS)));
        outPass.setWardenSigned(
                cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED)).equals("1")
        );
        outPass.setWardenRemark(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_REMARK)));
        outPass.setWardenTalkedToParent(
                cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_WARDEN_TALKED_TO_PARENT)).equals("1")
        );
        outPass.setHodSigned(
                cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_SIGNED)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_SIGNED)).equals("1")
        );
        outPass.setHodRemark(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_REMARK)));
        outPass.setHodTalkedToParent(
                cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_HOD_TALKED_TO_PARENT)).equals("1")
        );
        outPass.setDirectorSigned(
                cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED)).equals("1")
        );
        outPass.setDirectorRemark(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_REMARK)));
        outPass.setDirectorPrioritySign(
                cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN)) == null ?
                        null :
                        cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_NAME_DIRECTOR_PRIORITY_SIGN)).equals("1")
        );
        outPass.setOutPassType(cursor.getString(cursor.getColumnIndex(SchemaOutPassSigned.OutPassColumns.COLUMN_OUT_PASS_TYPE)));
        return outPass;
    }

    public interface AddOrUpdateCallback {
        void done(List<OutPassModel> outPasses);
    }
}
