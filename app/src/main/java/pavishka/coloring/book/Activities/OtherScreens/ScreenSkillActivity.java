package pavishka.coloring.book.Activities.OtherScreens;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import pavishka.coloring.book.Activities.Screens.ScreenSharePopup;
import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenLanguage;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;


public class ScreenSkillActivity extends Activity implements View.OnClickListener {
    ImageView imageView;
    Intent intent;
    private ImageView back;
    private ScreenOwnAuidioPlayer mediaPlayer;
    private ImageView share_btn;

    private void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    private void finishActivity() {
        int i = ScreenConstable.anInt5;
        if (i == 8) {
            ScreenConstable.isBackFromDrawActivity = true;
            this.mediaPlayer.StopMp();
            this.mediaPlayer.playSound(R.raw.click);
            finish();
        } else if (i == 15) {
            ScreenConstable.isBackFromDrawActivity = true;
            this.mediaPlayer.StopMp();
            this.mediaPlayer.playSound(R.raw.click);
            finish();
        } else {
            ScreenConstable.isBackFromDrawActivity = true;
            this.mediaPlayer.StopMp();
            this.mediaPlayer.playSound(R.raw.click);
            finish();
        }
    }

    private void finishActivityWhenUrlNull() {
        ScreenConstable.isBackFromDrawActivity = true;
        finish();
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
    }

    private void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }


    @Override
    public void onBackPressed() {
        finishActivity();
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        int id = view.getId();
        if (id == R.id.back) {
            finishActivity();
        } else if (id == R.id.share_btn) {
            startActivity(new Intent(this, ScreenSharePopup.class).putExtra("keyval",SnapShotUtils.file.getAbsolutePath()));
        }
    }


    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ScreenLanguage().setUpLocale(this);
        if (SnapShotUtils.file == null) {
            finishActivityWhenUrlNull();
            return;
        }
        setContentView(R.layout.shareurart);
        ImageView imageView = (ImageView) findViewById(R.id.back);
        this.back = imageView;
        imageView.setOnClickListener(this);
        ImageView imageView2 = (ImageView) findViewById(R.id.iv_urart);
        this.imageView = imageView2;
        imageView2.setImageBitmap(BitmapFactory.decodeFile(SnapShotUtils.file.getAbsolutePath()));
        ImageView imageView3 = (ImageView) findViewById(R.id.share_btn);
        this.share_btn = imageView3;
        imageView3.setOnClickListener(this);
        this.mediaPlayer = new ScreenOwnAuidioPlayer(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
        new ScreenLanguage().setUpLocale(this);
    }



    @Override
    protected void onPause() {
        super.onPause();

    }
}
