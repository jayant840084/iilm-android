/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;
import android.support.annotation.Nullable;

import net.ApiClient;
import net.ApiInterface;

import java.io.IOException;

import models.UserModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Jayant Singh on 18/2/18.
 */

public class ResetPasswordRequest {

    public void execute(
            Context context,
            final String uid,
            final ResetPasswordRequest.Callback callback
    ) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<UserModel> call = apiInterface.putResetPassword(uid);

        call.enqueue(new retrofit2.Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                callback.response(null, response);
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                callback.response("Something went wrong, please try again.", null);
            }
        });
    }

    public interface Callback {
        void response(@Nullable String errMsg, @Nullable Response<UserModel> response);
    }
}
