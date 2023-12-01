package pavishka.coloring.book.Activities.OtherScreens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import pavishka.coloring.book.R;
import pavishka.coloring.book.Advertisement.AdsLoaded;
import pavishka.coloring.book.Advertisement.DataCapture;


public class ScreenDelayActivity extends Activity {
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash_screen);
        DataCapture.getInstance().initAds(this, new AdsLoaded() {
            @Override
            public void success() {
                new Handler().postDelayed(() -> {
                    ScreenDelayActivity.this.startActivity(new Intent(ScreenDelayActivity.this, ScreenInitiateActivity.class));
                    ScreenDelayActivity.this.finish();
                }, 1000);
            }

            @Override
            public void fail() {
                new Handler().postDelayed(() -> {
                    ScreenDelayActivity.this.startActivity(new Intent(ScreenDelayActivity.this, ScreenInitiateActivity.class));
                    ScreenDelayActivity.this.finish();
                }, 1000);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
