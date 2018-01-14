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

import utils.UserInformation;

/**
 * Created by Jayant Singh on 29-03-2017.
 */

public class CrudOutPassToSign {

    private DbHelper helper;
    private Context context;

    public CrudOutPassToSign(Context context, DbHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void addOrUpdateOutPass(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPassesToSign());
        else {
            new Thread(() -> {

                SQLiteDatabase database = helper.getWritableDatabase();
                final List<String> outPassIdList = getIdList();
                final List<String> outPassSignedIdList = new CrudOutPassSigned(context,
                        new DbHelper(context)).getIdList();
                database.beginTransaction();

                String selection;

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outPass = data.get(i);
                    if (outPassSignedIdList.contains(outPass.getId())) {
                        selection = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID + " = ?";
                        String[] selectionArgs = {outPass.getId()};
                        database.delete(SchemaOutPassToSign.OutPassColumns.TABLE_NAME, selection, selectionArgs);

//                            selection = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID + " = ?";
//                            String[] selectionArgs = { outPass.getId() };
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID, outPass.getId());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_NAME, outPass.getName());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_YEAR, outPass.getYear());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED, true);
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED, true);
//                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED, true);
//                            database.update(SchemaOutPassToSign.OutPassColumns.TABLE_NAME, values, selection, selectionArgs);
                    } else {
                        if (!outPassIdList.contains(outPass.getId())) {
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID, outPass.getId());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_UID, outPass.getUid());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_NAME, outPass.getName());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_YEAR, outPass.getYear());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
                            database.insert(SchemaOutPassToSign.OutPassColumns.TABLE_NAME, null, values);
                        } else {
                            selection = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID + " = ?";
                            String[] selectionArgs = {outPass.getId()};
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID, outPass.getId());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_UID, outPass.getUid());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_NAME, outPass.getName());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_YEAR, outPass.getYear());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
                            values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
                            database.update(SchemaOutPassToSign.OutPassColumns.TABLE_NAME, values, selection, selectionArgs);
                        }
                    }
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPassesToSign());
            }).start();
        }
    }

    public void deleteAllAndAdd(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutPassesToSign());
        else {
            new Thread(() -> {

                deleteOutPasses();

                SQLiteDatabase database = helper.getWritableDatabase();
                database.beginTransaction();

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outPass = data.get(i);
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID, outPass.getId());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_UID, outPass.getUid());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_NAME, outPass.getName());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER, outPass.getPhoneNumber());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_BRANCH, outPass.getBranch());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_YEAR, outPass.getYear());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ROOM_NUMBER, outPass.getRoomNumber());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_LEAVE, outPass.getTimeLeave());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_RETURN, outPass.getTimeReturn());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING, outPass.getPhoneNumberVisiting());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_REASON_VISIT, outPass.getReasonVisit());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS, outPass.getVisitingAddress());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED, outPass.getWardenSigned());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED, outPass.getHodSigned());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED, outPass.getDirectorSigned());
                    values.put(SchemaOutPassToSign.OutPassColumns.COLUMN_OUT_PASS_TYPE, outPass.getOutPassType());
                    database.insert(SchemaOutPassToSign.OutPassColumns.TABLE_NAME, null, values);
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutPassesToSign());
            }).start();
        }
    }

    public List<OutPassModel> getOutPassesToSign() {
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] projection = {
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_UID,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_NAME,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_BRANCH,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_YEAR,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ROOM_NUMBER,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_LEAVE,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_RETURN,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_REASON_VISIT,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED,
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED,
                SchemaOutPassToSign.OutPassColumns.COLUMN_OUT_PASS_TYPE
        };


        String selection;

        switch (UserInformation.getString(context, UserInformation.StringKey.SCOPE)) {
            case "director":
                selection = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NULL";
                break;
            case "warden":
                selection = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED + " IS NULL";
                break;
            case "hod":
                selection = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED + " IS NULL";
                break;
            default:
                selection = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NULL";
                break;
        }

        String[] selectionArgs = {};

        String sortOrder = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaOutPassToSign.OutPassColumns.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );


        List<OutPassModel> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            OutPassModel outPass = new OutPassModel();
            outPass.setId(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID)));
            outPass.setUid(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_UID)));
            outPass.setName(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_NAME)));
            outPass.setPhoneNumber(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER)));
            outPass.setBranch(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_BRANCH)));
            outPass.setYear(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_YEAR)));
            outPass.setRoomNumber(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ROOM_NUMBER)));
            outPass.setTimeLeave(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_LEAVE)));
            outPass.setTimeReturn(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_TIME_RETURN)));
            outPass.setPhoneNumberVisiting(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_PHONE_NUMBER_VISITING)));
            outPass.setReasonVisit(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_REASON_VISIT)));
            outPass.setVisitingAddress(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_VISITING_ADDRESS)));
            outPass.setWardenSigned(
                    cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_WARDEN_SIGNED)).equals("1")
            );
            outPass.setHodSigned(
                    cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_HOD_SIGNED)).equals("1")
            );
            outPass.setDirectorSigned(
                    cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_DIRECTOR_SIGNED)).equals("1")
            );
            outPass.setOutPassType(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_OUT_PASS_TYPE)));
            list.add(outPass);
        }
        cursor.close();

        List<OutPassModel> data = new ArrayList<>(list.size());
        data.addAll(list);
        return data;
    }

    public void deleteOutPasses() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete(SchemaOutPassToSign.OutPassColumns.TABLE_NAME, null, null);
        database.close();
    }

    private List<String> getIdList() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID,
        };

        String sortOrder = SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                SchemaOutPassToSign.OutPassColumns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<String> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(SchemaOutPassToSign.OutPassColumns.COLUMN_NAME_ID)));
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
