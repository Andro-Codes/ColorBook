package pavishka.coloring.book.Activities.Screens;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import pavishka.coloring.book.R;


public class ScreenItemsActivity extends Activity {
    TextView textView;
    ImageView imageView;
    File[] files;
    ArrayList<String> strings = new ArrayList<>();
    ScreenOwnAuidioPlayer myMediaPlayer;
    ImageAdapter imageAdapter;

    public void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    private void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    public void getFromSdcard() {
        File dir = new ContextWrapper(this).getDir("gallery", 0);
        if (dir.isDirectory()) {
            File[] listFiles = dir.listFiles();
            this.files = listFiles;
            if (listFiles == null || listFiles.length <= 0) {
                this.textView.setVisibility(View.VISIBLE);
                return;
            }
            this.textView.setVisibility(View.GONE);
            for (File file : this.files) {
                this.strings.add(file.getAbsolutePath());
            }
        }
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
        this.myMediaPlayer.playSound(R.raw.click);
        finish();
    }

    @Override 
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_gallery);
        new ScreenMyRevenueView(this).setad((FrameLayout) findViewById(R.id.adView));
        ScreenOwnAuidioPlayer myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        this.myMediaPlayer = myMediaPlayer;
        myMediaPlayer.playSound(R.raw.colortouch13);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        ScreenConstable.heightInPixels = displayMetrics.heightPixels;
        ScreenConstable.widthInPixels = displayMetrics.widthPixels;
        TextView textView2 = (TextView) findViewById(R.id.empty_msg);
        this.textView = textView2;
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.imageView = imageView;
        imageView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ScreenItemsActivity.this.animateClick(view);
                ScreenItemsActivity.this.onBackPressed();
            }
        });
        getFromSdcard();
        GridView gridView = (GridView) findViewById(R.id.PhoneImageGrid);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        this.imageAdapter = imageAdapter;
        gridView.setAdapter((ListAdapter) imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() { 
            @Override 
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
                ScreenItemsActivity.this.animateClick(view);
                ScreenItemsActivity.this.myMediaPlayer.playSound(R.raw.click);
                Intent intent = new Intent(ScreenItemsActivity.this, ScreenSamViewActivity.class);
                intent.putExtra("index", ScreenItemsActivity.this.strings.get(i));
                ScreenItemsActivity.this.finish();
                ScreenItemsActivity.this.startActivity(intent);
            }
        });
    }

    @Override 
    protected void onResume() {
        super.onResume();
        hideNavigation();
    }

    
    public class ImageAdapter extends BaseAdapter {
        Context a;
        Bitmap b;
        LayoutInflater mInflater;

        public ImageAdapter(Context context) {
            this.a = context;
        }

        @Override 
        public long getItemId(int i) {
            return i;
        }

        @Override 
        public int getCount() {
            return ScreenItemsActivity.this.strings.size();
        }

        @Override 
        public Object getItem(int i) {
            return Integer.valueOf(i);
        }

        @Override 
        public View getView(int i, View view, ViewGroup viewGroup) {
            int i2 = ScreenConstable.widthInPixels;
            int i3 = (i2 / 2) - (i2 / 40);
            int i4 = (i3 * 3) / 2;
            if (view == null) {
                view = ((LayoutInflater) this.a.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.gallery_item, viewGroup, false);
            }
            ImageView imageView = (ImageView) view.findViewById(R.id.thumbImage);
            ((FrameLayout) view.findViewById(R.id.parent)).setLayoutParams(new AbsListView.LayoutParams(i3, i4));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            Bitmap decodeFile = BitmapFactory.decodeFile(ScreenItemsActivity.this.strings.get(i));
            this.b = decodeFile;
            imageView.setImageBitmap(decodeFile);
            return view;
        }
    }
}
