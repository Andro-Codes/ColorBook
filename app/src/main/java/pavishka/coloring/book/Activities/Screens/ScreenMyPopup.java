package pavishka.coloring.book.Activities.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import pavishka.coloring.book.R;


public class ScreenMyPopup extends ScreenPillarActivity implements View.OnClickListener {
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
            case R.id.btn_no :
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenMyPopup.this.finish();
                    }
                }, 100L);
                return;
            case R.id.btn_yes :
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenMyPopup.this.finish();
                        ScreenMyPopup.this.finishAffinity();
                        Intent intent = new Intent("android.intent.action.MAIN");
                        intent.addCategory("android.intent.category.HOME");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        System.out.println("After Finish ::");
                        ScreenMyPopup.this.startActivity(intent);
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
        setContentView(R.layout.activity_custom_dialog);
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        ImageView button = (ImageView) findViewById(R.id.btn_yes);
        ImageView button2 = (ImageView) findViewById(R.id.btn_no);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
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
