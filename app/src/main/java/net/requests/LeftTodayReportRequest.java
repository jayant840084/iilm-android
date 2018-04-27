/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;

import net.ApiClient;
import net.ApiInterface;

import db.ReportLeftToday;
import models.OutPassModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by Jayant Singh on 14/1/18.
 */

public class LeftTodayReportRequest {

    private Call<List<ReportLeftToday>> call = null;

    public void execute(
            final Context context,
            final int offset,
            final int limit,
            final LeftTodayReportRequest.Callback callback) {
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        call = apiInterface.getStudentsLeftToday(
                UserInformation.getString(context, UserInformation.StringKey.TOKEN),
                offset,
                limit
        );

        call.enqueue(new retrofit2.Callback<List<ReportLeftToday>>() {
            @Override
            public void onResponse(Call<List<ReportLeftToday>> call, Response<List<ReportLeftToday>> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<ReportLeftToday>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    public interface Callback {
        void onResponse(Response<List<ReportLeftToday>> response);

        void onFailure();
    }
}
