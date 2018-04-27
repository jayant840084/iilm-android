/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net.requests;

import android.content.Context;
import android.support.annotation.Nullable;

import net.ApiClient;
import net.ApiInterface;

import db.FacultySignedPasses;
import io.realm.RealmResults;
import models.OutPassModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by sherlock on 9/7/17.
 */

public class GetSignedPassesRequest {

    private Call<List<FacultySignedPasses>> call = null;

    public void execute(
            final Context context,
            final int offset,
            final int limit,
            final Callback callback
    ) {
        final ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        call = apiInterface.getOutPassesSigned(
                UserInformation.getString(context, UserInformation.StringKey.TOKEN),
                true,
                offset,
                limit
        );

        call.enqueue(new retrofit2.Callback<List<FacultySignedPasses>>() {
            @Override
            public void onResponse(Call<List<FacultySignedPasses>> call, Response<List<FacultySignedPasses>> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<FacultySignedPasses>> call, Throwable t) {
                callback.onResponse(null);
            }
        });

    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }

    public interface Callback {
        void onResponse(@Nullable Response<List<FacultySignedPasses>> response);
    }
}
