/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;
import android.support.annotation.Nullable;

import net.ApiClient;
import net.ApiInterface;
import models.GuardLogModel;

import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by sherlock on 9/7/17.
 */

public class LogLeaveRequest {

    public void execute(Context context, String id, final LogLeaveRequest.Callback callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final String token = UserInformation.getString(context, UserInformation.StringKey.TOKEN);

        Call<GuardLogModel> call = apiInterface.putLogLeave(token, id);

        call.enqueue(new retrofit2.Callback<GuardLogModel>() {
            @Override
            public void onResponse(Call<GuardLogModel> call, Response<GuardLogModel> response) {
                if (response.code() == 203) {
                    callback.status(null, response.body());
                } else if (response.code() == 600) {
                    callback.status("Pass has already been used", null);
                }
            }

            @Override
            public void onFailure(Call<GuardLogModel> call, Throwable throwable) {
                callback.status(null, null);
            }
        });
    }

    public interface Callback {
        void status(@Nullable String errMsg, @Nullable GuardLogModel guardLogModel);
    }
}
