/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import net.ApiClient;
import net.ApiInterface;
import net.models.GuardLogModel;
import net.models.LogReturnModel;

import constants.OutPassAttributes;
import constants.OutPassType;
import in.ac.iilm.iilm.R;
import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by sherlock on 27/8/17.
 */

public class LogReturnRequest {

    public void execute(Context context, String id, final LogReturnRequest.Callback callback) {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        final String token = UserInformation.getString(context, UserInformation.StringKey.TOKEN);

        Call<GuardLogModel> call = apiInterface.putLogReturn(token, id);

        call.enqueue(new retrofit2.Callback<GuardLogModel>() {
            @Override
            public void onResponse(Call<GuardLogModel> call, Response<GuardLogModel> response) {
                if (response.code() == 203) {
                    callback.status(null, response.body());
                } else if (response.code() == 600) {
                    callback.status("Pass has not been used to leave the campus", null);
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
