package net.requests;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import net.APIClient;
import net.APIInterface;
import net.models.OutPassModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.UserInformation;

/**
 * Created by sherlock on 9/7/17.
 */

public class GetSignedPassesRequest {

    private Call<List<OutPassModel>> call;

    public void execute(
            final Context context,
            final int offset,
            final int limit,
            final Callback callback
            ) {
        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        call = apiInterface.getOutPassesSigned(
                UserInformation.getString(context, UserInformation.StringKey.TOKEN),
                true,
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
                callback.onResponse(null);
            }
        });

    }

    public void cancel() {
        call.cancel();
    }

    public interface Callback {
        void onResponse(@Nullable Response<List<OutPassModel>> response);
    }
}
