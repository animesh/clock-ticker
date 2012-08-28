package Clock.Reminder;
/*
 * @author Ani
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TickerBoot extends BroadcastReceiver{   
    HourlyReminder hRemB = new HourlyReminder();
    @Override
    public void onReceive(Context context, Intent intent){   
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            hRemB.SetAlarm(context);
        }
    }
}

// http://stackoverflow.com/questions/1756296/android-writing-logs-to-text-file
// https://github.com/dive/ServiceExample/blob/master/AndroidManifest.xml
// http://www.vogella.com/articles/AndroidServices/article.html#scheduleservice_startauto