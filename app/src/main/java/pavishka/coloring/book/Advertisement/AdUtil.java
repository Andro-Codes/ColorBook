package pavishka.coloring.book.Advertisement;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AdUtil {

    public static final String AdMob_Id = "AdMob_Id";
    public static final String AdMob_BannerAdId = "AdMob_BannerAdId";
    public static final String AdMob_InterstitialAdId = "AdMob_InterstitialAdId";
    public static final String AdMob_NativeAdId = "AdMob_NativeAdId";
    public static final String KEY = "data";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editorSharedPreferences;

    public AdUtil(Context activity) {
        sharedPreferences = activity.getSharedPreferences(KEY, Context.MODE_PRIVATE);
        editorSharedPreferences = sharedPreferences.edit();
    }

    public static AdUtil getutil(Context activity) {
        return new AdUtil(activity);
    }

    public String getString(String key) {

        Log.e("aaabbbccc",":"+sharedPreferences.getString(key, ""));
        if(sharedPreferences.contains(key)){
            return sharedPreferences.getString(key, "");
        }else{
            return "";
        }
    }

    public void setString(String key,String val) {
        editorSharedPreferences.putString(key,val);
        editorSharedPreferences.apply();
    }

    public boolean getBoolen(String key) {
        if(sharedPreferences.contains(key)){
            return sharedPreferences.getBoolean(key, false);
        }else{
            return false;
        }
    }

    public void setBoolen(String key,boolean val) {
        editorSharedPreferences.putBoolean(key,val);
        editorSharedPreferences.apply();
    }


}
