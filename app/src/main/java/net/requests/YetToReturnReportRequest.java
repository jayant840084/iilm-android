/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;

import net.ApiClient;
import net.ApiInterface;

import db.ReportYetToReturn;
import models.OutPassModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by Jayant Singh on 14/1/18.
 */

public class YetToReturnReportRequest {

    private Call<List<ReportYetToReturn>> call = null;

    public void execute(
            final Context context,
            final int offset,
            final int limit,
            final YetToReturnReportRequest.Callback callback) {
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        call = apiInterface.getStudentsYetToReturn(
                UserInformation.getString(context, UserInformation.StringKey.TOKEN),
                offset,
                limit
        );

        call.enqueue(new retrofit2.Callback<List<ReportYetToReturn>>() {
            @Override
            public void onResponse(Call<List<ReportYetToReturn>> call, Response<List<ReportYetToReturn>> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<ReportYetToReturn>> call, Throwable t) {
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
        void onResponse(Response<List<ReportYetToReturn>> response);

        void onFailure();
    }
}
