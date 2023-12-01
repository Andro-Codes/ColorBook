package pavishka.coloring.book.Activities.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import pavishka.coloring.book.R;


public class ScreenBabyFunActivity extends AppCompatActivity {
    ImageView back;
    ImageView menu13;
    ImageView menu14;
    ImageView menu15;
    ImageView menu16;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_kids_game);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.adViewTop);
        ScreenMyRevenueView myAdView = new ScreenMyRevenueView(this);
        myAdView.setad(frameLayout);

        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.menu13);
        this.menu13 = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 12;
                startActivity(new Intent(ScreenBabyFunActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.menu14);
        this.menu14 = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 14;
                startActivity(new Intent(ScreenBabyFunActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.menu15);
        this.menu15 = imageView4;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 101;
                startActivity(new Intent(ScreenBabyFunActivity.this, ScreenCrackersActivity.class));
            }
        });
        ImageView imageView5 = (ImageView) findViewById(R.id.menu16);
        this.menu16 = imageView5;
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 100;
                startActivity(new Intent(ScreenBabyFunActivity.this, ScreenCrackersActivity.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
