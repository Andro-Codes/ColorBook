package pavishka.coloring.book.Manager.Chart;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import pavishka.coloring.book.Activities.Screens.ScreenItemsActivity;
import pavishka.coloring.book.Activities.Screens.ScreenAudioPlayerActivity;
import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenLanguage;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.R;


public class ScreenChartBluePrintGridActivity extends Activity implements View.OnClickListener {
    public static int pos;
    ChartAdapter graphAdapter;
    ImageView gallery;
    RecyclerView gridView;
    ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;
    ScreenOwnAuidioPlayer myMediaPlayer;
    RelativeLayout topll;
    ArrayList<String> strings = new ArrayList<>();
    String picDirectory = "";
    private ImageView back;

    private void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
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
        ScreenConstable.selected_bitmapIds = ScreenConstable.bitmapGraphIds;
        this.picDirectory = ScreenConstable.DIR_GRAPH;
        loadFromLocale();

    }


    private void loadFromLocale() {
        int i = 0;
        while (true) {
            Integer[] numArr = ScreenConstable.selected_bitmapIds;
            if (i < numArr.length) {
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

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        new ScreenLanguage().setUpLocale(this);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_graph_grid);
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
        intialize();
        ChartAdapter graphAdapter = new ChartAdapter(this, this.strings);
        this.graphAdapter = graphAdapter;
        this.gridView.setAdapter(graphAdapter);
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
