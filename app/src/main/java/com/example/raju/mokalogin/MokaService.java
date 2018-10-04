package com.example.raju.mokalogin;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;


/**
 * @see <a href="https://developer.android.com/guide/components/services">Android Service Reference</a>
 */
public class MokaService extends Service {

    private static int FOREGROUND_ID = 1338;

    public MokaService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service ", "Service started by alarm receiver");
        Toast.makeText(getBaseContext(), "Service Created", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("Service ", "Service On Start Command");
        startForeground(FOREGROUND_ID, buildForegroundNotification());
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return new MokaServiceBinder();
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getBaseContext(), "Service destroyed", Toast.LENGTH_LONG).show();
    }

    public void callMethodOne() {
        Toast.makeText(getBaseContext(), "Service method called", Toast.LENGTH_LONG).show();
    }

    public class MokaServiceBinder extends Binder {

        MokaService getService() {
            return MokaService.this;
        }
    }


    private Notification buildForegroundNotification() {
        Intent notificationIntent = new Intent(this, LoginActivity.class);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent, 0);


        Notification.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = createNotificationChannel();
            builder = new Notification.Builder(this, channelId);
        } else {
            // If earlier version channel ID is not used
            // https://developer.android.com/reference/android/support/v4/app/NotificationCompat.Builder.html#NotificationCompat.Builder(android.content.Context)
            builder = new Notification.Builder(this);
        }

        Notification notification =
                builder
                        .setContentTitle(getText(R.string.app_name))
                        .setContentText(getText(R.string.app_name))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        // .setContentIntent(pendingIntent)
                        .setTicker(getText(R.string.action_sign_in))
                        .build();

        return notification;
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        String channelId = "my_service";
        String channelName = "My Background Service";
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.createNotificationChannel(notificationChannel);
        return channelId;
    }

}