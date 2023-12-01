package pavishka.coloring.book.Activities.Screens;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.io.File;

import pavishka.coloring.book.R;


public class ScreenSamViewActivity extends Activity {
    ImageView imageView;
    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    String string;
    ScreenOwnAuidioPlayer myMediaPlayer;

    public void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    private void hideNavigation() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 14) {
            getWindow().getDecorView().setSystemUiVisibility(7686);
        } else if (i >= 11) {
            getWindow().getDecorView().setSystemUiVisibility(1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this, ScreenItemsActivity.class));
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_item_viewer);
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        this.string = getIntent().getExtras().getString("index");
        Bitmap decodeFile = BitmapFactory.decodeFile(string);
        ImageView imageView = (ImageView) findViewById(R.id.img);
        this.imageView = imageView;
        imageView.setImageBitmap(decodeFile);
        ImageView imageView2 = (ImageView) findViewById(R.id.delete);
        this.imageView2 = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myMediaPlayer.playSound(R.raw.click);
                final File absoluteFile = new File(string).getAbsoluteFile();
                DisplayMetrics displayMetrics = new DisplayMetrics();
                ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
                int i = displayMetrics.widthPixels;
                int i2 = i - (i / 9);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
                layoutParams.height = (i2 / 8) + i2;
                layoutParams.width = i2;
                layoutParams.gravity = 17;
                int i3 = Build.VERSION.SDK_INT >= 19 ? 5894 : 0;
                try {
                    final Dialog dialog = new Dialog(ScreenSamViewActivity.this, R.style.AlertDialogCustom);
                    dialog.getWindow().setFlags(8, 8);
                    dialog.getWindow().getDecorView().setSystemUiVisibility(i3);
                    dialog.setContentView(R.layout.dialog_save_delete);
                    dialog.setCancelable(false);
                    ((ConstraintLayout) dialog.findViewById(R.id.bg_dialog)).setLayoutParams(layoutParams);
                    TextView textView = (TextView) dialog.findViewById(R.id.msg);
                    ((ImageView) dialog.findViewById(R.id.picture)).setImageResource(R.drawable.ic_delete_dil_bg);
                    textView.setText(getString(R.string.delete));
                    ((ImageView) dialog.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            animateClick(view2);
                            myMediaPlayer.playSound(R.raw.click);
                            dialog.dismiss();
                            if (absoluteFile.delete()) {
                                onBackPressed();
                            }
                        }
                    });
                    ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view2) {
                            animateClick(view2);
                            myMediaPlayer.playSound(R.raw.click);
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.getWindow().clearFlags(8);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.back);
        this.imageView3 = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                onBackPressed();
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.share_btn);
        this.imageView1 = imageView4;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                startActivity(new Intent(ScreenSamViewActivity.this, ScreenSharePopup.class).putExtra("keyval", string));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
    }

}
