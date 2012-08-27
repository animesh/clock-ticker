package Clock.Reminder;
/**
 * * @author sharma.animesh@gmail.com
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.widget.Toast;

public class HourlyReminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        
        PowerManager hRemPM = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock hRemWL = hRemPM.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");

        hRemWL.acquire();

        // Put here YOUR code.
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(300);
        Toast.makeText(context.getApplicationContext(), "Sample Text", Toast.LENGTH_LONG).show();

        hRemWL.release();
        
    }

    public void SetAlarm(Context context) {
        AlarmManager hRemMan = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent hRemP = new Intent(context, HourlyReminder.class);
        PendingIntent hRemPI = PendingIntent.getBroadcast(context, 0, hRemP, 0);
        hRemMan.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 10, hRemPI); // Millisec * Second * Minute
    }

    public void CancelAlarm(Context context) {
        Intent hRemIC = new Intent(context, HourlyReminder.class);
        PendingIntent hRemPIC = PendingIntent.getBroadcast(context, 0, hRemIC, 0);
        AlarmManager hRemManCan = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        hRemManCan.cancel(hRemPIC);
    }
}

// http://stackoverflow.com/questions/4459058/alarm-manager-example