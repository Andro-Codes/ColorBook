package pavishka.coloring.book.Utilities;

import android.app.NotificationManager;
import android.content.Context;


public class AlertAlarm {
    private static String TAG = "NotificationUtils";
    private Context mContext;

    public AlertAlarm(Context context) {
        this.mContext = context;
    }

    public static void clearNotifications(Context context) {
        ((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
    }



}
