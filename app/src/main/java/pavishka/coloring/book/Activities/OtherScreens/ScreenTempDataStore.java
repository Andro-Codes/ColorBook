package pavishka.coloring.book.Activities.OtherScreens;

import android.content.Context;
import android.content.SharedPreferences;

import pavishka.coloring.book.Activities.Screens.ScreenMyApplicationAlongWithOneSingal;


public class ScreenTempDataStore {
    public static final String PREFS_KEY_AL = "pref_key";
    public static final String PREFS_KEY_IMAGE = "pref_key_image";
    public static final String PREFS_KEY_IMAGE_LINK = "pref_key_image_link";
    public static final String PREFS_KEY_IMAGE_NAME = "pref_key_image_name";
    public static final String PREFS_KEY_ISSHOWNEWAPP = "pref_key_isshownewapp";
    public static final String PREFS_KEY_NS = "pref_key_nevershow";
    public static final String PREFS_NAME_AL = "pref_name";
    public static final String PREFS_NAME_IMAGE = "pref_name_image";
    public static final String PREFS_NAME_IMAGE_LINK = "pref_name_image_link";
    public static final String PREFS_NAME_IMAGE_NAME = "pref_name_image_name";
    public static final String PREFS_NAME_ISSHOWNEWAPP = "pref_name_isshownewapp";
    public static final String PREFS_NAME_NS = "pref_name_nevershow";
    public static final String PREF_KEY_FIRST_TIME = "first_time_key";
    public static final String PREF_KEY_MUSIC = "key_music";
    public static final String PREF_KEY_PURCHASE = "BUY";
    public static final String PREF_KEY_SOUND = "key_sound";
    public static final String PREF_KEY_STORAGE_PERMISSION_NEVER = "key_storage_permission_never";
    public static final String PREF_NAME_FIRST_TIME = "first_time_name";
    public static final String PREF_NAME_MUSIC = "name_music";
    public static final String PREF_NAME_PURCHASE = "SCORE";
    public static final String PREF_NAME_SOUND = "name_sound";
    public static final String PREF_NAME_STORAGE_PERMISSION_NEVER = "name_storage_permission_never";
    public String PREFS_KEY;
    public String PREFS_NAME;
    private SharedPreferences sharedPrefisShow;

    public ScreenTempDataStore(String str, String str2) {
        this.PREFS_NAME = str;
        this.PREFS_KEY = str2;
    }

    public static int getBuyChoise(Context context) {
        return context.getSharedPreferences(PREF_NAME_PURCHASE, 0).getInt(PREF_KEY_PURCHASE, 0);
    }

    public static void saveIndexLang(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences("pref_name_lang", 0).edit();
        edit.putInt("pref_key_lang", i);
        edit.apply();
    }


    public boolean getDialogNoShow() {
        SharedPreferences sharedPreferences = ScreenMyApplicationAlongWithOneSingal.getAppContext().getSharedPreferences(PREFS_NAME_ISSHOWNEWAPP, 0);
        this.sharedPrefisShow = sharedPreferences;
        return sharedPreferences.getBoolean(PREFS_KEY_ISSHOWNEWAPP, false);
    }


    public String getImageName(Context context) {
        return context.getSharedPreferences(PREFS_NAME_IMAGE_NAME, 0).getString(PREFS_KEY_IMAGE_NAME, "");
    }

    public int getIndexLang(Context context) {
        return context.getSharedPreferences("pref_name_lang", 0).getInt("pref_key_lang", 0);
    }

    public String getLocale(Context context) {
        return context.getSharedPreferences("CommonPrefs", 0).getString("Language", "en");
    }

    public boolean getSettingFirstTime(Context context) {
        return context.getSharedPreferences(PREF_NAME_FIRST_TIME, 0).getBoolean(PREF_KEY_FIRST_TIME, true);
    }

    public boolean getSettingMusic(Context context) {
        return context.getSharedPreferences(PREF_NAME_MUSIC, 0).getBoolean(PREF_KEY_MUSIC, true);
    }

    public boolean getSettingSound(Context context) {
        return context.getSharedPreferences(PREF_NAME_SOUND, 0).getBoolean(PREF_KEY_SOUND, true);
    }

    public boolean getStoragePermissionNever(Context context) {
        return context.getSharedPreferences(PREF_NAME_STORAGE_PERMISSION_NEVER, 0).getBoolean(PREF_KEY_STORAGE_PERMISSION_NEVER, false);
    }

    public int getValue(Context context) {
        return context.getSharedPreferences(this.PREFS_NAME, 0).getInt(this.PREFS_KEY, 0);
    }


    public void save(Context context, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(this.PREFS_NAME, 0).edit();
        edit.putInt(this.PREFS_KEY, i);
        edit.apply();
    }


    public void saveLocale(Context context, String str) {
        SharedPreferences.Editor edit = context.getSharedPreferences("CommonPrefs", 0).edit();
        edit.putString("Language", str);
        edit.apply();
    }

    public void saveSettingFirstTime(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME_FIRST_TIME, 0).edit();
        edit.putBoolean(PREF_KEY_FIRST_TIME, z);
        edit.apply();
    }

    public void saveSettingMusic(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME_MUSIC, 0).edit();
        edit.putBoolean(PREF_KEY_MUSIC, z);
        edit.apply();
    }

    public void saveSettingSound(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME_SOUND, 0).edit();
        edit.putBoolean(PREF_KEY_SOUND, z);
        edit.apply();
    }

    public void saveStoragePermissionNever(Context context, boolean z) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREF_NAME_STORAGE_PERMISSION_NEVER, 0).edit();
        edit.putBoolean(PREF_KEY_STORAGE_PERMISSION_NEVER, z);
        edit.apply();
    }

}
