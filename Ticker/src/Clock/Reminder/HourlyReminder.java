/** * @author sharma.animesh@gmail.com */

package Clock.Reminder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class HourlyReminder extends BroadcastReceiver {
    /**
     *
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        System.out.println("Receiver");
    }
        
}

// suggestions via http://vikinghammer.com/2012/04/22/android-use-alarmmanager-instead-of-a-service-with-a-timertask/