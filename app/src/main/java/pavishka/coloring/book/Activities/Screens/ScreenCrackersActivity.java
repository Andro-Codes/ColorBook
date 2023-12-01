package pavishka.coloring.book.Activities.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import pavishka.babyphone.balloonanimation.BalloonAnimation;
import pavishka.babyphone.balloonanimation.TempBalloonData;
import pavishka.babyphone.rocketanimation.RocketAnimation;
import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;
import pavishka.coloring.book.R;

import java.util.Random;


public class ScreenCrackersActivity extends Activity {
    ScreenMyRevenueView myAdView;
    RelativeLayout animLayout;
    ScreenTempDataStore sharedPreference;
    BalloonAnimation balloonView;
    ImageView ivBack;
    ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;
    ScreenOwnAuidioPlayer myMediaPlayer;
    RocketAnimation rocketView;
    Handler handler;
    private TextView txt_time;
    private TextView txt_score;
    private int time = 0;
    private Runnable myRunnable;

    public int getRandomFaceSound() {
        switch (new Random().nextInt(9)) {
            case 0:
                return R.raw.face_9;
            case 1:
                return R.raw.face_1;
            case 2:
                return R.raw.face_2;
            case 3:
                return R.raw.face_3;
            case 4:
                return R.raw.face_4;
            case 5:
                return R.raw.face_5;
            case 6:
                return R.raw.face_6;
            case 7:
                return R.raw.face_7;
            default:
                return R.raw.face_8;
        }
    }

    private void setAd() {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.adViewTop);
        if (ScreenTempDataStore.getBuyChoise(this) == 0) {
            this.myAdView.setad(frameLayout);
        } else {
            frameLayout.setVisibility(View.GONE);
        }
    }

    private void startBalloon() {
        this.animLayout.setBackgroundResource(R.drawable.ic_sky_bg);
        this.balloonView = new BalloonAnimation(this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(13);
        this.balloonView.setLayoutParams(layoutParams);
        this.animLayout.addView(this.balloonView);
        this.balloonView.addOnAnimationEndListener(new BalloonAnimation.OnAnimationEndListener() {
            @Override
            public void onExplosion() {
                int i = balloonView != null ?balloonView.movingBallonsoneorTwocount():00;
                txt_score.setText(String.valueOf(i));

                String str = TempBalloonData.OBJECT;
                char c = 65535;
                switch (str.hashCode()) {
                    case -2075438705:
                        if (str.equals("tiger_sound")) {
                            c = 4;
                            break;
                        }
                        break;
                    case -1837653601:
                        if (str.equals("fox_sound")) {
                            c = 5;
                            break;
                        }
                        break;
                    case -1823676644:
                        if (str.equals("chick_sound")) {
                            c = 3;
                            break;
                        }
                        break;
                    case -1768249148:
                        if (str.equals("bear_sound")) {
                            c = 6;
                            break;
                        }
                        break;
                    case -1737945503:
                        if (str.equals("elephant_sound")) {
                            c = 2;
                            break;
                        }
                        break;
                    case -338856913:
                        if (str.equals("balloon")) {
                            c = 7;
                            break;
                        }
                        break;
                    case 3540562:
                        if (str.equals("star")) {
                            c = '\b';
                            break;
                        }
                        break;
                    case 99151942:
                        if (str.equals("heart")) {
                            c = '\t';
                            break;
                        }
                        break;
                    case 1516673194:
                        if (str.equals("rabbit_sound")) {
                            c = 0;
                            break;
                        }
                        break;
                    case 1788017438:
                        if (str.equals("pig_sound")) {
                            c = 1;
                            break;
                        }
                        break;
                }
                switch (c) {
                    case 0:
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(R.raw.rabbit_sound);
                            return;
                        }
                        return;
                    case 1:
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(R.raw.pig_sound);
                            return;
                        }
                        return;
                    case 2:
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(R.raw.elephant_sound);
                            return;
                        }
                        return;
                    case 3:
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(R.raw.chick_sound);
                            return;
                        }
                        return;
                    case 4:
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(R.raw.tiger_sound);
                            return;
                        }
                        return;
                    case 5:
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(R.raw.fox_sound);
                            return;
                        }
                        return;
                    case 6:
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(R.raw.bear_sound);
                            return;
                        }
                        return;
                    case 7:
                    case '\b':
                    case '\t':
                        if (myMediaPlayer != null) {
                            myMediaPlayer.playSound(getRandomFaceSound());
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }

            @Override
            public void onFinish() {
                Log.e("sddd", "");
            }
        });
        BalloonAnimation balloonAnimation = this.balloonView;
        if (balloonAnimation != null && balloonAnimation.isItReady()) {
            this.balloonView.start(8);
        }
    }

    private void startRocket() {
        this.animLayout.setBackgroundResource(R.drawable.ic_sky_bg);
        this.rocketView = new RocketAnimation(getApplicationContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(13);
        this.rocketView.setLayoutParams(layoutParams);
        this.animLayout.addView(this.rocketView);
        rocketView.addOnAnimationEndListener(new RocketAnimation.OnAnimationEndListener() {
            @Override
            public void onExplosion() {
                int i = rocketView != null ?rocketView.movingBallonsoneorTwocount():00;
                txt_score.setText(String.valueOf(i));
            }

            @Override
            public void onFinish() {

            }
        });
        RocketAnimation rocketAnimation = this.rocketView;
        if (rocketView != null && rocketView.isItReady()) {
            this.rocketView.start(8);
        }
    }

    private void stopAnimation() {
        if (rocketView != null) {
            rocketView.stopAnimation();
        }
        BalloonAnimation balloonAnimation = this.balloonView;
        if (balloonAnimation != null) {
            balloonAnimation.stopAnimation();
        }
        this.animLayout.removeAllViews();
    }

    protected void clind() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    public void initializeMediaPlayer() {
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = new ScreenAudioPlayerActivity();
        this.mediaPlayerSoundAndMusic = mediaPlayerSoundAndMusic;
        mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.coloring);
    }

    @Override
    public void onBackPressed() {
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        stopAnimation();
        finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_balloon_n_rocket);
        this.txt_time = (TextView) findViewById(R.id.txt_time);
        this.txt_score = (TextView) findViewById(R.id.txt_score);
        handler = new Handler();
        time = 0;
        myRunnable = new Runnable() {
            public void run() {
                time ++;
                if (time < 30) {
                    txt_time.setText(""+time);
                    handler.postDelayed(myRunnable,1000);
                } else {
                    startActivity(new Intent(ScreenCrackersActivity.this, ScreenFnishGamePopup.class).putExtra("key",txt_score.getText().toString()));
                    onBackPressed();
                }
            }
        };
        handler.postDelayed(myRunnable,1000);
        if (this.sharedPreference == null) {
            this.sharedPreference = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        }
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        initializeMediaPlayer();
        this.ivBack = (ImageView) findViewById(R.id.back);
        this.animLayout = (RelativeLayout) findViewById(R.id.balloonContainer);
        this.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (ScreenConstable.anInt5 == 100) {
            startBalloon();
        } else {
            startRocket();
        }
        this.myAdView = new ScreenMyRevenueView(this);
        setAd();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clind();
        this.mediaPlayerSoundAndMusic.startMainMusic();
    }

    @Override
    protected void onStop() {
        super.onStop();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }
}
