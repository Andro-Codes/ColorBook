package pavishka.coloring.book.Activities.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import pavishka.coloring.book.R;


public class ScreenFillColorActivity extends AppCompatActivity {
    ImageView back;
    ImageView menu1;
    ImageView menu10;
    ImageView menu12;
    ImageView menu2;
    ImageView menu3;
    ImageView menu4;
    ImageView menu5;
    ImageView menu6;
    ImageView menu7;
    ImageView menu8;
    ImageView menu9;


    @Override

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_coloring_book);
        new ScreenMyRevenueView(this).setad((FrameLayout) findViewById(R.id.adView));
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenFillColorActivity.this.finish();
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.menu1);
        this.menu1 = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 0;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView ivfefa = (ImageView) findViewById(R.id.ivfefa);
        ivfefa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 16;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView ivsuperhero = (ImageView) findViewById(R.id.ivsuperhero);
        ivsuperhero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 17;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.menu2);
        this.menu2 = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 5;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.menu3);
        this.menu3 = imageView4;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 2;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView5 = (ImageView) findViewById(R.id.menu4);
        this.menu4 = imageView5;
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 3;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView6 = (ImageView) findViewById(R.id.menu5);
        this.menu5 = imageView6;
        imageView6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 1;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView7 = (ImageView) findViewById(R.id.menu6);
        this.menu6 = imageView7;
        imageView7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 4;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView8 = (ImageView) findViewById(R.id.menu7);
        this.menu7 = imageView8;
        imageView8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 6;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView9 = (ImageView) findViewById(R.id.menu8);
        this.menu8 = imageView9;
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 7;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView10 = (ImageView) findViewById(R.id.menu9);
        this.menu9 = imageView10;
        imageView10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 8;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView11 = (ImageView) findViewById(R.id.menu10);
        this.menu10 = imageView11;
        imageView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 9;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
        ImageView imageView13 = (ImageView) findViewById(R.id.menu12);
        this.menu12 = imageView13;
        imageView13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenConstable.anInt5 = 10;
                ScreenFillColorActivity.this.startActivity(new Intent(ScreenFillColorActivity.this, ScreenGridColoringBook.class));
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
