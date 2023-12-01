package pavishka.coloring.book.Activities.Screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import pavishka.coloring.book.R;


public class ScreenSharePopup extends ScreenPillarActivity implements View.OnClickListener {
    ScreenOwnAuidioPlayer myMediaPlayer;

    String string;
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
            case R.id.btn_share:
                sharePicture("");
                return;
            case R.id.btn_watsapp:
                sharePicture("com.whatsapp");
                return;
            case R.id.btn_facebook:
                sharePicture("com.facebook.katana");
                return;
            default:
                return;
        }
    }

    private Bitmap bitmap1;
    public void sharePicture(String pacage) {
        String absolutePath = string;
        Bitmap decodeFile = BitmapFactory.decodeFile(absolutePath);
        bitmap1 = decodeFile;
        String imgBitmapPath= MediaStore.Images.Media.insertImage(getContentResolver(),bitmap1,"title",null);
        Uri imgBitmapUri=Uri.parse(imgBitmapPath);

        String shareText="I have created an art work, let look at this painting."+"Try this awesome coloring game: https://play.google.com/store/apps/details?id=" + getPackageName();
        Intent shareIntent=new Intent(Intent.ACTION_SEND);
        if (!pacage.equals("")) {
            shareIntent.setPackage(pacage);
        }
        shareIntent.setType("*/*");
        shareIntent.putExtra(Intent.EXTRA_STREAM,imgBitmapUri);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent,"Share Wallpaper using"));

    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_share_dialog);
        this.string = getIntent().getExtras().getString("keyval");



        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        ImageView btn_close = (ImageView) findViewById(R.id.btn_close);
        ImageView btn_share = (ImageView) findViewById(R.id.btn_share);
        ImageView btn_facebook = (ImageView) findViewById(R.id.btn_facebook);
        ImageView btn_watsapp = (ImageView) findViewById(R.id.btn_watsapp);
        btn_close.setOnClickListener(this);
        btn_share.setOnClickListener(this);
        btn_facebook.setOnClickListener(this);
        btn_watsapp.setOnClickListener(this);
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
