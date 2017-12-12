package firebase;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import in.ac.iilm.iilm.R;
import in.ac.iilm.iilm.SplashActivity;
import utils.ActivityTracker;
import utils.UserInformation;

/**
 * Created by sherlock on 14/2/17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static int notificationID = 0;

    private static final Uri notificationSound = RingtoneManager
            .getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {

        final String uid = UserInformation.getString(this, UserInformation.StringKey.UID);

        if (uid != null && remoteMessage.getNotification() == null) {
            switch (remoteMessage.getData().get("type")) {
                case "notification":
                    showNotification(remoteMessage, uid);
                    break;
                case "logout":
                    UserInformation.putBoolean(this, UserInformation.BooleanKey.LOGOUT_FLAG, true);
                    break;
            }
        }
    }

    private void showNotification(final RemoteMessage remoteMessage, final String uid) {
        if (remoteMessage.getData().get("uid").equals(uid) && !ActivityTracker.isActivityRunning()) {

            final Intent intent = new Intent(this, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            final PendingIntent pendingIntent = TaskStackBuilder
                    .create(this)
                    .addParentStack(SplashActivity.class)
                    .addNextIntent(intent)
                    .getPendingIntent(notificationID, PendingIntent.FLAG_UPDATE_CURRENT);

            final NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat
                    .BigTextStyle();

            bigTextStyle.bigText(remoteMessage.getData().get("description"));

            final Notification notification = new NotificationCompat.Builder(this)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(remoteMessage.getData().get("description"))
                    .setStyle(bigTextStyle)
                    .setSound(notificationSound)
                    .build();

            final NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            manager.notify(notificationID++, notification);
        }
    }
}
