package utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.Calendar;

/**
 * Created by sherlock on 5/7/17.
 */

public class UserInformation {

    private static final String defaultStringValue = "";
    private static final long defaultLongValue = 0L;
    private static final boolean defaultBooleanValue = false;

    private static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static String getString(Context context, StringKey key) {
        return getPreferences(context).getString(key.name(), defaultStringValue);
    }

    @SuppressLint("ApplySharedPref")
    public static void putString(Context context, StringKey key, String value) {
        getPreferences(context).edit().putString(key.name(), value).commit();
    }

    public static long getLong(Context context, LongKey key) {
        return getPreferences(context).getLong(key.name(), defaultLongValue);
    }

    @SuppressLint("ApplySharedPref")
    public static void putLong(Context context, LongKey key, long value) {
        getPreferences(context).edit().putLong(key.name(), value).commit();
    }

    public static boolean getBoolean(Context context, BooleanKey key) {
        return getPreferences(context).getBoolean(key.name(), defaultBooleanValue);
    }

    @SuppressLint("ApplySharedPref")
    public static void putBoolean(Context context, BooleanKey key, boolean value) {
        getPreferences(context).edit().putBoolean(key.name(), value).commit();
    }

    public static boolean isTokenValid(Context context) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTimeInMillis() >= getLong(context, LongKey.TOKEN_EXPIRATION);
    }

    public enum StringKey {
        UID,
        NAME,
        TOKEN,
        SCOPE,
        GENDER,
        PHONE_NUMBER,
        BRANCH,
        YEAR,
        ROOM_NUMBER,
        FIREBASE_TOKEN,
        EMAIL
    }

    public enum LongKey {
        TOKEN_EXPIRATION
    }

    public enum BooleanKey {
        LOGOUT_FLAG,
        IS_LOGGED_IN
    }
}
