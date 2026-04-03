package com.fuzzyLife.hourlyvibration;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.util.Log;
import android.view.Gravity;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private SharedPreferences p;
    private final ActivityResultLauncher<String> rl = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isG -> {});
    private final BroadcastReceiver sr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent i) { shake(); }
    };

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);
        p = getSharedPreferences("Prefs", 0);

        createNotificationChannel();
        
        if (Build.VERSION.SDK_INT >= 33) rl.launch(Manifest.permission.POST_NOTIFICATIONS);

        findViewById(R.id.startButton).setOnClickListener(v -> {
            Log.d(TAG, "Start button clicked");
            if (p.getBoolean("sched", false)) {
                Toast.makeText(this, "Already started", Toast.LENGTH_SHORT).show();
            } else {
                vibrate();
                shake();
                Toast t = Toast.makeText(this, "Hourly Vibration Activated", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                VibrationReceiver.scheduleNext(this);
                p.edit().putBoolean("sched", true).apply();
            }
        });

        findViewById(R.id.stopButton).setOnClickListener(v -> {
            Log.d(TAG, "Stop button clicked");
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(this, VibrationReceiver.class), 
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (am != null) am.cancel(pi);
            p.edit().putBoolean("sched", false).apply();
            Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel c = new NotificationChannel("hourly_channel", "Hourly Reminder", NotificationManager.IMPORTANCE_HIGH);
            c.setDescription("Hourly vibration notifications");
            c.enableVibration(true);
            c.setVibrationPattern(VibrationReceiver.PATTERN);
            
            NotificationManager nm = getSystemService(NotificationManager.class);
            if (nm != null) nm.createNotificationChannel(c);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 33) {
            registerReceiver(sr, new IntentFilter("SHAKE"), Context.RECEIVER_NOT_EXPORTED);
        } else {
            registerReceiver(sr, new IntentFilter("SHAKE"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(sr);
    }

    private void vibrate() {
        Vibrator v;
        if (Build.VERSION.SDK_INT >= 31) {
            VibratorManager vm = (VibratorManager) getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            if (vm != null) v = vm.getDefaultVibrator();
            else v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        } else {
            v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        }

        if (v != null && v.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 26) {
                VibrationEffect effect = VibrationEffect.createWaveform(VibrationReceiver.PATTERN, VibrationReceiver.AMPLITUDES, -1);
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
                v.vibrate(VibrationReceiver.PATTERN, -1);
            }
        }
    }

    private void shake() {
        TranslateAnimation a = new TranslateAnimation(0, 20, 0, 0);
        a.setDuration(500);
        a.setInterpolator(new CycleInterpolator(7));
        findViewById(R.id.main_layout).startAnimation(a);
    }
}
