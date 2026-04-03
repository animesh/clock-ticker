package com.fuzzyLife.hourlyvibration;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.PowerManager;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import java.util.Calendar;

public class VibrationReceiver extends BroadcastReceiver {
    private static final String TAG = "VibrationReceiver";
    // Strong Morse code pattern "GOD" (multiplied by 3 for duration)
    public static final long[] PATTERN = {0, 150, 450, 450, 300, 150, 450, 450, 450, 150, 450, 300, 300, 600};
    // Max intensity amplitudes
    public static final int[] AMPLITUDES = {0, 255, 0, 255, 0, 255, 0, 255, 0, 255, 0, 255, 0, 255};

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "HourlyVibration:WakeLock");
        wl.acquire(10000); // 10s for pattern completion

        try {
            Log.d(TAG, "onReceive triggered");
            context.sendBroadcast(new Intent("SHAKE").setPackage(context.getPackageName()));

            Toast t = Toast.makeText(context, "Hourly Vibration Triggered", Toast.LENGTH_SHORT);
            t.setGravity(Gravity.CENTER, 0, 0);
            t.show();

            vibrate(context);
            showNotification(context);
            scheduleNext(context);
        } finally {
            if (wl.isHeld()) wl.release();
        }
    }

    private void vibrate(Context context) {
        Vibrator v;
        if (Build.VERSION.SDK_INT >= 31) {
            VibratorManager vm = (VibratorManager) context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            v = (vm != null) ? vm.getDefaultVibrator() : (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        } else {
            v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        }

        if (v != null && v.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 26) {
                VibrationEffect effect = VibrationEffect.createWaveform(PATTERN, AMPLITUDES, -1);
                if (Build.VERSION.SDK_INT >= 33) {
                    VibrationAttributes attr = new VibrationAttributes.Builder()
                            .setUsage(VibrationAttributes.USAGE_ALARM)
                            .build();
                    v.vibrate(effect, attr);
                } else {
                    AudioAttributes aa = new AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setUsage(AudioAttributes.USAGE_ALARM)
                            .build();
                    v.vibrate(effect, aa);
                }
            } else {
                v.vibrate(PATTERN, -1);
            }
        }
    }

    private void showNotification(Context context) {
        String channelId = "hourly_channel";
        NotificationCompat.Builder b = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Hourly Vibration")
                .setContentText("Hourly reminder triggered.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setAutoCancel(true);

        try {
            NotificationManagerCompat.from(context).notify(1, b.build());
        } catch (SecurityException ignored) {}
    }

    public static void scheduleNext(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, VibrationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        // Calculate time for the top of the next hour (e.g., if it is 10:15, schedule for 11:00)
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, 1);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        if (am != null) {
            if (Build.VERSION.SDK_INT >= 31 && !am.canScheduleExactAlarms()) return;

            AlarmManager.AlarmClockInfo info = new AlarmManager.AlarmClockInfo(c.getTimeInMillis(), pi);
            am.setAlarmClock(info, pi);
            Log.d(TAG, "Scheduled next alarm for: " + c.getTime());
        }
    }
}
