package pavishka.coloring.book.VectorArt;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import pavishka.coloring.book.Advertisement.Adshandler;
import pavishka.coloring.book.Activities.Screens.ScreenAudioPlayerActivity;
import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenLanguage;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;


public class ScreenPixelSkillActivity extends Activity implements View.OnClickListener {
    static boolean aBoolean = false;
    FrameLayout frameLayout;
    FrameLayout frameLayout1;
    FrameLayout frameLayout2;
    FrameLayout frameLayout3;
    LinearLayout linearLayout;
    LinearLayout linearLayout1;
    VectorBasedDrawing pixelDrawing;
    ArrayList<Carding> grids;
    int anInt = 0;
    int anInt1;
    ScreenOwnAuidioPlayer myMediaPlayer;
    FrameLayout frameLayout4;
    ScreenTempDataStore sharedPreference;
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView imageView10;
    ImageView imageView11;
    ImageView imageView12;
    ImageView imageView13;
    ImageView imageView14;
    ImageView imageView15;
    ImageView imageView16;
    FrameLayout frameLayout5;
    FrameLayout frameLayout6;
    FrameLayout frameLayout7;
    FrameLayout frameLayout8;
    FrameLayout frameLayout9;
    FrameLayout frameLayout10;
    FrameLayout frameLayout11;
    FrameLayout frameLayout12;
    private ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;

    private void animateClick(View view) {
        Animation loadAnimation = AnimationUtils.loadAnimation(this, R.anim.bounce_low);
        loadAnimation.setDuration(100L);
        view.startAnimation(loadAnimation);
    }

