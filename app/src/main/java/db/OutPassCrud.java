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
 * Created by Jayant Singh on 19/2/17.
 */

public class OutPassCrud {

    private OutPassDbHelper helper;
    private Context context;

    public OutPassCrud(Context context, OutPassDbHelper helper) {
        this.context = context;
        this.helper = helper;
    }

    public void addOrUpdateOutapss(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutpasses());
        else {
            new Thread(() -> {

                SQLiteDatabase database = helper.getWritableDatabase();
                List<String> outpassIDs = getIdList();
                database.beginTransaction();

                String selection;

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outpass = data.get(i);
                    if (!outpassIDs.contains(outpass.getId())) {
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID, outpass.getId());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_UID, outpass.getUid());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_NAME, outpass.getName());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER, outpass.getPhoneNumber());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_BRANCH, outpass.getBranch());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_YEAR, outpass.getYear());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER, outpass.getRoomNumber());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE, outpass.getTimeLeave());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_RETURN, outpass.getTimeReturn());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING, outpass.getPhoneNumberVisiting());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_REASON_VISIT, outpass.getReasonVisit());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS, outpass.getVisitingAddress());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED, outpass.getWardenSigned());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED, outpass.getHodSigned());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED, outpass.getDirectorSigned());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_OUTPASS_TYPE, outpass.getOutPassType());
                        database.insert(OutPassSchema.OutpassCoulmns.TABLE_NAME, null, values);
                    } else {
                        selection = OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID + " = ?";
                        String[] selectionArgs = {outpass.getId()};
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID, outpass.getId());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_NAME, outpass.getName());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER, outpass.getPhoneNumber());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_BRANCH, outpass.getBranch());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_YEAR, outpass.getYear());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER, outpass.getRoomNumber());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE, outpass.getTimeLeave());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_RETURN, outpass.getTimeReturn());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING, outpass.getPhoneNumberVisiting());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_REASON_VISIT, outpass.getReasonVisit());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS, outpass.getVisitingAddress());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED, outpass.getWardenSigned());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED, outpass.getHodSigned());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED, outpass.getDirectorSigned());
                        values.put(OutPassSchema.OutpassCoulmns.COLUMN_OUTPASS_TYPE, outpass.getOutPassType());
                        database.update(OutPassSchema.OutpassCoulmns.TABLE_NAME, values, selection, selectionArgs);
                    }
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutpasses());
            }).start();
        }
    }

    public void deleteAllAndAdd(final List<OutPassModel> data, final AddOrUpdateCallback callback) {
        if (data == null)
            callback.done(getOutpasses());
        else {
            new Thread(() -> {

                deleteOutpasses();

                SQLiteDatabase database = helper.getWritableDatabase();
                database.beginTransaction();

                for (int i = 0; i < data.size(); i++) {
                    ContentValues values = new ContentValues();
                    OutPassModel outpass = data.get(i);
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID, outpass.getId());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_UID, outpass.getUid());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_NAME, outpass.getName());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER, outpass.getPhoneNumber());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_BRANCH, outpass.getBranch());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_YEAR, outpass.getYear());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER, outpass.getRoomNumber());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE, outpass.getTimeLeave());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_RETURN, outpass.getTimeReturn());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING, outpass.getPhoneNumberVisiting());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_REASON_VISIT, outpass.getReasonVisit());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS, outpass.getVisitingAddress());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED, outpass.getWardenSigned());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED, outpass.getHodSigned());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED, outpass.getDirectorSigned());
                    values.put(OutPassSchema.OutpassCoulmns.COLUMN_OUTPASS_TYPE, outpass.getOutPassType());
                    database.insert(OutPassSchema.OutpassCoulmns.TABLE_NAME, null, values);
                }

                database.setTransactionSuccessful();
                database.endTransaction();
                callback.done(getOutpasses());
            }).start();
        }
    }

    public List<OutPassModel> getOutpasses() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_UID,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_NAME,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_BRANCH,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_YEAR,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_RETURN,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_REASON_VISIT,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED,
                OutPassSchema.OutpassCoulmns.COLUMN_OUTPASS_TYPE
        };

        String selection = OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID + " = ?";
        String[] selectionArgs = {};

        String sortOrder = OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                OutPassSchema.OutpassCoulmns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<OutPassModel> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            OutPassModel outpass = new OutPassModel();
            outpass.setId(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID)));
            outpass.setUid(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_UID)));
            outpass.setName(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_NAME)));
            outpass.setPhoneNumber(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER)));
            outpass.setBranch(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_BRANCH)));
            outpass.setYear(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_YEAR)));
            outpass.setRoomNumber(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER)));
            outpass.setTimeLeave(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE)));
            outpass.setTimeReturn(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_RETURN)));
            outpass.setPhoneNumberVisiting(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING)));
            outpass.setReasonVisit(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_REASON_VISIT)));
            outpass.setVisitingAddress(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS)));
            outpass.setWardenSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED)).equals("1")
            );
            outpass.setHodSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED)).equals("1")
            );
            outpass.setDirectorSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED)).equals("1")
            );
            outpass.setOutPassType(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_OUTPASS_TYPE)));
            list.add(outpass);
        }
        cursor.close();
        return list;
    }

    public List<OutPassModel> getOutpassesSigned() {
        SQLiteDatabase database = helper.getReadableDatabase();

        String[] projection = {
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_UID,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_NAME,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_BRANCH,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_YEAR,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_RETURN,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_REASON_VISIT,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED,
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED
        };


        String selection;

        switch (UserInformation.getString(context, UserInformation.StringKey.SCOPE)) {
            case "director":
                selection = OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NOT NULL";
                break;
            case "warden":
                selection = OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED + " IS NOT NULL";
                break;
            case "hod":
                selection = OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED + " IS NOT NULL";
                break;
            default:
                selection = OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED + " IS NOT NULL";
                break;
        }

        String[] selectionArgs = {};

        String sortOrder = OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                OutPassSchema.OutpassCoulmns.TABLE_NAME,
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
            outpass.setId(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID)));
            outpass.setUid(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_UID)));
            outpass.setName(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_NAME)));
            outpass.setPhoneNumber(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER)));
            outpass.setBranch(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_BRANCH)));
            outpass.setYear(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_YEAR)));
            outpass.setRoomNumber(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ROOM_NUMBER)));
            outpass.setTimeLeave(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_LEAVE)));
            outpass.setTimeReturn(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_TIME_RETURN)));
            outpass.setPhoneNumberVisiting(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_PHONE_NUMBER_VISITING)));
            outpass.setReasonVisit(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_REASON_VISIT)));
            outpass.setVisitingAddress(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_VISITING_ADDRESS)));
            outpass.setWardenSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_WARDEN_SIGNED)).equals("1")
            );
            outpass.setHodSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_HOD_SIGNED)).equals("1")
            );
            outpass.setDirectorSigned(
                    cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED)) == null ?
                            null :
                            cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_DIRECTOR_SIGNED)).equals("1")
            );
            outpass.setOutPassType(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_OUTPASS_TYPE)));
            list.add(outpass);
        }
        cursor.close();
        return list;
    }

    public void deleteOutpasses() {
        SQLiteDatabase database = helper.getWritableDatabase();

        database.delete(OutPassSchema.OutpassCoulmns.TABLE_NAME, null, null);

        database.close();
    }

    private List<String> getIdList() {
        SQLiteDatabase database = helper.getReadableDatabase();
        String[] projection = {
                OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID,
        };

        String sortOrder = OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID + " DESC";

        Cursor cursor = database.query(
                OutPassSchema.OutpassCoulmns.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List<String> list = new LinkedList<>();

        while (cursor.moveToNext()) {
            list.add(cursor.getString(cursor.getColumnIndex(OutPassSchema.OutpassCoulmns.COLUMN_NAME_ID)));
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
