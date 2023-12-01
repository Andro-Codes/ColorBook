package pavishka.coloring.book.Advertisement;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import pavishka.coloring.book.R;

import org.json.JSONException;
import org.json.JSONObject;



public class DataCapture {
    private static DataCapture singleton;


    private DataCapture() {
    }

    public static DataCapture getInstance() {
        if (singleton == null) {
            singleton = new DataCapture();
        }
        return singleton;
    }

    public void initAds(Activity context, AdsLoaded adsLoaded) {
        checkForUpdate(context, adsLoaded);
    }

    public void checkForUpdate(Activity context, AdsLoaded adsLoaded) {
        if (isConnected(context)) {
            getUpdateFromServer(context, adsLoaded);
        } else {
            adsLoaded.fail();
        }
    }

    public boolean isConnected(Context context) {

        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
            return false;
        }
        return true;
    }

    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private void getUpdateFromServer(final Activity application, AdsLoaded adsLoaded) {



        FirebaseApp.initializeApp(application);
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        mFirebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_defaults);

        fetchWelcome(application, adsLoaded);

    }

    private void fetchWelcome(Activity context, AdsLoaded adsLoaded) {

        mFirebaseRemoteConfig.fetchAndActivate()
                .addOnCompleteListener(new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            String s = mFirebaseRemoteConfig.getString(AdUtil.KEY);
                            AdUtil adUtil = AdUtil.getutil(context.getBaseContext());
                            try {
                                JSONObject jsonObject = new JSONObject(s);
                                adUtil.setString(AdUtil.AdMob_Id, jsonObject.getString(AdUtil.AdMob_Id));
                                adUtil.setString(AdUtil.AdMob_BannerAdId, jsonObject.getString(AdUtil.AdMob_BannerAdId));
                                adUtil.setString(AdUtil.AdMob_InterstitialAdId, jsonObject.getString(AdUtil.AdMob_InterstitialAdId));
                                adUtil.setString(AdUtil.AdMob_NativeAdId, jsonObject.getString(AdUtil.AdMob_NativeAdId));



                                MobileAds.initialize(context, new OnInitializationCompleteListener() {
                                    @Override
                                    public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

                                    }
                                });
                                if (adsLoaded != null) {
                                    adsLoaded.success();
                                }

                            } catch (JSONException e) {
                                Log.e("aabbbccc", ":fail" + task.isSuccessful());
                                if (adsLoaded != null) {
                                    adsLoaded.fail();
                                }
                            }

                        } else {
                            if (adsLoaded != null) {
                                adsLoaded.fail();
                            }
                        }
                    }
                });
    }

}
