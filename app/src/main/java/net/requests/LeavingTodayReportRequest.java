/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;

import net.ApiClient;
import net.ApiInterface;

import db.ReportLeavingToday;
import models.OutPassModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by jayant on 6/1/18.
 */

public class LeavingTodayReportRequest {

    private Call<List<ReportLeavingToday>> call;

    public void execute(
            final Context context,
            final int offset,
            final int limit,
            final Callback callback
    ) {
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        call = apiInterface.getStudentsLeavingToday(
                UserInformation.getString(context, UserInformation.StringKey.TOKEN),
                offset,
                limit
        );

        call.enqueue(new retrofit2.Callback<List<ReportLeavingToday>>() {
            @Override
            public void onResponse(Call<List<ReportLeavingToday>> call, Response<List<ReportLeavingToday>> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<ReportLeavingToday>> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void cancel() {
        call.cancel();
    }

    public interface Callback {
        void onResponse(Response<List<ReportLeavingToday>> response);

        void onFailure();
    }
}
