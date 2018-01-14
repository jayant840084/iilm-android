/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;

import com.google.firebase.iid.FirebaseInstanceId;

import net.ApiClient;
import net.ApiInterface;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by sherlock on 5/7/17.
 */

public class AddFirebaseTokenRequest {

    public void execute(final Context context) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        final String token = UserInformation.getString(context, UserInformation.StringKey.TOKEN);
        Call<ResponseBody> call = apiInterface.putAddFirebaseToken(token, firebaseToken);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 201) {
                    UserInformation.putString(context, UserInformation.StringKey.FIREBASE_TOKEN, firebaseToken);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void execute(final Context context, final LogoutRequest.Callback callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final String firebaseToken = FirebaseInstanceId.getInstance().getToken();
        final String token = UserInformation.getString(context, UserInformation.StringKey.TOKEN);
        Call<ResponseBody> call = apiInterface.putAddFirebaseToken(token, firebaseToken);

        call.enqueue(new retrofit2.Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 203) {
                    UserInformation.putString(context, UserInformation.StringKey.FIREBASE_TOKEN, firebaseToken);
                    callback.status(true);
                } else {
                    callback.status(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.status(false);
            }
        });
    }

    public interface Callback {
        void status(boolean success);
    }
}
