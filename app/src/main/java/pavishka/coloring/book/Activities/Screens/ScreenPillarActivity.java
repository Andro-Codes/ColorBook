package pavishka.coloring.book.Activities.Screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;
import pavishka.coloring.book.R;


public abstract class ScreenPillarActivity extends Activity {
    public static int height;
    ScreenMyRevenueView myAdView;
    ScreenTempDataStore sharedPreference;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (this.sharedPreference == null) {
            this.sharedPreference = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        }
        this.myAdView = new ScreenMyRevenueView(this);
    }

    public void setAd(FrameLayout frameLayout) {
        FrameLayout frameLayout2 = (FrameLayout) findViewById(R.id.adViewTop);
        if (ScreenTempDataStore.getBuyChoise(this) > 0) {
            frameLayout2.setVisibility(View.GONE);
        } else {
            this.myAdView.setad(frameLayout2);
        }
    }
}
