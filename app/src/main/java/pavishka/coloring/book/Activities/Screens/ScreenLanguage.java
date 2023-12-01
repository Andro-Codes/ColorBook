package pavishka.coloring.book.Activities.Screens;

import android.app.Activity;
import android.content.res.Configuration;
import android.util.Log;

import java.util.Locale;

import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;


public class ScreenLanguage {
    Activity myactivity;
    ScreenTempDataStore sharedPreference;

    private static void updateTTF(String str) {
        int hashCode = str.hashCode();
        if (hashCode == 3201) {
            str.equals("de");
        } else if (hashCode == 3241) {
            str.equals("en");
        } else if (hashCode == 3246) {
            str.equals("es");
        } else if (hashCode == 3276) {
            str.equals("fr");
        } else if (hashCode == 3371) {
            str.equals("it");
        } else if (hashCode == 3588) {
            str.equals("pt");
        } else if (hashCode == 3651 && str.equals("ru")) {
        }
    }

    protected void ale(Activity activity, String str) {
        Locale locale = new Locale(str);
        Configuration configuration = new Configuration(activity.getResources().getConfiguration());
        Locale.setDefault(locale);
        configuration.locale = locale;
        configuration.setLocale(locale);
        activity.getBaseContext().getResources().updateConfiguration(configuration, activity.getBaseContext().getResources().getDisplayMetrics());
        Log.d("LANGUAGE_CHECK", "Language: " + str);
    }

    public void loadLocale() {
        String locale = this.sharedPreference.getLocale(this.myactivity);
        ale(this.myactivity, locale);
        updateTTF(locale);
    }

    public void setUpLocale(Activity activity) {
        this.myactivity = activity;
        activity.getResources();
        this.sharedPreference = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        loadLocale();
    }
}
