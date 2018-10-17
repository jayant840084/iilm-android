/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import net.ApiClient;
import net.ApiInterface;
import net.requests.LogoutRequest;

import java.util.Calendar;

import in.ac.iilm.iilm.SplashActivity;
import models.LoginModel;
import models.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        TOKEN_EXPIRATION,
        LAST_PASS_REQUEST_TIME
    }

    public enum BooleanKey {
        LOGOUT_FLAG,
        IS_LOGGED_IN
    }

    public static void getFromServer(Context context, Callback callback) {
        getFromServer(context, getString(context, StringKey.TOKEN), callback);
    }

    public static void getFromServer(Context context, String token, Callback callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<UserModel> call = apiInterface.getUser(token);

        call.enqueue(new retrofit2.Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if (response.code() == 200) {
                    saveUserInfo(context, token, response.body());
                    callback.response(true);
                } else {
                    new LogoutRequest().execute(context, success -> {
                        context.startActivity(new Intent(context, SplashActivity.class));
                        ((Activity) context).finish();
                    });
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                callback.response(false);
            }
        });
    }

    private static void saveUserInfo(Context context, String token, UserModel response) {
        SharedPreferences.Editor editor = UserInformation.getPreferences(context).edit();

        editor.putString(StringKey.UID.name(), response.getUid());
        editor.putString(StringKey.NAME.name(), response.getName());
        editor.putString(StringKey.BRANCH.name(), response.getBranch());
        editor.putString(StringKey.GENDER.name(), response.getGender());
        editor.putString(StringKey.PHONE_NUMBER.name(), response.getPhoneNumber());
        editor.putString(StringKey.YEAR.name(), response.getYear());
        editor.putString(StringKey.ROOM_NUMBER.name(), response.getRoomNumber());
        editor.putString(StringKey.SCOPE.name(), response.getScope());
        editor.putString(StringKey.EMAIL.name(), response.getEmail());
        editor.putString(StringKey.TOKEN.name(), token);

        editor.commit();
    }

    public interface Callback {
        void response(boolean success);
    }
}
