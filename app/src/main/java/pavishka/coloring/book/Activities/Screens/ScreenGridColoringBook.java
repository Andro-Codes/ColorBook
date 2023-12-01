package pavishka.coloring.book.Activities.Screens;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import pavishka.coloring.book.R;


public class ScreenGridColoringBook extends Activity implements View.OnClickListener {
    public static int pos;
    ScreenPhotoAdapter imageAdapter;
    ImageView back;
    ImageView gallery;
    RecyclerView gridView;
    ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;
    ScreenOwnAuidioPlayer myMediaPlayer;
    RelativeLayout topll;
    ArrayList<String> strings = new ArrayList<>();
    String picDirectory = "";

    private void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    private void checkTotalServerImages() {
/*        if (MyConstant.anInt5 == 14) {
//        puzzel
            String[] images = new String[0];
            try {
                images = getAssets().list("maze");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> listImages = new ArrayList<String>(Arrays.asList(images));
            Log.e("aaaaa",":assetpath:"+listImages.size());

            for(String s : listImages){

                int resID = getResources().getIdentifier("maze/"+s, "drawable", getPackageName());

                Log.e("aaaaa",":assetpath:"+resID);
                a.add(String.valueOf(resID));
            }
            GridActivityColoringBook gridActivityColoringBook = GridActivityColoringBook.this;
            gridActivityColoringBook.imageAdapter.refresh(a);
        } else if (MyConstant.anInt5 == 12) {
//        dote

            String[] images = new String[0];
            try {
                images = getAssets().list("onnecting_dots");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> listImages = new ArrayList<String>(Arrays.asList(images));
            Log.e("aaaaa",":assetpath:"+listImages.get(0));
            a.addAll(listImages);
            GridActivityColoringBook gridActivityColoringBook = GridActivityColoringBook.this;
            gridActivityColoringBook.imageAdapter.refresh(a);
        } else if (MyConstant.anInt5 == 15) {

        }*/
    }

    private void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    private void intialize() {
        ScreenConstable.selectedImageFromBitmap = null;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        ScreenConstable.heightInPixels = displayMetrics.heightPixels;
        ScreenConstable.widthInPixels = displayMetrics.widthPixels;
        int i = ScreenConstable.anInt5;
        if (i == 16) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.fifa;
            this.picDirectory = "fifa";
            loadFromLocale();
            checkTotalServerImages();
        } else  if (i == 17) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.superhiro;
            this.picDirectory = "superhiro";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 0) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers5;
            this.picDirectory = "animal";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 1) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers6;
            this.picDirectory = "toy";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 2) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers7;
            ScreenConstable.aNull = ScreenConstable.integers20;
            this.picDirectory = "shape";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 3) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers9;
            this.picDirectory = "food";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 4) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers8;
            this.picDirectory = "fruit";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 5) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers10;
            ScreenConstable.aNull = ScreenConstable.integers;
            this.picDirectory = "vehicle";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 6) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers11;
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 7) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers12;
            loadFromLocale();
        } else if (i == 8) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers13;
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 9) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers14;
            ScreenConstable.aNull = ScreenConstable.integers4;
            this.picDirectory = "fantasy";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 10) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers15;
            ScreenConstable.aNull = ScreenConstable.integers21;
            this.picDirectory = "christmas";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 11) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers16;
            ScreenConstable.aNull = ScreenConstable.integers22;
            this.picDirectory = "frame";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 12) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers23;
            this.picDirectory = "connecting_dots";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 13) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.empty_bitmapIds;
            this.picDirectory = "worksheet";
            loadFromLocale();
            checkTotalServerImages();
        } else if (i == 14) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers24;
            this.picDirectory = "maze";
            loadFromLocale();
            checkTotalServerImages();
        }
    }


    private void loadFromLocale() {
        int i = 0;
        while (true) {
            Integer[] numArr = ScreenConstable.selected_bitmapIds;
            if (i < numArr.length) {
                Log.e("aaaaa", ":loadFromLocale:" + String.valueOf(numArr[i]).trim());
                this.strings.add(String.valueOf(numArr[i]).trim());
                i++;
            } else {
                loadPictures();
                return;
            }
        }
    }

    private void loadPictures() {
        File[] listFiles;
        File dir = new ContextWrapper(this).getDir(this.picDirectory, 0);
        if (dir.isDirectory() && (listFiles = dir.listFiles()) != null && listFiles.length > 0) {
            for (int length = listFiles.length - 1; length >= 0; length--) {
                Log.e("aaaaa", ":loadPictures:" + String.valueOf(listFiles[length].getAbsolutePath()));
                this.strings.add(String.valueOf(listFiles[length].getAbsolutePath()));
            }
        }
    }


    public void finishActivity() {
        ScreenConstable.showNewApp = true;
        finish();
        this.myMediaPlayer.playSound(R.raw.click);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        int id = view.getId();
        if (id == R.id.back) {
            finishActivity();
        } else if (id == R.id.gallery) {
            startActivity(new Intent(this, ScreenItemsActivity.class));
        }
    }

    FrameLayout frameLayout;
    @Override
    public void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.grid_layout);
        this.frameLayout = (FrameLayout) findViewById(R.id.adViewTop);
            ScreenMyRevenueView myAdView = new ScreenMyRevenueView(this);
            myAdView.setad(frameLayout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = new ScreenAudioPlayerActivity();
        this.mediaPlayerSoundAndMusic = mediaPlayerSoundAndMusic;
        mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.submenu);
        this.topll = (RelativeLayout) findViewById(R.id.topll);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.grid_view);
        this.gridView = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.gridView.setHasFixedSize(true);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.gallery);
        this.gallery = imageView2;
        imageView2.setOnClickListener(this);
        imageAdapter = new ScreenPhotoAdapter(this, this.strings);
        this.gridView.setAdapter(imageAdapter);
        intialize();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = this.mediaPlayerSoundAndMusic;
        if (mediaPlayerSoundAndMusic != null) {
            mediaPlayerSoundAndMusic.destroyMusic();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = this.mediaPlayerSoundAndMusic;
        if (mediaPlayerSoundAndMusic != null) {
            mediaPlayerSoundAndMusic.pauseMainMusic();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = this.mediaPlayerSoundAndMusic;
        if (mediaPlayerSoundAndMusic != null) {
            mediaPlayerSoundAndMusic.startMainMusic();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = this.mediaPlayerSoundAndMusic;
        if (mediaPlayerSoundAndMusic != null) {
            mediaPlayerSoundAndMusic.pauseMainMusic();
        }
    }
}
