package net.requests;

import android.content.Context;
import android.preference.PreferenceManager;

import net.ApiClient;
import net.ApiInterface;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sherlock on 29/10/17.
 */

public class CheckUpdateRequest {


    public void execute(final Context context) {

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseBody> call = apiInterface.checkUpdate();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    try {
                        int version = Integer.parseInt(response.body().string());

                        PreferenceManager
                                .getDefaultSharedPreferences(context)
                                .edit()
                                .putInt("versionRequired", version)
                                .apply();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable throwable) {

            }
        });

    }
}
