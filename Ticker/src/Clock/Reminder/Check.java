package Clock.Reminder;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AnalogClock;
import android.widget.DigitalClock;

public class Check extends Activity
{
    /** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
 
		AnalogClock ac = (AnalogClock) findViewById(R.id.analogClock1);
		//what can i do with AnalogClock?
 
		DigitalClock dc = (DigitalClock) findViewById(R.id.digitalClock1);
		//what can i do with DigitalClock also? for display only
 
	}
 
}
