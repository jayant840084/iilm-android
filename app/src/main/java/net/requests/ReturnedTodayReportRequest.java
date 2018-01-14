/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;

import net.ApiClient;
import net.ApiInterface;
import net.models.OutPassModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by Jayant Singh on 14/1/18.
 */

public class ReturnedTodayReportRequest {

    private Call<List<OutPassModel>> call;

    public void execute(
            final Context context,
            final int offset,
            final int limit,
            final Callback callback) {
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        call = apiInterface.getStudentsReturnedToday(
                UserInformation.getString(context, UserInformation.StringKey.TOKEN),
                offset,
                limit
        );

        call.enqueue(new retrofit2.Callback<List<OutPassModel>>() {
            @Override
            public void onResponse(Call<List<OutPassModel>> call, Response<List<OutPassModel>> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<OutPassModel>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void cancel() {
        call.cancel();
    }

    public interface Callback {
        void onResponse(Response<List<OutPassModel>> response);

        void onFailure();
    }
}
