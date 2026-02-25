package com.fuzzyLife.hourlyvibration;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Calendar;

public class VibrationReceiver extends BroadcastReceiver {
    public static final long[] PATTERN = {0, 50, 150, 150, 100, 50, 150, 150, 150, 50, 150, 100, 100, 200};

    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("SHAKE").setPackage(context.getPackageName()));

        Toast t = Toast.makeText(context, "Hourly Vibration Triggered", Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER, 0, 0);
        t.show();

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (v != null && v.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 26) v.vibrate(VibrationEffect.createWaveform(PATTERN, -1));
            else v.vibrate(PATTERN, -1);
        }

        try {
            NotificationManagerCompat.from(context).notify(1, new NotificationCompat.Builder(context, "hourly_channel")
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentTitle("Hourly Vibration").setContentText("Top of the hour!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT).setAutoCancel(true).build());
        } catch (SecurityException ignored) {}

        scheduleNext(context);
    }

    public static void scheduleNext(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent(context, VibrationReceiver.class),
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar c = Calendar.getInstance();
        c.set(Calendar.MINUTE, 0); c.set(Calendar.SECOND, 0); c.set(Calendar.MILLISECOND, 0);
        c.add(Calendar.HOUR_OF_DAY, 1);

        if (am != null) {
            if (Build.VERSION.SDK_INT >= 31 && !am.canScheduleExactAlarms()) return;
            am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
        }
    }
}
