package pavishka.coloring.book.Activities.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import pavishka.coloring.book.R;


public class ScreenFnishGamePopup extends ScreenPillarActivity implements View.OnClickListener {
    ScreenOwnAuidioPlayer myMediaPlayer;

    private void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    private void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        playClickSound();
        switch (view.getId()) {
            case R.id.btn_close:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 100L);

                return;
            case R.id.btn_retry:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(ScreenFnishGamePopup.this, ScreenCrackersActivity.class));
                        finish();
                    }
                }, 100L);
                return;
            default:
                return;
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_finishgame_dialog);
        TextView txt_score = (TextView) findViewById(R.id.txt_score);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("key");
            //The key argument here must match that used in the other activity
            txt_score.setText("Score : "+value);

        }else{
            txt_score.setText("Score : 00");
        }

        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        ImageView btn_close = (ImageView) findViewById(R.id.btn_close);
        ImageView btn_retry = (ImageView) findViewById(R.id.btn_retry);
        btn_close.setOnClickListener(this);
        btn_retry.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
    }

    public void playClickSound() {
        if (ScreenConstable.SOUND_SETTING) {
            this.myMediaPlayer.playSound(R.raw.click);
        }
    }
}
