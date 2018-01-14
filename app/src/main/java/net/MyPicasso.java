/*
 * Copyright 2018,  Jayant Singh, All rights reserved.
 */

package net;

import android.content.Context;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import utils.UserInformation;

/**
 * Created by sherlock on 14/10/17.
 */

public class MyPicasso {

    public static Picasso with(final Context context) {
        OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(chain -> {
                    Request.Builder request = chain.request()
                            .newBuilder()
                            .addHeader("token",
                                    UserInformation.getString(
                                            context,
                                            UserInformation.StringKey.TOKEN));
                    return chain.proceed(request.build());
                }).build();

        return new Picasso
                .Builder(context)
                .downloader(new OkHttp3Downloader(client))
                .build();
    }
}
