/** * @author sharma.animesh@gmail.com */

package Clock.Reminder;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Calendar;


public class Check extends Activity {
    
    /** Called when the activity is first created. */
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

        alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

        buttonStart.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                setForNextHour();
                finish();
            }});
    }

    public void setForNextHour() {
        Calendar calendar = Calendar.getInstance();
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_HOUR * 1, pendingIntent);
        System.out.println("Next Millis = " + calendar.getTime());
    }
}
// http://stackoverflow.com/questions/11167025/repeat-alarms-on-every-monday-in-android-usig-alarmmanager-broadcastreceiver
