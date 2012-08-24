package Clock.Reminder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import java.util.Calendar;

public class Check extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DigitalClock dc = (DigitalClock) findViewById(R.id.digitalClock1);
        //if(dc.playSoundEffect(MODE_PRIVATE))
        //what can i do with DigitalClock also? for display only
        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        if (minutes%2 == 0) {
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(300);
            setContentView(R.layout.main);
        }
    }
}
// http://stackoverflow.com/questions/5775973/how-can-give-vibrate-permission
// http://stackoverflow.com/questions/5369682/android-get-current-time-and-date