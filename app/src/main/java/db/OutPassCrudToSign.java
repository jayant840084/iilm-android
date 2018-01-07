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
 * Created by jayan on 29-03-2017.
 */

public class OutPassCrudToSign {

    private OutPassDbHelper helper;
    private Context context;

    public OutPassCrudToSign(Context context, OutPassDbHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void addOrUpdateOutapss(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutpassesToSign());
        else {
            new Thread(() -> {

                SQLiteDatabase database = helper.getWritableDatabase();
                final List<String> outpassIDs = getIdList();
                final List<String> outpassIDsSigned = new OutPassCrudSigned(context,
                        new OutPassDbHelper(context)).getIdList();
                database.beginTransaction();

                String selection;

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outpass = data.get(i);
                    if (outpassIDsSigned.contains(outpass.getId())) {
                        selection = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID + " = ?";
                        String[] selectionArgs = {outpass.getId()};
                        database.delete(OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME, selection, selectionArgs);

//                            selection = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID + " = ?";
//                            String[] selectionArgs = { outpass.getId() };
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID, outpass.getId());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_NAME, outpass.getName());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER, outpass.getPhoneNumber());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_BRANCH, outpass.getBranch());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_YEAR, outpass.getYear());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER, outpass.getRoomNumber());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE, outpass.getTimeLeave());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_RETURN, outpass.getTimeReturn());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING, outpass.getPhoneNumberVisiting());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_REASON_VISIT, outpass.getReasonVisit());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS, outpass.getVisitingAddress());
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED, true);
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED, true);
//                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED, true);
//                            database.update(OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME, values, selection, selectionArgs);
                    } else {
                        if (!outpassIDs.contains(outpass.getId())) {
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID, outpass.getId());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_UID, outpass.getUid());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_NAME, outpass.getName());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER, outpass.getPhoneNumber());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_BRANCH, outpass.getBranch());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_YEAR, outpass.getYear());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER, outpass.getRoomNumber());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE, outpass.getTimeLeave());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_RETURN, outpass.getTimeReturn());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING, outpass.getPhoneNumberVisiting());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_REASON_VISIT, outpass.getReasonVisit());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS, outpass.getVisitingAddress());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED, outpass.getWardenSigned());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED, outpass.getHodSigned());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED, outpass.getDirectorSigned());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_OUTPASS_TYPE, outpass.getOutPassType());
                            database.insert(OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME, null, values);
                        } else {
                            selection = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID + " = ?";
                            String[] selectionArgs = {outpass.getId()};
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID, outpass.getId());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_UID, outpass.getUid());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_NAME, outpass.getName());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER, outpass.getPhoneNumber());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_BRANCH, outpass.getBranch());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_YEAR, outpass.getYear());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER, outpass.getRoomNumber());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE, outpass.getTimeLeave());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_RETURN, outpass.getTimeReturn());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING, outpass.getPhoneNumberVisiting());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_REASON_VISIT, outpass.getReasonVisit());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS, outpass.getVisitingAddress());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED, outpass.getWardenSigned());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED, outpass.getHodSigned());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED, outpass.getDirectorSigned());
                            values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_OUTPASS_TYPE, outpass.getOutPassType());
                            database.update(OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME, values, selection, selectionArgs);
                        }
                    }
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutpassesToSign());
            }).start();
        }
    }

    public void deleteAllAndAdd(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutpassesToSign());
        else {
            new Thread(() -> {

                deleteOutpasses();

                SQLiteDatabase database = helper.getWritableDatabase();
                database.beginTransaction();

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outpass = data.get(i);
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID, outpass.getId());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_UID, outpass.getUid());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_NAME, outpass.getName());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER, outpass.getPhoneNumber());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_BRANCH, outpass.getBranch());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_YEAR, outpass.getYear());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER, outpass.getRoomNumber());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE, outpass.getTimeLeave());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_RETURN, outpass.getTimeReturn());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING, outpass.getPhoneNumberVisiting());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_REASON_VISIT, outpass.getReasonVisit());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS, outpass.getVisitingAddress());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED, outpass.getWardenSigned());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED, outpass.getHodSigned());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED, outpass.getDirectorSigned());
                    values.put(OutPassSchemaToSign.OutpassCoulmns.COLUMN_OUTPASS_TYPE, outpass.getOutPassType());
                    database.insert(OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME, null, values);
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutpassesToSign());
            }).start();
        }
    }

    public List<OutPassModel> getOutpassesToSign() {
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] projection = {
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_UID,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_NAME,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_BRANCH,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_YEAR,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_RETURN,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_REASON_VISIT,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED,
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_OUTPASS_TYPE
        };


        String selection;

        switch (UserInformation.getString(context, UserInformation.StringKey.SCOPE)) {
            case "director":
                selection = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NULL";
                break;
            case "warden":
                selection = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED + " IS NULL";
                break;
            case "hod":
                selection = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED + " IS NULL";
                break;
            default:
                selection = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NULL";
                break;
        }

        String[] selectionArgs = {};

        String sortOrder = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );


        List<OutPassModel> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            OutPassModel outpass = new OutPassModel();
            outpass.setId(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID)));
            outpass.setUid(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_UID)));
            outpass.setName(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_NAME)));
            outpass.setPhoneNumber(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER)));
            outpass.setBranch(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_BRANCH)));
            outpass.setYear(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_YEAR)));
            outpass.setRoomNumber(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER)));
            outpass.setTimeLeave(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE)));
            outpass.setTimeReturn(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_TIME_RETURN)));
            outpass.setPhoneNumberVisiting(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING)));
            outpass.setReasonVisit(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_REASON_VISIT)));
            outpass.setVisitingAddress(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS)));
            outpass.setWardenSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED)).equals("1")
            );
            outpass.setHodSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED)).equals("1")
            );
            outpass.setDirectorSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED)).equals("1")
            );
            outpass.setOutPassType(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_OUTPASS_TYPE)));
            list.add(outpass);
        }
        cursor.close();

        List<OutPassModel> data = new ArrayList<>(list.size());
        data.addAll(list);
        return data;
    }

    public void deleteOutpasses() {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete(OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME, null, null);
        database.close();
    }

    public List<String> getIdList() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID,
        };

        String sortOrder = OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<String> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_ID)));
        }
        cursor.close();

        List<String> data = new ArrayList<>(list.size());
        data.addAll(list);

        return data;
    }

    public interface AddOrUpdateCallback {
        void done(List<OutPassModel> outpasses);
    }
}
