package db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sherlock on 19/2/17.
 */

public class OutPassDBHelper extends SQLiteOpenHelper {

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "outpass.db";

    public OutPassDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(OutPassSchema.CREATE_ENTRIES);
        db.execSQL(OutPassSchemaSigned.CREATE_ENTRIES);
        db.execSQL(OutPassSchemaToSign.CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion > 1) {
            db.execSQL("ALTER TABLE " +
                    OutPassSchema.OutpassCoulmns.TABLE_NAME +
                    " ADD COLUMN " + OutPassSchema.OutpassCoulmns.COLUMN_NAME_UID +
                    " TEXT"
            );
            db.execSQL("ALTER TABLE " +
                    OutPassSchemaSigned.OutpassCoulmns.TABLE_NAME +
                    " ADD COLUMN " + OutPassSchemaSigned.OutpassCoulmns.COLUMN_NAME_UID +
                    " TEXT"
            );
            db.execSQL("ALTER TABLE " +
                    OutPassSchemaToSign.OutpassCoulmns.TABLE_NAME +
                    " ADD COLUMN " + OutPassSchemaToSign.OutpassCoulmns.COLUMN_NAME_UID +
                    " TEXT"
            );
        }
    }
}
