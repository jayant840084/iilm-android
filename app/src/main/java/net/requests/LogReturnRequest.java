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
import net.models.LogReturnModel;

import constants.OutPassAttributes;
import constants.OutPassType;
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

        Call<LogReturnModel> call = apiInterface.putLogReturn(token, id);

        call.enqueue(new retrofit2.Callback<LogReturnModel>() {
            @Override
            public void onResponse(Call<LogReturnModel> call, Response<LogReturnModel> response) {
                if (response.code() == 203) {

                    Bundle bundle = new Bundle();
                    bundle.putString(OutPassAttributes.NAME, response.body().getName());
                    bundle.putString(OutPassAttributes.UID, response.body().getUid());
                    bundle.putString(OutPassAttributes.TIME_STAMP_LEAVE, response.body().getTimeLeave());
                    bundle.putString(OutPassAttributes.TIME_STAMP_RETURN, response.body().getTimeReturn());
                    bundle.putString(OutPassAttributes.ADDRESS, response.body().getVisitingAddress());
                    bundle.putString(OutPassAttributes.PHONE_NUMBER, response.body().getPhoneNumber());
                    bundle.putString(OutPassAttributes.REASON, response.body().getReasonVisit());
                    bundle.putString(OutPassAttributes.OUT_PASS_TYPE, response.body().getOutPassType());
                    bundle.putBoolean(OutPassAttributes.ALLOWED, response.body().isAllowed());
                    bundle.putString(OutPassAttributes.WARDEN_SIGNED, response.body().getWardenSigned().toString());
                    if (response.body().getOutPassType().equals(OutPassType.NIGHT)) {
                        bundle.putString(OutPassAttributes.DIRECTOR_SIGNED, response.body().getDirectorSigned().toString());
                        bundle.putString(OutPassAttributes.HOD_SIGNED, response.body().getHodSigned().toString());
                    }
                    callback.status(true, bundle);
                } else if (response.code() == 600) {
                    Toast.makeText(context, "Your never left the campus", Toast.LENGTH_LONG).show();
                    Bundle bundle = new Bundle();
                    bundle.putBoolean(OutPassAttributes.ALLOWED, false);
                    callback.status(true, bundle);
                }
            }

            @Override
            public void onFailure(Call<LogReturnModel> call, Throwable throwable) {
                callback.status(false, null);
            }
        });
    }

    public interface Callback {
        void status(boolean success, @Nullable Bundle passData);
    }
}
