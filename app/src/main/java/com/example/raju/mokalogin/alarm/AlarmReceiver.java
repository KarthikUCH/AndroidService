package com.example.raju.mokalogin.alarm;

        import android.app.AlarmManager;
        import android.app.PendingIntent;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.Build;

        import com.example.raju.mokalogin.MokaService;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int REQUEST_CODE = 1;

    private static final int TIME_INTERVAL = 5 * 1000; // 10 SECONDS

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent serviceIntent = new Intent(context, MokaService.class);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            context.startService(serviceIntent);
        }else {
            context.startForegroundService(serviceIntent);
        }

    }

    public void setAlarm(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, REQUEST_CODE, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TIME_INTERVAL, pendingIntent);
        }
    }
}
