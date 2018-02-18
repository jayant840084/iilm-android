/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;

import net.ApiClient;
import net.ApiInterface;

import db.ReportReturnedToday;
import models.OutPassModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by Jayant Singh on 14/1/18.
 */

public class ReturnedTodayReportRequest {

    private Call<List<ReportReturnedToday>> call;

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

        call.enqueue(new retrofit2.Callback<List<ReportReturnedToday>>() {
            @Override
            public void onResponse(Call<List<ReportReturnedToday>> call, Response<List<ReportReturnedToday>> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<ReportReturnedToday>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void cancel() {
        call.cancel();
    }

    public interface Callback {
        void onResponse(Response<List<ReportReturnedToday>> response);

        void onFailure();
    }
}
