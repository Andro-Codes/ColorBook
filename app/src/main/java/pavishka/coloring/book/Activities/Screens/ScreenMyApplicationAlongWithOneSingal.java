package pavishka.coloring.book.Activities.Screens;

import android.content.Context;

import androidx.multidex.MultiDexApplication;
import com.onesignal.OneSignal;


public class ScreenMyApplicationAlongWithOneSingal extends MultiDexApplication {
    private static Context context;
    private static final String ONESIGNAL_APP_ID = "e2de4f29-4983-4320-a6ff-a20a26340755";

    public static Context getAppContext() {
        return context;
    }



    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

    }
}
