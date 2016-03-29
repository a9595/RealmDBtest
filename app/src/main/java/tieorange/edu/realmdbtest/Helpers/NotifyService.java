package tieorange.edu.realmdbtest.Helpers;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;

import tieorange.edu.realmdbtest.Activities.ChartsActivity;
import tieorange.edu.realmdbtest.R;

/**
 * Created by tieorange on 29/03/16.
 */
public class NotifyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this.getApplicationContext(), ChartsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this
                , ((int) System.currentTimeMillis()) // unique ID
                , intent
                , 0);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Title")
                .setContentText("Text")
                .setSmallIcon(R.drawable.ic_bookmark)
                .setContentIntent(pendingIntent)
                .setSound(sound)
                .addAction(R.drawable.ic_app_icon_clock, "ActionText", pendingIntent)
                .build();

        notificationManager.notify(1, notification);

    }
}
