/**
 * * @author sharma.animesh@gmail.com
 */
package Clock.Reminder;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.widget.Button;
import java.util.Calendar;

public class Check extends Activity {

    /**
     * Called when the activity is first created.
     */
    int id = 12;
    Intent nextIntent;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Button buttonStart = (Button) findViewById(R.id.start);

        nextIntent = new Intent(getBaseContext(), HourlyReminder.class);
        nextIntent.putExtra("id", id);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), id, nextIntent, 0);

        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        //buttonStart.setOnClickListener(new Button.OnClickListener(){
        //  public void onClick(View arg0) {
        // TODO Auto-generated method stub
        setForNextHour();
        finish();
        //}});
    }

    public void setForNextHour() {
        Calendar calendar = Calendar.getInstance();
        vibrateOnce();
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 3600000, pendingIntent);
        System.out.println("Next Millis = " + calendar.getTime());
    }

    public void vibrateOnce() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

// This example will cause the phone to vibrate "SOS" in Morse Code
// In Morse Code, "s" = "dot-dot-dot", "o" = "dash-dash-dash"
// There are pauses to separate dots/dashes, letters, and words
// The following numbers represent millisecond lengths
        int dot = 200;      // Length of a Morse Code "dot" in milliseconds
        int dash = 500;     // Length of a Morse Code "dash" in milliseconds
        int short_gap = 200;    // Length of Gap Between dots/dashes
        int medium_gap = 500;   // Length of Gap Between Letters
        int long_gap = 1000;    // Length of Gap Between Words
        long[] pattern = {
            0, // Start immediately
            dot, short_gap, dot, short_gap, dot, // s
            medium_gap,
            dash, short_gap, dash, short_gap, dash, // o
            medium_gap,
            dot, short_gap, dot, short_gap, dot, // s
            long_gap
        };

// Only perform this pattern one time (-1 means "do not repeat")
        v.vibrate(pattern, -1);
    }
}
// http://stackoverflow.com/questions/11733736/alarmmanager-never-calling-onrecieve-in-alarmreceiver-broadcastreceiver
// http://stackoverflow.com/questions/11167025/repeat-alarms-on-every-monday-in-android-usig-alarmmanager-broadcastreceiver
// http://android.konreu.com/developer-how-to/vibration-examples-for-android-phone-development/