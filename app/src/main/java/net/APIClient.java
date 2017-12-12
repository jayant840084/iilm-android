package net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jayan on 16-11-2016.
 */

public class APIClient {

    private static Retrofit retrofit = null;

    private static OkHttpClient httpClient = null;

    public static void with(Context context) {
        if (httpClient == null) {
            int packageVersion = 0;
            try {
                PackageInfo packageInfo = context
                        .getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0);
                packageVersion = packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            final int finalPackageVersion = packageVersion;
            httpClient = new OkHttpClient().newBuilder().addInterceptor(chain -> {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("agent", "ANDROID_APP")
                        .addHeader("agent-version", String.valueOf(finalPackageVersion))
                        .build();
                return chain.proceed(request);
            }).build();
        } else {
            Log.e("ERROR", "HttpClient already initialized...");
        }
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(UrlGenerator.getUrl())
                    .client(httpClient)
                    .build();
        }
        return retrofit;
    }
}
