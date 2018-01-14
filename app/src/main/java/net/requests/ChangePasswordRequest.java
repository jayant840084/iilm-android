/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;
import android.support.annotation.Nullable;

import net.ApiClient;
import net.ApiInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by jayant on 6/11/17.
 */

public class ChangePasswordRequest {

    public void execute(final Context context,
                        final String oldPassword,
                        final String newPassword,
                        final ChangePasswordRequest.callback callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final String token = UserInformation.getString(context, UserInformation.StringKey.TOKEN);
        Call<ResponseBody> call = apiInterface.putChangePassword(
                token,
                oldPassword,
                newPassword
        );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 203) {
                    callback.onResponse(true, null);
                } else {
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (jsonObject != null) {
                        try {
                            callback.onResponse(false, jsonObject.getString("error"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        callback.onResponse(false, "Something went wrong, please try again");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                callback.onResponse(false, "Something went wrong, please try again");
            }
        });

    }

    public interface callback {
        void onResponse(boolean isSuccess, @Nullable String err);
    }
}
