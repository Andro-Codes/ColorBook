package pavishka.coloring.book.Advertisement;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;

import java.util.Arrays;
import java.util.List;

public class Adshandler {


    public static InterstitialAd sInterstitialAd;

    public Adshandler() {
    }

    public static void showAd(Activity activity, OnClose onClose) {
        InterstitialAd interstitialAd = sInterstitialAd;
        if (interstitialAd != null) {
            interstitialAd.setFullScreenContentCallback(
                    new FullScreenContentCallback() {
                        @Override
                        public void onAdDismissedFullScreenContent() {
                            loadad(activity);
                            onClose.click();
                        }

                        @Override
                        public void onAdFailedToShowFullScreenContent(AdError adError) {
                            loadad(activity);
                        }

                        @Override
                        public void onAdShowedFullScreenContent() {
                        }
                    });
            interstitialAd.show(activity);
        } else {
            onClose.click();
            loadad(activity);
        }

    }

    public static void loadad(Activity context) {
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(
                context,
                AdUtil.getutil(context).getString(AdUtil.AdMob_InterstitialAdId),
                adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        sInterstitialAd = interstitialAd;
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        sInterstitialAd = null;
                    }
                });
    }

    public static void bannerad(AdView adViews, Context context) {

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adViews.loadAd(adRequest);

    }

    public void NativeBannerAd(Activity activity, TemplateView template, FrameLayout frameLayout) {
        googleNativeBannerAd(activity, template, frameLayout);
    }

    public void googleNativeBannerAd(Activity activity, final TemplateView template, FrameLayout frameLayout) {
        List<String> testDeviceIds = Arrays.asList();
        RequestConfiguration configuration =
                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
        MobileAds.setRequestConfiguration(configuration);
        MobileAds.initialize(activity, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {

            }
        });

        AdLoader adLoader = new AdLoader.Builder(activity, AdUtil.getutil(activity).getString(AdUtil.AdMob_NativeAdId))
                .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                    @Override
                    public void onNativeAdLoaded(NativeAd unifiedNativeAd) {
                        if (unifiedNativeAd == null) {
                            return;
                        }
                        if (activity.isDestroyed()) {
                            unifiedNativeAd.destroy();
                            return;
                        }

                        if (template.getVisibility() != View.VISIBLE) {
                            template.setVisibility(View.VISIBLE);
                        }
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .withAdListener(new AdListener() {

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        frameLayout.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                    }
                })
                .build();
        AdRequest adRequest;
        adRequest = new AdRequest.Builder().build();
        adLoader.loadAd(adRequest);
    }
    public interface OnClose {
        void click();
    }
}