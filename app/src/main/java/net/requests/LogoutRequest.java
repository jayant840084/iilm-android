package net.requests;

import android.content.Context;
import android.util.Log;

import net.APIClient;
import net.APIInterface;
import net.MyPicasso;
import net.UrlGenerator;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by sherlock on 5/7/17.
 */

public class LogoutRequest {

    public void execute(final Context context, final Callback callback) {

        String firebaseToken = UserInformation.getString(context, UserInformation.StringKey.FIREBASE_TOKEN);

        /*
        If FIREBASE_TOKEN exists in user information it must be present on server hence make a request to remove
        the token from the server, if no token is present in user information simply reset user information
         */
        if (firebaseToken != null) {
            APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<ResponseBody> call = apiInterface.putLogout(
                    UserInformation.getString(context, UserInformation.StringKey.TOKEN),
                    firebaseToken
            );

            call.enqueue(new retrofit2.Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.code() == 203) {
                        postLogout(context);
                        callback.status(true);
                    } else {
                        try {
                            Log.d("LOGOUT_FAILED", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        callback.status(false);
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    callback.status(false);
                }
            });
        } else {
            postLogout(context);
            callback.status(true);
        }
    }

    private void postLogout(Context context) {
        UserInformation.putString(context, UserInformation.StringKey.UID, "");
        UserInformation.putString(context, UserInformation.StringKey.NAME, "");
        UserInformation.putString(context, UserInformation.StringKey.BRANCH, "");
        UserInformation.putString(context, UserInformation.StringKey.GENDER, "");
        UserInformation.putString(context, UserInformation.StringKey.PHONE_NUMBER, "");
        UserInformation.putString(context, UserInformation.StringKey.YEAR, "");
        UserInformation.putString(context, UserInformation.StringKey.ROOM_NUMBER, "");
        UserInformation.putString(context, UserInformation.StringKey.SCOPE, "");
        UserInformation.putString(context, UserInformation.StringKey.TOKEN, "");
        MyPicasso.with(context).invalidate(UrlGenerator.getUrl("profile-img"));
    }

    public interface Callback {
        void status(boolean success);
    }
}
