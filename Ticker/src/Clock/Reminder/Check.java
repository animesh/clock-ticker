package Clock.Reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.Calendar;


public class Check extends Activity {
    private Context context;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Intent alarmIntent = new Intent(context, HourlyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, Calendar.getInstance().getTimeInMillis(), pendingIntent);
        
    }
}
// http://stackoverflow.com/questions/5775973/how-can-give-vibrate-permission
// http://stackoverflow.com/questions/5369682/android-get-current-time-and-date