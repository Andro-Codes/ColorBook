package pavishka.coloring.book.Activities.OtherScreens;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.Screens.ScreenIncomeActivity;
import pavishka.coloring.book.Activities.Screens.ScreenMyPopup;
import pavishka.coloring.book.Activities.Screens.ScreenMyRevenueView;
import pavishka.coloring.book.Advertisement.Adshandler;


public class ScreenInitiateActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_start);
        new ScreenMyRevenueView(this).setad((FrameLayout) findViewById(R.id.adView));
        findViewById(R.id.share).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                inggamesStartActivity(view);
            }
        });
        findViewById(R.id.rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                inggamesStartActivity1(view);
            }
        });
        findViewById(R.id.privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                inggamesStartActivity2(view);
            }
        });
        findViewById(R.id.start).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                inggamesStartActivity3(view);
            }
        });
        findViewById(R.id.iv_credit).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                startActivity(new Intent(ScreenInitiateActivity.this, ScreenIncomeActivity.class));
            }
        });
    }


    public void inggamesStartActivity(View view) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + getPackageName());
        intent.setType("text/plain");
        startActivity(intent);
    }


    public void inggamesStartActivity1(View view) {
        try {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
        } catch (Exception unused) {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(myAppLinkToMarket);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(this, "Unable to find market app", Toast.LENGTH_LONG).show();
            }
        }
    }


    public void inggamesStartActivity2(View view) {
        Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("https://renjuism.blogspot.com/2023/10/privacy-policy.html"));
        startActivity(intent);
    }


    public void inggamesStartActivity3(View view) {
        Adshandler.showAd(this, new Adshandler.OnClose() {
            @Override
            public void click() {
                startActivity(new Intent(ScreenInitiateActivity.this, MainActivity.class));
            }
        });
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ScreenMyPopup.class));
    }
}
