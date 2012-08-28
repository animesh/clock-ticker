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

public class HourlyReminder extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager hRemPM = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock hRemWL = hRemPM.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");

        hRemWL.acquire();
        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {0, 50, 100, 50, 100, 50, 150, 200, 100, 200, 100, 200, 150, 50, 100, 50, 100, 50, 300};
        v.vibrate(pattern, -1);
        hRemWL.release();

    }

    public void SetAlarm(Context context) {
        AlarmManager hRemMan = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent hRemI = new Intent(context, HourlyReminder.class);
        PendingIntent hRemPI = PendingIntent.getBroadcast(context, 0, hRemI, 0);
        hRemMan.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60 * 1, hRemPI); // Millisec * Second * Minute
    }

    public void CancelAlarm(Context context) {
        Intent hRemIC = new Intent(context, HourlyReminder.class);
        PendingIntent hRemPIC = PendingIntent.getBroadcast(context, 0, hRemIC, 0);
        AlarmManager hRemManCan = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        hRemManCan.cancel(hRemPIC);
    }
}

// http://stackoverflow.com/questions/4459058/alarm-manager-example
// http://code.google.com/p/clock-ticker/source/browse/Ticker/src/Clock/Reminder/Check.java?spec=svnb25282c6fd4dbe8383e5ec98354bfda0583115a8&r=b25282c6fd4dbe8383e5ec98354bfda0583115a8
