package Clock.Reminder;
/**
 * * @author sharma.animesh@gmail.com
 */
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class Check extends Service{
    
    HourlyReminder hRem = new HourlyReminder();
    
    @Override
    public void onCreate(){
        super.onCreate();       
    }

    public void onStart(Context context,Intent intent, int startId){
        hRem.SetAlarm(context);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
// http://stackoverflow.com/questions/11733736/alarmmanager-never-calling-onrecieve-in-alarmreceiver-broadcastreceiver
// http://stackoverflow.com/questions/11167025/repeat-alarms-on-every-monday-in-android-usig-alarmmanager-broadcastreceiver
// http://android.konreu.com/developer-how-to/vibration-examples-for-android-phone-development/