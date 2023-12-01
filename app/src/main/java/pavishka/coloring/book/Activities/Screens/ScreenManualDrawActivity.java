package pavishka.coloring.book.Activities.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import pavishka.coloring.book.Manager.Chart.ScreenChartBluePrintGridActivity;
import pavishka.coloring.book.Manager.PhotoEditor.NewImageActivity;
import pavishka.coloring.book.R;


public class ScreenManualDrawActivity extends AppCompatActivity {
    ImageView back;
    ImageView menu17;
    ImageView menu18;
    ImageView menu19;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_custom_paint);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.adViewTop);
        ScreenMyRevenueView myAdView = new ScreenMyRevenueView(this);
        myAdView.setad(frameLayout);

        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualDrawActivity.this.finish();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.menu17);
        this.menu17 = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 15;
                ScreenManualDrawActivity.this.startActivity(new Intent(ScreenManualDrawActivity.this, ScreenChartBluePrintGridActivity.class));
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.menu18);
        this.menu18 = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 0;
                ScreenManualDrawActivity.this.startActivity(new Intent(ScreenManualDrawActivity.this, NewImageActivity.class));
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.menu19);
        this.menu19 = imageView4;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 11;
                ScreenManualDrawActivity.this.startActivity(new Intent(ScreenManualDrawActivity.this, ScreenGridColoringBook.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