    public void animateHint() {
        this.imageView3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_low));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScreenPixelSkillActivity.this.animateHint();
            }
        }, 2500L);
    }

    private void dialogComplete() {
        this.myMediaPlayer.playSound(R.raw.clap);
        this.myMediaPlayer.speakApplause();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.height = i;
        layoutParams.width = i;
        layoutParams.gravity = 17;
        int i2 = Build.VERSION.SDK_INT >= 19 ? 5894 : 0;
        try {
            final Dialog dialog = new Dialog(this, R.style.AlertDialogCustom);
            dialog.getWindow().setFlags(8, 8);
            dialog.getWindow().getDecorView().setSystemUiVisibility(i2);
            dialog.setContentView(R.layout.dialog_complete);
            ((FrameLayout) dialog.findViewById(R.id.bg_dialog)).setLayoutParams(layoutParams);
            ImageView button = (ImageView) dialog.findViewById(R.id.next);
            ((ImageView) dialog.findViewById(R.id.rays)).startAnimation(AnimationUtils.loadAnimation(this, R.anim.rotation));
            button.startAnimation(AnimationUtils.loadAnimation(this, R.anim.zoomin_zoomout_coloringbook));
            ((ImageView) dialog.findViewById(R.id.close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenPixelSkillActivity.this.myMediaPlayer.playClickSound();
                    Adshandler.showAd(ScreenPixelSkillActivity.this, new Adshandler.OnClose() {
                        @Override
                        public void click() {
                        }
                    });
                    ScreenPixelSkillActivity.aBoolean = false;
                    ScreenPixelSkillActivity.this.setUpUI();
                    ScreenPixelSkillActivity pixelArtActivity = ScreenPixelSkillActivity.this;
                    pixelArtActivity.loadPicture(pixelArtActivity.getPicture());
                    ScreenPixelSkillActivity.this.myMediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenPixelSkillActivity.this.myMediaPlayer.playClickSound();
                    Adshandler.showAd(ScreenPixelSkillActivity.this, new Adshandler.OnClose() {
                        @Override
                        public void click() {
                        }
                    });
                    ScreenPixelSkillActivity.aBoolean = false;
                    ScreenPixelSkillActivity pixelArtActivity = ScreenPixelSkillActivity.this;
                    pixelArtActivity.anInt1++;
                    pixelArtActivity.setUpUI();
                    ScreenPixelSkillActivity pixelArtActivity2 = ScreenPixelSkillActivity.this;
                    pixelArtActivity2.loadPicture(pixelArtActivity2.getPicture());
                    ScreenPixelSkillActivity.this.myMediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().clearFlags(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<Carding> getHintPicture() {
        ArrayList<Carding> arrayList = new ArrayList<>();
        int i = 0;
        for (int i2 = 0; i2 < this.pixelDrawing.getNumColumns(); i2++) {
            for (int i3 = 0; i3 < this.pixelDrawing.getNumRows(); i3++) {
                Carding grid = new Carding(i2, i3, 8);
                if (isExistRowCol(grid)) {
                    arrayList.add(this.grids.get(i));
                    i++;
                } else {
                    arrayList.add(grid);
                }
            }
        }
        return arrayList;
    }

    private String getJSONFromAsset() {
        InputStream inputStream;
        try {
            if (ScreenCardingPixbasedActivity.gridType == 10) {
                inputStream = getAssets().open("Grid/GridDesign10.json");
            } else {
                inputStream = getAssets().open("Grid/GridDesign15.json");
            }
            byte[] bArr = new byte[inputStream.available()];
            inputStream.read(bArr);
            inputStream.close();
            return new String(bArr, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPicture() {
        try {
            JSONArray jSONArray = new JSONObject(getJSONFromAsset()).getJSONArray(ScreenConstable.JSON_ARRAY_NAME);
            if (this.anInt1 < 0) {
                this.anInt1 = jSONArray.length() - 1;
            }
            if (this.anInt1 > jSONArray.length()) {
                this.anInt1 = 0;
            }
            return jSONArray.getJSONObject(this.anInt1).toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

    private void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    private void initIds() {
        this.linearLayout1 = (LinearLayout) findViewById(R.id.drawing_board);
        this.linearLayout = (LinearLayout) findViewById(R.id.picture_container);
        this.imageView4 = (ImageView) findViewById(R.id.hint_hand);
        this.imageView = (ImageView) findViewById(R.id.back);
        this.imageView1 = (ImageView) findViewById(R.id.next);
        this.imageView2 = (ImageView) findViewById(R.id.prev);
        this.imageView3 = (ImageView) findViewById(R.id.hint);
        this.frameLayout5 = (FrameLayout) findViewById(R.id.color1);
        this.frameLayout6 = (FrameLayout) findViewById(R.id.color2);
        this.frameLayout7 = (FrameLayout) findViewById(R.id.color3);
        this.frameLayout8 = (FrameLayout) findViewById(R.id.color4);
        this.frameLayout9 = (FrameLayout) findViewById(R.id.color5);
        this.frameLayout10 = (FrameLayout) findViewById(R.id.color6);
        this.frameLayout11 = (FrameLayout) findViewById(R.id.color7);
        this.frameLayout12 = (FrameLayout) findViewById(R.id.color8);
        this.frameLayout = (FrameLayout) findViewById(R.id.color9);
        this.frameLayout1 = (FrameLayout) findViewById(R.id.color10);
        this.frameLayout2 = (FrameLayout) findViewById(R.id.color11);
        this.frameLayout3 = (FrameLayout) findViewById(R.id.color12);
        this.imageView5 = (ImageView) findViewById(R.id.check1);
        this.imageView6 = (ImageView) findViewById(R.id.check2);
        this.imageView7 = (ImageView) findViewById(R.id.check3);
        this.imageView8 = (ImageView) findViewById(R.id.check4);
        this.imageView9 = (ImageView) findViewById(R.id.check5);
        this.imageView10 = (ImageView) findViewById(R.id.check6);
        this.imageView11 = (ImageView) findViewById(R.id.check7);
        this.imageView12 = (ImageView) findViewById(R.id.check8);
        this.imageView13 = (ImageView) findViewById(R.id.check9);
        this.imageView14 = (ImageView) findViewById(R.id.check10);
        this.imageView15 = (ImageView) findViewById(R.id.check11);
        this.imageView16 = (ImageView) findViewById(R.id.check12);
        this.imageView.setOnClickListener(this);
        this.imageView1.setOnClickListener(this);
        this.imageView2.setOnClickListener(this);
        this.imageView3.setOnClickListener(this);
        this.frameLayout5.setOnClickListener(this);
        this.frameLayout6.setOnClickListener(this);
        this.frameLayout7.setOnClickListener(this);
        this.frameLayout8.setOnClickListener(this);
        this.frameLayout9.setOnClickListener(this);
        this.frameLayout10.setOnClickListener(this);
        this.frameLayout11.setOnClickListener(this);
        this.frameLayout12.setOnClickListener(this);
        this.frameLayout.setOnClickListener(this);
        this.frameLayout1.setOnClickListener(this);
        this.frameLayout2.setOnClickListener(this);
        this.frameLayout3.setOnClickListener(this);
        showTick(this.imageView5);
    }

    private void initializeMediaPlayer() {
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = new ScreenAudioPlayerActivity();
        this.mediaPlayerSoundAndMusic = mediaPlayerSoundAndMusic;
        mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.submenu);
    }

    private boolean isExist(Carding grid) {
        for (int i = 0; i < this.grids.size(); i++) {
            if (this.grids.get(i).getRow() == grid.getRow() && this.grids.get(i).getColumn() == grid.getColumn() && this.grids.get(i).getColor() == grid.getColor()) {
                return true;
            }
        }
        return false;
    }

    private boolean isExistRowCol(Carding grid) {
        for (int i = 0; i < this.grids.size(); i++) {
            if (this.grids.get(i).getRow() == grid.getRow() && this.grids.get(i).getColumn() == grid.getColumn()) {
                return true;
            }
        }
        return false;
    }

    private boolean isSame(ArrayList<Carding> arrayList) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (!isExist(arrayList.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void loadPicture(String str) {
        int i;
        if (aBoolean) {
            this.imageView3.setImageResource(R.drawable.hint_on);
        } else {
            this.imageView3.setImageResource(R.drawable.hint_off);
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (!this.grids.isEmpty()) {
                ArrayList<Carding> arrayList = this.grids;
                arrayList.removeAll(arrayList);
            }
            for (int i2 = 0; i2 < jSONObject.length(); i2++) {
                ArrayList<Carding> arrayList2 = this.grids;
                int i3 = jSONObject.getInt(ScreenConstable.JSON_ROW + i2);
                int i4 = jSONObject.getInt(ScreenConstable.JSON_COLUMN + i2);
                arrayList2.add(new Carding(i3, i4, jSONObject.getInt(ScreenConstable.JSON_COLOR + i2)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int i5 = this.anInt;
        int i6 = (i5 / 4) + (i5 / 9);
        if (ScreenCardingPixbasedActivity.gridType == 10) {
            i = i6 % 10;
        } else {
            i = i6 % 15;
        }
        int i7 = i6 - i;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.height = i7;
        layoutParams.width = i7;
        VectorBasedDrawing pixelDrawing = new VectorBasedDrawing(this, null);
        pixelDrawing.setNumRows(ScreenCardingPixbasedActivity.gridType);
        pixelDrawing.setNumColumns(ScreenCardingPixbasedActivity.gridType);
        pixelDrawing.setPicture(this.grids);
        pixelDrawing.setLayoutParams(layoutParams);
        this.linearLayout.removeAllViews();
        this.linearLayout.addView(pixelDrawing);
    }

    private void setAd() {
        this.frameLayout4 = (FrameLayout) findViewById(R.id.adViewTop);
            this.frameLayout4.setVisibility(View.GONE);
    }

    public void setUpUI() {
        int i;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.widthPixels;
        this.anInt = i2;
        int i3 = i2 - (i2 / 9);
        if (ScreenCardingPixbasedActivity.gridType == 10) {
            i = i3 % 10;
        } else {
            i = i3 % 15;
        }
        int i4 = i3 - i;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.height = i4;
        layoutParams.width = i4;
        VectorBasedDrawing pixelDrawing = new VectorBasedDrawing(this, null);
        this.pixelDrawing = pixelDrawing;
        pixelDrawing.setLayoutParams(layoutParams);
        this.pixelDrawing.setNumRows(ScreenCardingPixbasedActivity.gridType);
        this.pixelDrawing.setNumColumns(ScreenCardingPixbasedActivity.gridType);
        this.linearLayout1.removeAllViews();
        this.linearLayout1.addView(this.pixelDrawing);
    }

    private void showTick(ImageView imageView) {
        this.imageView5.setImageResource(0);
        this.imageView6.setImageResource(0);
        this.imageView7.setImageResource(0);
        this.imageView8.setImageResource(0);
        this.imageView9.setImageResource(0);
        this.imageView10.setImageResource(0);
        this.imageView11.setImageResource(0);
        this.imageView12.setImageResource(0);
        this.imageView13.setImageResource(0);
        this.imageView14.setImageResource(0);
        this.imageView15.setImageResource(0);
        this.imageView16.setImageResource(0);
        imageView.setImageResource(R.drawable.tick);
    }

    private void toggleHint() {
        if (aBoolean) {
            aBoolean = false;
            this.imageView3.setImageResource(R.drawable.hint_off);
            return;
        }
        aBoolean = true;
        this.imageView3.setImageResource(R.drawable.hint_on);
    }

    public void checkGrid() {
        if (this.pixelDrawing.grids.size() == this.grids.size() && isSame(this.pixelDrawing.grids)) {
            dialogComplete();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(0, R.anim.slide_out_left);
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        this.myMediaPlayer.playSound(R.raw.click);
        String locale = this.sharedPreference.getLocale(this);
        int id = view.getId();
        switch (id) {
            case R.id.back :
                onBackPressed();
                return;
            case R.id.hint :
                ScreenConstable.hint_pixelart = false;
                this.imageView4.setVisibility(View.GONE);
                toggleHint();
                this.pixelDrawing.setHintPicture(getHintPicture());
                return;
            case R.id.next :
                aBoolean = false;
                this.anInt1++;
                setUpUI();
                loadPicture(getPicture());
                return;
            case R.id.prev :
                aBoolean = false;
                this.anInt1--;
                setUpUI();
                loadPicture(getPicture());
                return;
            default:
                switch (id) {
                    case R.id.color1 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_black);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch1);
                        }
                        ScreenConstable.selected_color = 0;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView5);
                        return;
                    case R.id.color10 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_white);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch10);
                        }
                        ScreenConstable.selected_color = 8;
                        ScreenConstable.eraser = true;
                        showTick(this.imageView14);
                        return;
                    case R.id.color11 :
                        this.myMediaPlayer.playSound(R.raw.colortouch1);
                        ScreenConstable.selected_color = 10;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView15);
                        return;
                    case R.id.color12 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_grey);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch11);
                        }
                        ScreenConstable.selected_color = 11;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView16);
                        return;
                    case R.id.color2 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_blue);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch2);
                        }
                        ScreenConstable.selected_color = 1;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView6);
                        return;
                    case R.id.color3 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_brown);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch3);
                        }
                        ScreenConstable.selected_color = 2;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView7);
                        return;
                    case R.id.color4 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_green);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch4);
                        }
                        ScreenConstable.selected_color = 3;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView8);
                        return;
                    case R.id.color5 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_orange);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch5);
                        }
                        ScreenConstable.selected_color = 4;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView9);
                        return;
                    case R.id.color6 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_pink);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch6);
                        }
                        ScreenConstable.selected_color = 5;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView10);
                        return;
                    case R.id.color7 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_purple);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch7);
                        }
                        ScreenConstable.selected_color = 6;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView11);
                        return;
                    case R.id.color8 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_red);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch8);
                        }
                        ScreenConstable.selected_color = 7;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView12);
                        return;
                    case R.id.color9 :
                        if (locale.equals("en")) {
                            this.myMediaPlayer.playSound(R.raw.color_yellow);
                        } else {
                            this.myMediaPlayer.playSound(R.raw.colortouch9);
                        }
                        ScreenConstable.selected_color = 9;
                        ScreenConstable.eraser = false;
                        showTick(this.imageView13);
                        return;
                    default:
                        return;
                }
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_pixel_art);
        aBoolean = false;
        if (this.sharedPreference == null) {
            this.sharedPreference = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        }
        getWindow().addFlags(128);
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        ScreenConstable.selected_color = 0;
        ScreenConstable.eraser = false;
        this.anInt1 = getIntent().getExtras().getInt(ScreenConstable.PICTURE_CODE);
        this.grids = new ArrayList<>();
        initIds();
        initializeMediaPlayer();
        setUpUI();
        loadPicture(getPicture());
        setAd();
        animateHint();
        if (ScreenConstable.hint_pixelart) {
            this.imageView4.setVisibility(View.VISIBLE);
            ((AnimationDrawable) this.imageView4.getDrawable()).start();
            return;
        }
        this.imageView4.setVisibility(View.GONE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
        this.mediaPlayerSoundAndMusic.startMainMusic();
        if (ScreenTempDataStore.getBuyChoise(this) > 0) {
            this.frameLayout4.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }
}
