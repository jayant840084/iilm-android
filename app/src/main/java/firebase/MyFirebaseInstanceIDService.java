package firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import net.requests.AddFirebaseTokenRequest;

import utils.UserInformation;

/**
 * Created by sherlock on 14/2/17.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();
    private static final String TOKEN = "TOKEN";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String firebaseToken) {
        String token = UserInformation.getString(this, UserInformation.StringKey.TOKEN);

        if (token != null) {
            // make a post request and save the firebase token on server
            new AddFirebaseTokenRequest().execute(this);
        }
    }
}
