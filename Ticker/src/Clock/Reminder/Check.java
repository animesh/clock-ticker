package Clock.Reminder;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class Check extends Service {

    HourlyReminder hRem = new HourlyReminder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void onStart(Context context, Intent intent, int startId) {
        hRem.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

// @author sharma.animesh@gmail.com mashed up from following
// http://stackoverflow.com/questions/11733736/alarmmanager-never-calling-onrecieve-in-alarmreceiver-broadcastreceiver
// http://stackoverflow.com/questions/11167025/repeat-alarms-on-every-monday-in-android-usig-alarmmanager-broadcastreceiver
// http://android.konreu.com/developer-how-to/vibration-examples-for-android-phone-development/
// http://stackoverflow.com/questions/2784441/trying-to-start-a-service-on-boot-on-android/5439320#5439320
// http://developer.android.com/guide/topics/data/install-location.html
// http://stackoverflow.com/questions/7344897/autostart-android-service