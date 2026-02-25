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
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
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

        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel c = new NotificationChannel("hourly_channel", "Hourly", NotificationManager.IMPORTANCE_DEFAULT);
            getSystemService(NotificationManager.class).createNotificationChannel(c);
        }
        if (Build.VERSION.SDK_INT >= 33) rl.launch(Manifest.permission.POST_NOTIFICATIONS);

        findViewById(R.id.startButton).setOnClickListener(v -> {
            if (p.getBoolean("sched", false)) Toast.makeText(this, "Already started", Toast.LENGTH_SHORT).show();
            else {
                vibrate();
                shake();
                Toast t = Toast.makeText(this, "Hourly Vibration Triggered", Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER, 0, 0);
                t.show();
                VibrationReceiver.scheduleNext(this);
                p.edit().putBoolean("sched", true).apply();
            }
        });

        findViewById(R.id.stopButton).setOnClickListener(v -> {
            PendingIntent pi = PendingIntent.getBroadcast(this, 0, new Intent(this, VibrationReceiver.class), 
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
            if (am != null) am.cancel(pi);
            p.edit().putBoolean("sched", false).apply();
            Toast.makeText(this, "Stopped", Toast.LENGTH_SHORT).show();
        });
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
        Vibrator v = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (v != null && v.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= 26) v.vibrate(VibrationEffect.createWaveform(VibrationReceiver.PATTERN, -1));
            else v.vibrate(VibrationReceiver.PATTERN, -1);
        }
    }

    private void shake() {
        TranslateAnimation a = new TranslateAnimation(0, 20, 0, 0);
        a.setDuration(500);
        a.setInterpolator(new CycleInterpolator(7));
        findViewById(R.id.main_layout).startAnimation(a);
    }
}
