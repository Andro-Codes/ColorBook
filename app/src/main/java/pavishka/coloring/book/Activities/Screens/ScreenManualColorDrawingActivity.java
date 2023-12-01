package pavishka.coloring.book.Activities.Screens;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pavishka.coloring.book.Activities.OtherScreens.ScreenSkillActivity;
import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;
import pavishka.coloring.book.Activities.OtherScreens.SnapShotUtils;
import pavishka.coloring.book.Activities.OtherScreens.ScreenThumbSticekrAdapter;
import pavishka.coloring.book.Activities.OtherScreens.ScreenSearchSeekBar;
import pavishka.coloring.book.R;
import pavishka.coloring.book.Advertisement.Adshandler;
import pavishka.coloring.book.Manager.ChooseColor.CMYKObserver;
import pavishka.coloring.book.Manager.ChooseColor.ColorPickerView;


public class ScreenManualColorDrawingActivity extends ScreenPillarActivity implements View.OnClickListener {
    private static final String TAG = "DrawActivity";
    public static ScreenManualColorDrawingActivity drawActivity = null;
    public static boolean ispatternClicked = false;
    public static ImageView iv;
    public static Bitmap myart;
    public static int newHeight;
    public static int newWidth;
    private static float seek_cos_X;
    private static float seek_cos_Y;
    public ScreenOwnAuidioPlayer mediaPlayer;
    public FrameLayout zoomFrame;
    public boolean isCol_picked = false;
    public boolean isZoom = false;
    public boolean useTexture = false;
    protected DrawerLayout drawerLayout;
    protected DrawerLayout drawerLayout1;
    ImageView imageView;
    LinearLayout linearLayout;
    RecyclerView recyclerView;
    boolean aBoolean;
    ScreenTempDataStore sharedPreference1;
    boolean aBoolean1;
    int anInt;
    View view;
    LinearLayout linearLayout1;
    LinearLayout linearLayout2;
    ConstraintLayout constraintLayout;
    ImageView imageView1;
    ColorPickerView colorPickerView;
    int[] ints;
    ArrayList<Integer> integers = new ArrayList<>();
    int anInt1 = 0;
    private ImageView back;
    private ImageView choose_colortype;
    private View drawerViewBrush;
    private View drawerViewColor;
    private ImageView eraser;
    private HorizontalAdapter horizontalAdapter;
    private RecyclerView horizontal_recycler_view;
    private ImageView mPaint;
    private ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;
    private ScreenSlatPen myDrawView;
    private ImageView newPage;
    private ImageView pen;
    private ImageView save;
    private SeekBar seekbar_hor;
    private RelativeLayout seekbar_lay;
    private ScreenSearchSeekBar seekbar_var;
    private ImageView sound;
    private boolean writePermission;
    private ImageView zoom;
    private boolean isdraweropenedBrush = false;
    private boolean isdraweropenedColor = false;
    private int listItemDefaultPos = -1;
    private int row_index = -1;

    public static ScreenManualColorDrawingActivity getDrawActivity() {
        return drawActivity;
    }

    private void ShowDialogPermissions(int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.heightPixels;
        int i3 = displayMetrics.widthPixels;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.height = i2 - (i2 / 3);
        layoutParams.width = i3 - (i3 / 7);
        final Dialog dialog = new Dialog(this, R.style.AlertDialogCustom);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.first_time_dialog);
        ((RelativeLayout) dialog.findViewById(R.id.parent)).setLayoutParams(layoutParams);
        ImageView button = (ImageView) dialog.findViewById(R.id.dialogbtn_close);
        ImageView button2 = (ImageView) dialog.findViewById(R.id.dialogbtn_retry);
        ((LinearLayout) dialog.findViewById(R.id.lay_permissions)).setVisibility(View.VISIBLE);
        if (i == 1) {
        } else if (i == 2) {
            button2.setVisibility(View.INVISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                ScreenManualColorDrawingActivity.this.mediaPlayer.playClickSound();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                    }
                }, 100L);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                ScreenManualColorDrawingActivity.this.mediaPlayer.playClickSound();
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                Toast.makeText(drawActivity2, drawActivity2.getString(R.string.longpress), Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialog.dismiss();
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                if (drawActivity2.sharedPreference1.getStoragePermissionNever(drawActivity2)) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", ScreenManualColorDrawingActivity.this.getPackageName(), null));
                    ScreenManualColorDrawingActivity.this.startActivityForResult(intent, 1111);
                    return true;
                }
                ActivityCompat.requestPermissions(ScreenManualColorDrawingActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PointerIconCompat.TYPE_CONTEXT_MENU);
                return true;
            }
        });
        dialog.show();
    }

    public void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_in_low));
    }

    public void brushSelectedOnClickButton() {
        if (ScreenConstable.SELECTED_TOOL == 2) {
            ScreenConstable.SELECTED_TOOL = 1;
            ScreenConstable.strokeWidth = ScreenConstable.brushSize;
            ScreenConstable.erase = false;
            menuSelectedClick(1);
        }
    }

    public void closeAllDrawer() {
        this.drawerLayout1.closeDrawer(this.drawerViewColor);
        this.drawerLayout.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedColor = false;
        this.isdraweropenedBrush = false;
    }

    public void closePicker() {
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 2000.0f);
        translateAnimation.setDuration(500L);
        this.constraintLayout.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ScreenManualColorDrawingActivity.this.view.setVisibility(View.VISIBLE);
                ScreenManualColorDrawingActivity.this.constraintLayout.setVisibility(View.GONE);
                ScreenManualColorDrawingActivity.this.imageView1.setEnabled(true);
            }

            @Override
            public void onAnimationStart(Animation animation) {
                ScreenManualColorDrawingActivity.this.view.setVisibility(View.VISIBLE);
                ScreenManualColorDrawingActivity.this.imageView1.setEnabled(false);
            }
        });
    }

    public void disableClick() {
        this.aBoolean1 = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ScreenManualColorDrawingActivity.this.aBoolean1 = true;
            }
        }, 700L);
    }

    public void enableColorDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);
    }

    private void finishActivity() {
        try {
            if (SnapShotUtils.file.getAbsolutePath() != null) {
                ScreenConstable.isBackFromDrawActivity = true;
                this.mediaPlayer.StopMp();
                this.mediaPlayer.playSound(R.raw.click);
                Intent intent = new Intent(this, ScreenSkillActivity.class);
                intent.putExtra("BitmapImage", SnapShotUtils.file.getAbsolutePath());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                finish();
                return;
            }
            finishActivityNoSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void finishActivityNoSave() {
        ScreenConstable.isBackFromDrawActivity = true;
        this.mediaPlayer.StopMp();
        this.mediaPlayer.playSound(R.raw.click);
        finish();
        startActivity(new Intent(this, ScreenGridColoringBook.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
        Adshandler.showAd(this, new Adshandler.OnClose() {
            @Override
            public void click() {
            }
        });
    }


    private void loadStickerToList() {
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        this.integers.clear();
        this.integers.add(Integer.valueOf((int) R.drawable.stick_1));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_2));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_3));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_4));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_5));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_6));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_7));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_8));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_9));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_10));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_11));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_12));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_13));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_14));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_15));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_16));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_17));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_18));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_19));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_20));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_21));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_22));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_23));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_24));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_25));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_26));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_27));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_28));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_29));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_30));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_31));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_32));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_33));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_34));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_35));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_36));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_37));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_38));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_39));
        this.integers.add(Integer.valueOf((int) R.drawable.stick_40));
        this.recyclerView.setAdapter(new ScreenThumbSticekrAdapter(this, this.integers, 0));
    }

    private void menuSelectedClick(int i) {
        switch (i) {
            case 1:
                this.pen.setImageResource(R.drawable.menu1pencil_sel);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.save.setImageResource(R.drawable.menu5save);
                this.newPage.setImageResource(R.drawable.menu7close);
                this.mPaint.setImageResource(R.drawable.menu3bucket);
                return;
            case 2:
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.save.setImageResource(R.drawable.menu5save);
                this.newPage.setImageResource(R.drawable.menu7close);
                this.mPaint.setImageResource(R.drawable.menu3bucket);
                return;
            case 3:
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser_sel);
                this.save.setImageResource(R.drawable.menu5save);
                this.newPage.setImageResource(R.drawable.menu7close);
                this.mPaint.setImageResource(R.drawable.menu3bucket);
                return;
            case 4:
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.save.setImageResource(R.drawable.menu5save_sel);
                this.newPage.setImageResource(R.drawable.menu7close);
                this.mPaint.setImageResource(R.drawable.menu3bucket);
                return;
            case 5:
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.save.setImageResource(R.drawable.menu5save);
                this.newPage.setImageResource(R.drawable.menu7close_sel);
                this.mPaint.setImageResource(R.drawable.menu3bucket);
                return;
            case 6:
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.save.setImageResource(R.drawable.menu5save);
                this.newPage.setImageResource(R.drawable.menu7close);
                this.mPaint.setImageResource(R.drawable.menu3bucket_sel);
                return;
            case 7:
                this.pen.setImageResource(R.drawable.menu1pencil);
                this.eraser.setImageResource(R.drawable.menu4eraser);
                this.save.setImageResource(R.drawable.menu5save);
                this.newPage.setImageResource(R.drawable.menu7close);
                this.mPaint.setImageResource(R.drawable.menu3bucket);
                return;
            default:
                return;
        }
    }

    public void openPicker() {
        int i = this.anInt1;
        if (i < 0 || i >= 5) {
            this.anInt1 = 0;
        }
        for (int i2 = 0; i2 < this.ints.length; i2++) {
            if (i2 == this.anInt1) {
                this.linearLayout2.getChildAt(i2).setScaleX(0.9f);
                this.linearLayout2.getChildAt(i2).setScaleY(0.9f);
            } else {
                this.linearLayout2.getChildAt(i2).setScaleX(1.0f);
                this.linearLayout2.getChildAt(i2).setScaleY(1.0f);
            }
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 2000.0f, 0.0f);
        translateAnimation.setDuration(500L);
        this.constraintLayout.setVisibility(View.VISIBLE);
        this.constraintLayout.startAnimation(translateAnimation);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ScreenManualColorDrawingActivity.this.imageView1.setEnabled(true);
            }

            @Override
            public void onAnimationStart(Animation animation) {
                ScreenManualColorDrawingActivity.this.view.setVisibility(View.GONE);
                ScreenManualColorDrawingActivity.this.constraintLayout.setVisibility(View.VISIBLE);
                ScreenManualColorDrawingActivity.this.imageView1.setEnabled(false);
            }
        });
    }

    public void saveBitmap() {
        requestPermissionWrite();
        if (this.writePermission) {
            this.myDrawView.setDrawingCacheEnabled(true);
            try {
                SnapShotUtils.insertImage(this, getContentResolver(), this.myDrawView.getDrawingCache(), "drawing", "storage");
                this.mediaPlayer.playSound(R.raw.camera_click);
            } catch (Exception unused) {
            }
            this.myDrawView.destroyDrawingCache();
        }
    }

    public void saveBitmapOnBackPressed() {
        requestPermissionWrite();
        if (this.writePermission) {
            this.myDrawView.setDrawingCacheEnabled(true);
            try {
                SnapShotUtils.insertImage(this, getContentResolver(), this.myDrawView.getDrawingCache(), "drawing", "storage");
                this.mediaPlayer.playSound(R.raw.camera_click);
                myart = this.myDrawView.getDrawingCache();
            } catch (Exception unused) {
            }
            finishActivity();
        }
    }

    public void setBrushClick(DrawerLayout drawerLayout, View view, int i) {
        ScreenConstable.SELECTED_TOOL = 1;
        drawerLayout.closeDrawer(view);
        this.mediaPlayer.playSound(R.raw.select);
        this.isdraweropenedBrush = false;
        ScreenConstable.brushSize = i;
        ScreenConstable.erase = false;
    }

    public void setFillType(DrawerLayout drawerLayout, View view, int i, boolean z) {
        ispatternClicked = false;
        drawerLayout.closeDrawer(view);
        ScreenConstable.TYPE_FILL = i;
        this.isdraweropenedColor = false;
        ispatternClicked = z;
        refreshData(D(i));
    }


    public void C(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.GONE);
        drawerLayout.setFocusable(false);
        drawerLayout.setClickable(false);
        drawerLayout.setEnabled(false);
    }


    public List<ScreenInfo> D(int i) {
        if (i == 0) {
            return setBottomColorsList();
        }
        if (i == 1) {
            return setBottomPatternList();
        }
        if (i == 2) {
            return setBottomGlitterList();
        }
        if (i != 3) {
            return setBottomColorsList();
        }
        return setBottomTextureList();
    }


    public void dec() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    if (ScreenManualColorDrawingActivity.this.myDrawView.kidBitmap == null) {
                        ScreenManualColorDrawingActivity.this.mediaPlayer.playColorRandomSound();
                    } else if (!ScreenManualColorDrawingActivity.this.sharedPreference1.getLocale(ScreenManualColorDrawingActivity.this).equals("en") || ScreenConstable.aNull.length < ScreenGridColoringBook.pos || ScreenConstable.anInt5 == 10 || ScreenConstable.anInt5 == 9 || ScreenConstable.anInt5 == 11 || ScreenConstable.anInt5 == 13 || ScreenConstable.anInt5 == 12 || ScreenConstable.anInt5 == 14 || ScreenConstable.anInt5 == 16 || ScreenConstable.anInt5 == 17) {
                        ScreenManualColorDrawingActivity.this.mediaPlayer.playColorRandomSound();
                    } else {
                        ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(ScreenConstable.aNull[ScreenGridColoringBook.pos].intValue());
                    }
                } catch (Exception unused) {
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playColorRandomSound();
                }
            }
        }, 1000L);
    }

    public void addStickerToCanvas(int i) {
        this.myDrawView.addNewSticker(i);
        this.linearLayout.setVisibility(View.GONE);
    }


    public void chooseDrawingType() {
        int i = ScreenConstable.anInt5;
        if (i == 0) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers5;
            ScreenConstable.aNull = ScreenConstable.integers17;
        } else if (i == 1) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers6;
            ScreenConstable.aNull = ScreenConstable.integers18;
        } else if (i == 2) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers7;
            ScreenConstable.aNull = ScreenConstable.integers20;
        } else if (i == 3) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers9;
            ScreenConstable.aNull = ScreenConstable.integers19;
        } else if (i == 4) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers8;
            ScreenConstable.aNull = ScreenConstable.integers1;
        } else if (i == 5) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers10;
            ScreenConstable.aNull = ScreenConstable.integers;
        } else if (i == 6) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers11;
            ScreenConstable.aNull = ScreenConstable.integers2;
        } else if (i == 7) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers12;
            ScreenConstable.aNull = ScreenConstable.integers3;
        } else if (i == 8) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers13;
        } else if (i == 9) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers14;
        } else if (i == 10) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers15;
        } else if (i == 11) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers16;
        } else if (i == 16) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.fifa;
        } else if (i == 17) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.superhiro;
        }
    }

    public void closePickerIfOpen() {
        if (this.aBoolean1) {
            disableClick();
            if (this.constraintLayout.getVisibility() == View.VISIBLE) {
                closePicker();
            }
        }
    }

    public void createNewPageDialog(int i) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i2 = displayMetrics.widthPixels;
        int i3 = i2 - (i2 / 9);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        layoutParams.height = (i3 / 8) + i3;
        layoutParams.width = i3;
        layoutParams.gravity = 17;
        int i4 = Build.VERSION.SDK_INT >= 19 ? 5894 : 0;
        try {
            final Dialog dialog = new Dialog(this, R.style.AlertDialogCustom);
            dialog.getWindow().setFlags(8, 8);
            dialog.getWindow().getDecorView().setSystemUiVisibility(i4);
            dialog.setContentView(R.layout.dialog_save_delete);
            dialog.setCancelable(false);
            ((ConstraintLayout) dialog.findViewById(R.id.bg_dialog)).setLayoutParams(layoutParams);
            TextView textView = (TextView) dialog.findViewById(R.id.msg);
            ((ImageView) dialog.findViewById(R.id.picture)).setImageResource(R.drawable.ic_delete_dil_bg);
            textView.setText(getString(R.string.clear));
            ((ImageView) dialog.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenManualColorDrawingActivity.this.animateClick(view);
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    ScreenManualColorDrawingActivity.this.myDrawView.startNew();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenManualColorDrawingActivity.this.animateClick(view);
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().clearFlags(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void disableBrushDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.GONE);
        drawerLayout.setFocusable(false);
        drawerLayout.setClickable(false);
        drawerLayout.setEnabled(false);
    }

    public void drawerImplementationForBrush() {
        this.drawerLayout = (DrawerLayout) findViewById(R.id.dr_layout_brush);
        this.drawerViewBrush = findViewById(R.id.drawer_brush);
        this.drawerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                drawActivity2.drawerLayout1.closeDrawer(drawActivity2.drawerViewColor);
                ScreenManualColorDrawingActivity drawActivity3 = ScreenManualColorDrawingActivity.this;
                drawActivity3.drawerLayout.closeDrawer(drawActivity3.drawerViewBrush);
                ScreenManualColorDrawingActivity.this.isdraweropenedColor = false;
                ScreenManualColorDrawingActivity.this.isdraweropenedBrush = false;
                return true;
            }
        });
        View findViewById = findViewById(R.id.ivBrushSize1);
        View findViewById2 = findViewById(R.id.ivBrushSize2);
        View findViewById3 = findViewById(R.id.ivBrushSize3);
        this.drawerLayout.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedBrush = false;
        findViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                        drawActivity2.setBrushClick(drawActivity2.drawerLayout, drawActivity2.drawerViewBrush, 20);
                    }
                }, 100L);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                        drawActivity2.setBrushClick(drawActivity2.drawerLayout, drawActivity2.drawerViewBrush, 30);
                    }
                }, 100L);
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                        drawActivity2.setBrushClick(drawActivity2.drawerLayout, drawActivity2.drawerViewBrush, 40);
                    }
                }, 100L);
            }
        });
        this.drawerLayout.addDrawerListener(new ActionBarDrawerToggle(this, this.drawerLayout, R.string.app_name, R.string.black_bat) {
            @Override

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.drawer_close);
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                drawActivity2.disableBrushDrawer(drawActivity2.drawerLayout);
            }

            @Override

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.drawer);
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                drawActivity2.enableBrushDrawer(drawActivity2.drawerLayout);
            }
        });
    }

    public void drawerImplementationForColor() {
        this.drawerLayout1 = (DrawerLayout) findViewById(R.id.dr_layout_color);
        this.drawerViewColor = findViewById(R.id.drawer_color);
        this.drawerLayout1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                drawActivity2.drawerLayout.closeDrawer(drawActivity2.drawerViewBrush);
                ScreenManualColorDrawingActivity drawActivity3 = ScreenManualColorDrawingActivity.this;
                drawActivity3.drawerLayout1.closeDrawer(drawActivity3.drawerViewColor);
                ScreenManualColorDrawingActivity.this.isdraweropenedBrush = false;
                ScreenManualColorDrawingActivity.this.isdraweropenedColor = false;
                return true;
            }
        });
        View findViewById = findViewById(R.id.ivColorSize1);
        View findViewById2 = findViewById(R.id.ivColorSize2);
        View findViewById3 = findViewById(R.id.ivColorSize3);
        View findViewById4 = findViewById(R.id.ivColorSize4);
        this.drawerLayout1.closeDrawer(this.drawerViewColor);
        this.isdraweropenedColor = false;
        findViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                ScreenManualColorDrawingActivity.this.myDrawView.isDraw = true;
                ScreenManualColorDrawingActivity.this.useTexture = false;
                ScreenConstable.TYPE_FILL = 0;
                ScreenManualColorDrawingActivity.ispatternClicked = false;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                        drawActivity2.setFillType(drawActivity2.drawerLayout1, drawActivity2.drawerViewColor, 0, false);
                    }
                }, 100L);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                ScreenManualColorDrawingActivity.this.myDrawView.isDraw = true;
                ScreenManualColorDrawingActivity.this.useTexture = false;
                ScreenConstable.TYPE_FILL = 1;
                ScreenManualColorDrawingActivity.ispatternClicked = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                        drawActivity2.setFillType(drawActivity2.drawerLayout1, drawActivity2.drawerViewColor, 1, true);
                    }
                }, 100L);
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.myDrawView.isDraw = true;
                ScreenManualColorDrawingActivity.this.useTexture = false;
                ScreenConstable.TYPE_FILL = 2;
                ScreenManualColorDrawingActivity.ispatternClicked = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                        drawActivity2.setFillType(drawActivity2.drawerLayout1, drawActivity2.drawerViewColor, 2, true);
                    }
                }, 100L);
            }
        });
        findViewById4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScreenManualColorDrawingActivity.this.animateClick(view);
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                drawActivity2.useTexture = true;
                ScreenConstable.TYPE_FILL = 3;
                drawActivity2.aBoolean = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualColorDrawingActivity drawActivity3 = ScreenManualColorDrawingActivity.this;
                        drawActivity3.setFillType(drawActivity3.drawerLayout1, drawActivity3.drawerViewColor, 3, false);
                    }
                }, 100L);
            }
        });
        this.drawerLayout1.addDrawerListener(new ActionBarDrawerToggle(this, this.drawerLayout1, R.string.app_name, R.string.black_bat) {
            @Override

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.drawer_close);
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                drawActivity2.C(drawActivity2.drawerLayout1);
            }

            @Override

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.drawer);
                ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                drawActivity2.enableColorDrawer(drawActivity2.drawerLayout1);
            }
        });
    }

    protected void enableBrushDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);
    }


    public void findByViewIds() {
        this.imageView = (ImageView) findViewById(R.id.stickers);
        this.linearLayout = (LinearLayout) findViewById(R.id.lay_sticker);
        this.recyclerView = (RecyclerView) findViewById(R.id.st_recyclerview);
        this.constraintLayout = (ConstraintLayout) findViewById(R.id.lay_popUpPicker);
        this.linearLayout1 = (LinearLayout) findViewById(R.id.picker_container);
        this.imageView1 = (ImageView) findViewById(R.id.close_picker);
        this.linearLayout2 = (LinearLayout) findViewById(R.id.col_history);
        this.view = findViewById(R.id.layer);
        this.zoom = (ImageView) findViewById(R.id.zoom);
        this.seekbar_hor = (SeekBar) findViewById(R.id.seekbar_hor);
        this.seekbar_var = (ScreenSearchSeekBar) findViewById(R.id.seekbar_var);
        this.seekbar_lay = (RelativeLayout) findViewById(R.id.seekbar_lay);
        this.zoomFrame = (FrameLayout) findViewById(R.id.zoomFrame);
        this.horizontal_recycler_view = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        this.myDrawView = (ScreenSlatPen) findViewById(R.id.draw);
        this.pen = (ImageView) findViewById(R.id.pen);
        this.choose_colortype = (ImageView) findViewById(R.id.choose_colortype);
        this.back = (ImageView) findViewById(R.id.back);
        this.eraser = (ImageView) findViewById(R.id.eraser);
        this.save = (ImageView) findViewById(R.id.save);
        this.newPage = (ImageView) findViewById(R.id.newPage);
        this.mPaint = (ImageView) findViewById(R.id.mPaint);
        iv = (ImageView) findViewById(R.id.iv);
        this.sound = (ImageView) findViewById(R.id.sound);
        this.pen.setOnClickListener(this);
        this.eraser.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.newPage.setOnClickListener(this);
        this.mPaint.setOnClickListener(this);
        this.choose_colortype.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.sound.setOnClickListener(this);
        this.zoom.setOnClickListener(this);
        this.imageView.setOnClickListener(this);
        this.imageView1.setOnClickListener(this);
        if (!ScreenConstable.SOUND_SETTING) {
            this.sound.setImageResource(R.drawable.but_mute);
        }
        this.seekbar_hor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                float unused = ScreenManualColorDrawingActivity.seek_cos_X = (ScreenManualColorDrawingActivity.this.zoomFrame.getWidth() * i) / 100.0f;
                if (ScreenManualColorDrawingActivity.seek_cos_X < ScreenManualColorDrawingActivity.this.zoomFrame.getHeight()) {
                    FrameLayout frameLayout = ScreenManualColorDrawingActivity.this.zoomFrame;
                    frameLayout.setPivotX((i * frameLayout.getWidth()) / 100.0f);
                    ScreenManualColorDrawingActivity.this.zoomFrame.setPivotY(ScreenManualColorDrawingActivity.seek_cos_Y);
                }
            }
        });
        this.seekbar_var.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                float unused = ScreenManualColorDrawingActivity.seek_cos_Y = (ScreenManualColorDrawingActivity.this.zoomFrame.getHeight() * i) / 100;
                if (ScreenManualColorDrawingActivity.seek_cos_Y < ScreenManualColorDrawingActivity.this.zoomFrame.getHeight()) {
                    ScreenManualColorDrawingActivity.this.zoomFrame.setPivotX(ScreenManualColorDrawingActivity.seek_cos_X);
                    FrameLayout frameLayout = ScreenManualColorDrawingActivity.this.zoomFrame;
                    frameLayout.setPivotY((i * frameLayout.getHeight()) / 100);
                }
            }
        });
        if (Build.VERSION.SDK_INT <= 19) {
            ((View) this.imageView.getParent()).setVisibility(View.GONE);
        } else {
            loadStickerToList();
        }
        this.constraintLayout.setVisibility(View.GONE);
    }


    public void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }


    public void initialize() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        ScreenConstable.heightInPixels = displayMetrics.heightPixels;
        ScreenConstable.widthInPixels = displayMetrics.widthPixels;
        double d = ScreenConstable.heightInPixels;
        double d2 = ScreenConstable.widthInPixels;
        Double.isNaN(d);
        Double.isNaN(d2);
        ScreenConstable.screenRatio = d / d2;
        ScreenConstable.erase = false;
        ScreenConstable.SELECTED_TOOL = -1;
        this.writePermission = false;
        ispatternClicked = false;
    }

    public void initializeMediaPlayer() {
        this.mediaPlayer = new ScreenOwnAuidioPlayer(this);
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = new ScreenAudioPlayerActivity();
        this.mediaPlayerSoundAndMusic = mediaPlayerSoundAndMusic;
        mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.coloring);
    }


    public void initializeOnSizeChangedValue() {
        ScreenConstable.onSizeCalled = 0;
    }

    public void insertBitmap() {
        if (ScreenConstable.selectedImageFromBitmap != null && ScreenConstable.fromGridActivityColoringBook) {
            ScreenConstable.fromGridActivityColoringBook = false;
            insertKidBitmap(ScreenConstable.selectedImageFromBitmap);
        }
    }

    public void insertKidBitmap(Bitmap bitmap) {
        try {
            this.myDrawView.kidBitmap = bitmap;
            double d = ScreenConstable.drawHeight;
            double height = this.myDrawView.kidBitmap.getHeight();
            Double.isNaN(d);
            Double.isNaN(height);
            double d2 = d / height;
            double d3 = ScreenConstable.drawWidth;
            double width = this.myDrawView.kidBitmap.getWidth();
            Double.isNaN(d3);
            Double.isNaN(width);
            double d4 = d3 / width;
            double width2 = this.myDrawView.kidBitmap.getWidth();
            Double.isNaN(width2);
            newWidth = (int) (width2 * d4);
            double height2 = this.myDrawView.kidBitmap.getHeight();
            Double.isNaN(height2);
            int i = (int) (height2 * d2);
            newHeight = i;
            if (i > 0 && newWidth > 0) {
                ScreenSlatPen drawingPicture = this.myDrawView;
                drawingPicture.kidBitmap = Bitmap.createScaledBitmap(drawingPicture.kidBitmap, newWidth, i, true);
                this.myDrawView.setKidsImage();
                if (ScreenConstable.selectedTool == -1) {
                    ScreenConstable.selectedTool = 1;
                }
            }
            if (!(ScreenConstable.anInt5 == 11 || ScreenConstable.anInt5 == 12 || ScreenConstable.anInt5 == 14)) {
                ScreenConstable.SELECTED_TOOL = 0;
                menuSelectedClick(6);
                ScreenConstable.strokeWidth = 0;
                return;
            }
            ScreenConstable.SELECTED_TOOL = 1;
            menuSelectedClick(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        ScreenConstable.isBackFromGlow = false;
        if (this.linearLayout.getVisibility() == View.VISIBLE) {
            this.linearLayout.setVisibility(View.GONE);
        } else {
            savePageDialogOnBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        int i;
        animateClick(view);
        this.mediaPlayer.playSound(R.raw.click);
        switch (view.getId()) {
            case R.id.back:
                ScreenConstable.isBackFromGlow = false;
                if (this.aBoolean1) {
                    disableClick();
                    savePageDialogOnBackPressed();
                }
                closeAllDrawer();
                return;
            case R.id.choose_colortype:
                this.myDrawView.isDraw = true;
                enableColorDrawer(this.drawerLayout1);
                if (!this.isdraweropenedColor) {
                    this.drawerLayout1.openDrawer(this.drawerViewColor);
                    this.isdraweropenedColor = true;
                } else {
                    this.drawerLayout1.closeDrawer(this.drawerViewColor);
                    this.isdraweropenedColor = false;
                }
                closePickerIfOpen();
                return;
            case R.id.close_picker:
                if (this.isCol_picked) {
                    this.horizontalAdapter.notifyDataSetChanged();
                }
                this.isCol_picked = false;
                closePickerIfOpen();
                return;
            case R.id.eraser:
                if (this.useTexture) {
                    this.useTexture = false;
                    ispatternClicked = false;
                    ScreenConstable.TYPE_FILL = 0;
                    refreshData(D(0));
                }
                this.myDrawView.isDraw = true;
                menuSelectedClick(3);
                ScreenConstable.SELECTED_TOOL = 2;
                ScreenConstable.strokeWidth = ScreenConstable.eraseWidth;
                ScreenConstable.erase = true;
                closeAllDrawer();
                this.aBoolean = true;
                closePickerIfOpen();
                return;
            case R.id.mPaint:
                this.useTexture = false;
                this.myDrawView.isDraw = true;
                menuSelectedClick(6);
                ScreenConstable.SELECTED_TOOL = 0;
                ScreenConstable.strokeWidth = 0;
                ScreenConstable.erase = false;
                closeAllDrawer();
                if (ScreenConstable.TYPE_FILL == 3) {
                    ScreenConstable.TYPE_FILL = 0;
                }
                refreshData(D(ScreenConstable.TYPE_FILL));
                if (this.aBoolean) {
                    this.aBoolean = false;
                }
                closePickerIfOpen();
                return;
            case R.id.newPage:
                menuSelectedClick(5);
                if (this.aBoolean1) {
                    disableClick();
                    createNewPageDialog(-1);
                }
                closeAllDrawer();
                closePickerIfOpen();
                return;
            case R.id.pen:
                this.useTexture = false;
                this.myDrawView.isDraw = true;
                ScreenConstable.SELECTED_TOOL = 1;
                ScreenConstable.erase = false;
                menuSelectedClick(1);
                enableBrushDrawer(this.drawerLayout);
                if (!this.isdraweropenedBrush) {
                    this.drawerLayout.openDrawer(this.drawerViewBrush);
                    this.isdraweropenedBrush = true;
                } else {
                    this.drawerLayout.closeDrawer(this.drawerViewBrush);
                    this.isdraweropenedBrush = false;
                }
                if (ScreenConstable.TYPE_FILL == 3) {
                    ScreenConstable.TYPE_FILL = 0;
                }
                refreshData(D(ScreenConstable.TYPE_FILL));
                if (this.aBoolean) {
                    this.aBoolean = false;
                }
                closePickerIfOpen();
                return;
            case R.id.save:
                if (this.aBoolean1) {
                    disableClick();
                    savePageDialog();
                }
                closeAllDrawer();
                closePickerIfOpen();
                return;
            case R.id.sound:
                if (!ScreenConstable.SOUND_SETTING) {
                    Toast.makeText(drawActivity, getString(R.string.mute), Toast.LENGTH_SHORT).show();
                    return;
                } else if (this.myDrawView.kidBitmap == null) {
                    this.mediaPlayer.playColorRandomSound();
                    return;
                } else if (!this.sharedPreference1.getLocale(this).equals("en") || ScreenConstable.aNull.length < ScreenGridColoringBook.pos || (i = ScreenConstable.anInt5) == 10 || i == 9 || i == 11 || i == 13 || i == 12 || i == 14 || i == 16 || i == 17) {
                    this.mediaPlayer.playColorRandomSound();
                    return;
                } else {
                    this.mediaPlayer.playSound(ScreenConstable.aNull[ScreenGridColoringBook.pos].intValue());
                    return;
                }
            case R.id.stickers:
                this.myDrawView.isDraw = false;
                if (this.linearLayout.getVisibility() == View.VISIBLE) {
                    this.linearLayout.setVisibility(View.GONE);
                } else {
                    this.linearLayout.setVisibility(View.VISIBLE);
                }
                closePickerIfOpen();
                return;
            case R.id.zoom:
                this.seekbar_hor.setProgress(0);
                this.seekbar_var.setProgress(0);
                if (this.isZoom) {
                    this.seekbar_lay.setVisibility(View.GONE);
                    zoom(Float.valueOf(1.0f), Float.valueOf(1.0f), new PointF(0.0f, 0.0f));
                    this.isZoom = false;
                    this.zoom.setImageResource(R.drawable.zoom_in);
                } else {
                    this.seekbar_lay.setVisibility(View.VISIBLE);
                    zoom(Float.valueOf(2.0f), Float.valueOf(2.0f), new PointF(0.0f, 0.0f));
                    this.isZoom = true;
                    this.zoom.setImageResource(R.drawable.zoom_out);
                }
                this.myDrawView.invalidate();
                closePickerIfOpen();
                return;
            default:
                return;
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.sharedPreference1 = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        ScreenConstable.TYPE_FILL = 0;
        ScreenConstable.selectedPatternBitmap = null;
        this.useTexture = false;
        this.aBoolean = false;
        ispatternClicked = false;
        this.aBoolean1 = true;
        this.anInt = ContextCompat.getColor(this, R.color.color25);
        this.ints = new int[]{ContextCompat.getColor(this, R.color.color25), ContextCompat.getColor(this, R.color.color25), ContextCompat.getColor(this, R.color.color25), ContextCompat.getColor(this, R.color.color25), ContextCompat.getColor(this, R.color.color25)};
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bitmap bitmap = this.myDrawView.canvasBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.myDrawView.canvasBitmap = null;
        }
        Bitmap bitmap2 = this.myDrawView.kidBitmap;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.myDrawView.kidBitmap = null;
        }
    }

    @Override
    public void onPause() {
        hideNavigation();
        super.onPause();

        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1001) {
            int length = strArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                String str = strArr[i2];
                if (iArr[i2] == -1 && (Build.VERSION.SDK_INT < 23 || !shouldShowRequestPermissionRationale(str))) {
                    this.sharedPreference1.saveStoragePermissionNever(this, true);
                }
            }
        }
    }


    @Override
    public void onResume() {
        hideNavigation();
        C(this.drawerLayout1);
        disableBrushDrawer(this.drawerLayout);
        this.mediaPlayerSoundAndMusic.startMainMusic();
        super.onResume();
    }

    @Override
    protected void onStart() {
        hideNavigation();
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }

    @Override
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            hideNavigation();
        }
    }


    public void refreshData(List<ScreenInfo> list) {
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(list, getApplication());
        this.horizontalAdapter = horizontalAdapter;
        this.horizontal_recycler_view.setAdapter(horizontalAdapter);
        if (ScreenConstable.TYPE_FILL == 0) {
            this.listItemDefaultPos = this.horizontalAdapter.getItemCount() - 3;
        } else {
            this.listItemDefaultPos = this.horizontalAdapter.getItemCount() - 1;
        }
        this.row_index = this.listItemDefaultPos;
        this.horizontalAdapter.notifyDataSetChanged();
    }

    public void requestPermissionWrite() {
        if (Build.VERSION.SDK_INT < 23) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PointerIconCompat.TYPE_CONTEXT_MENU);
            Log.v(TAG, "Permission is granted");
            this.writePermission = true;
        } else if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            this.writePermission = true;
            Log.v(TAG, "Permission is granted");
        } else {
            if (this.sharedPreference1.getStoragePermissionNever(this)) {
                ShowDialogPermissions(2);
            } else {
                ShowDialogPermissions(1);
            }
            Log.v(TAG, "Permission is revoked");
        }
    }

    public void savePageDialog() {
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
            final Dialog dialog = new Dialog(this, R.style.AlertDialogCustom);
            dialog.getWindow().setFlags(8, 8);
            dialog.getWindow().getDecorView().setSystemUiVisibility(i3);
            dialog.setContentView(R.layout.dialog_save_delete);
            dialog.setCancelable(false);
            ((ConstraintLayout) dialog.findViewById(R.id.bg_dialog)).setLayoutParams(layoutParams);
            TextView textView = (TextView) dialog.findViewById(R.id.msg);
            ((ImageView) dialog.findViewById(R.id.picture)).setImageResource(R.drawable.ic_save_pic_bg);
            textView.setText(getString(R.string.store_picture_title));
            ((ImageView) dialog.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenManualColorDrawingActivity.this.animateClick(view);
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    ScreenManualColorDrawingActivity.this.saveBitmap();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenManualColorDrawingActivity.this.animateClick(view);
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().clearFlags(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void savePageDialogOnBackPressed() {
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
            final Dialog dialog = new Dialog(this, R.style.AlertDialogCustom);
            dialog.getWindow().setFlags(8, 8);
            dialog.getWindow().getDecorView().setSystemUiVisibility(i3);
            dialog.setContentView(R.layout.dialog_save_delete);
            ((ConstraintLayout) dialog.findViewById(R.id.bg_dialog)).setLayoutParams(layoutParams);
            TextView textView = (TextView) dialog.findViewById(R.id.msg);
            ((ImageView) dialog.findViewById(R.id.picture)).setImageResource(R.drawable.ic_save_pic_bg);
            textView.setText(getString(R.string.store_picture_title));
            ((ImageView) dialog.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenManualColorDrawingActivity.this.animateClick(view);
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    ScreenManualColorDrawingActivity.this.saveBitmapOnBackPressed();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenManualColorDrawingActivity.this.animateClick(view);
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    ScreenManualColorDrawingActivity.this.finishActivityNoSave();
                }
            });
            dialog.show();
            dialog.getWindow().clearFlags(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setBottomColorLayout(List<ScreenInfo> list) {
        this.horizontalAdapter = new HorizontalAdapter(list, getApplication());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        this.horizontal_recycler_view.setLayoutManager(linearLayoutManager);
        this.horizontal_recycler_view.setAdapter(this.horizontalAdapter);
        linearLayoutManager.setStackFromEnd(true);
    }

    public List<ScreenInfo> setBottomColorsList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new ScreenInfo(1, R.drawable.c10_white, "Image 2", R.color.color25));
        arrayList.add(new ScreenInfo(1, R.drawable.c14_light_black, "Image 2", R.color.color26));
        arrayList.add(new ScreenInfo(1, R.drawable.c13_gray, "Image 2", R.color.grey));
        arrayList.add(new ScreenInfo(2, R.drawable.c9_grey2, "Image 1", R.color.color24));
        arrayList.add(new ScreenInfo(3, R.drawable.c8_brown2, "Image 2", R.color.color23));
        arrayList.add(new ScreenInfo(4, R.drawable.c8_brown1, "Image 1", R.color.color22));
        arrayList.add(new ScreenInfo(5, R.drawable.c8_brown0, "Image 1", R.color.color220));
        arrayList.add(new ScreenInfo(6, R.drawable.c7_pink3, "Image 3", R.color.color21));
        arrayList.add(new ScreenInfo(7, R.drawable.c7_pink2, "Image 2", R.color.color20));
        arrayList.add(new ScreenInfo(8, R.drawable.c7_pink1, "Image 1", R.color.color19));
        arrayList.add(new ScreenInfo(9, R.drawable.c6_purple3, "Image 3", R.color.color18));
        arrayList.add(new ScreenInfo(10, R.drawable.c6_purple2, "Image 2", R.color.color17));
        arrayList.add(new ScreenInfo(11, R.drawable.c6_purple1, "Image 1", R.color.color16));
        arrayList.add(new ScreenInfo(12, R.drawable.c5_blue4, "Image 3", R.color.color15));
        arrayList.add(new ScreenInfo(13, R.drawable.c5_blue3, "Image 2", R.color.color14));
        arrayList.add(new ScreenInfo(14, R.drawable.c5_blue2, "Image 1", R.color.color13));
        arrayList.add(new ScreenInfo(15, R.drawable.c5_blue1, "Image 3", R.color.color12));
        arrayList.add(new ScreenInfo(16, R.drawable.c5_blue0, "Image 3", R.color.color120));
        arrayList.add(new ScreenInfo(17, R.drawable.c4_green4, "Image 2", R.color.color11));
        arrayList.add(new ScreenInfo(18, R.drawable.c4_green3, "Image 1", R.color.color10));
        arrayList.add(new ScreenInfo(19, R.drawable.c4_green2, "Image 3", R.color.color9));
        arrayList.add(new ScreenInfo(20, R.drawable.c4_green1, "Image 2", R.color.color8));
        arrayList.add(new ScreenInfo(1, R.drawable.c12_skin, "Image 2", R.color.skin));
        arrayList.add(new ScreenInfo(21, R.drawable.c4_green0, "Image 2", R.color.color80));
        arrayList.add(new ScreenInfo(22, R.drawable.c3_yellow2, "Image 1", R.color.color7));
        arrayList.add(new ScreenInfo(23, R.drawable.c3_yellow1, "Image 3", R.color.color6));
        arrayList.add(new ScreenInfo(24, R.drawable.c3_yellow0, "Image 3", R.color.color60));
        arrayList.add(new ScreenInfo(25, R.drawable.c2_orange2, "Image 2", R.color.color5));
        arrayList.add(new ScreenInfo(26, R.drawable.c2_orange1, "Image 1", R.color.color4));
        arrayList.add(new ScreenInfo(27, R.drawable.c1_red3, "Image 3", R.color.color3));
        arrayList.add(new ScreenInfo(28, R.drawable.c1_red2, "Image 2", R.color.color2));
        arrayList.add(new ScreenInfo(29, R.drawable.c1_red1, "Image 2", R.color.color_new));
                arrayList.add(new ScreenInfo(3, R.drawable.c8_brown2, "Image 2", R.color.color23));
        arrayList.add(new ScreenInfo(4, R.drawable.c8_brown1, "Image 1", R.color.color22));

        arrayList.add(new ScreenInfo(31, R.drawable.c11_black, "Image 2", R.color.color27));
        arrayList.add(new ScreenInfo(30, R.drawable.picked_col_top, "Image 2", R.color.color2));
        arrayList.add(new ScreenInfo(30, R.drawable.col_picker, "Image 2", R.color.color2));
        return arrayList;
    }

    public List<ScreenInfo> setBottomGlitterList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new ScreenInfo(1, R.drawable.git_1, "g_1", R.color.color25));
        arrayList.add(new ScreenInfo(2, R.drawable.git_2, "g_2", R.color.color24));
        arrayList.add(new ScreenInfo(3, R.drawable.git_3, "g_3", R.color.color23));
        arrayList.add(new ScreenInfo(4, R.drawable.git_4, "g_4", R.color.color22));
        arrayList.add(new ScreenInfo(5, R.drawable.git_5, "g_5", R.color.color21));
        arrayList.add(new ScreenInfo(6, R.drawable.git_6, "g_6", R.color.color20));
        arrayList.add(new ScreenInfo(7, R.drawable.git_7, "g_7", R.color.color19));
        arrayList.add(new ScreenInfo(8, R.drawable.git_8, "g_8", R.color.color18));
        arrayList.add(new ScreenInfo(9, R.drawable.git_9, "g_9", R.color.color17));
        arrayList.add(new ScreenInfo(10, R.drawable.git_10, "g_10", R.color.color16));
        arrayList.add(new ScreenInfo(11, R.drawable.git_11, "g_11", R.color.color15));
        arrayList.add(new ScreenInfo(12, R.drawable.git_12, "g_12", R.color.color14));
        return arrayList;
    }

    public List<ScreenInfo> setBottomPatternList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new ScreenInfo(1, R.drawable.pat_1, "patt_1", R.color.color25));
        arrayList.add(new ScreenInfo(2, R.drawable.pat_2, "patt_2", R.color.color24));
        arrayList.add(new ScreenInfo(3, R.drawable.pat_3, "patt_3", R.color.color23));
        arrayList.add(new ScreenInfo(4, R.drawable.pat_4, "patt_4", R.color.color22));
        arrayList.add(new ScreenInfo(5, R.drawable.pat_5, "patt_5", R.color.color21));
        arrayList.add(new ScreenInfo(6, R.drawable.pat_6, "patt_6", R.color.color20));
        arrayList.add(new ScreenInfo(7, R.drawable.pat_7, "patt_7", R.color.color19));
        arrayList.add(new ScreenInfo(8, R.drawable.pat_8, "patt_8", R.color.color18));
        arrayList.add(new ScreenInfo(9, R.drawable.pat_9, "patt_9", R.color.color17));
        arrayList.add(new ScreenInfo(10, R.drawable.pat_10, "patt_10", R.color.color16));
        arrayList.add(new ScreenInfo(11, R.drawable.pat_11, "patt_11", R.color.color15));
        arrayList.add(new ScreenInfo(12, R.drawable.pat_12, "patt_12", R.color.color14));
        arrayList.add(new ScreenInfo(13, R.drawable.pat_13, "patt_13", R.color.color13));
        arrayList.add(new ScreenInfo(14, R.drawable.pat_14, "patt_14", R.color.color12));
        arrayList.add(new ScreenInfo(15, R.drawable.pat_15, "patt_15", R.color.color11));
        arrayList.add(new ScreenInfo(16, R.drawable.pat_16, "patt_16", R.color.color10));
        arrayList.add(new ScreenInfo(17, R.drawable.pat_17, "patt_17", R.color.color9));
        arrayList.add(new ScreenInfo(18, R.drawable.pat_18, "patt_18", R.color.color9));
        arrayList.add(new ScreenInfo(19, R.drawable.pat_19, "patt_19", R.color.color9));
        arrayList.add(new ScreenInfo(20, R.drawable.pat_20, "patt_20", R.color.color9));
        return arrayList;
    }

    public List<ScreenInfo> setBottomTextureList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new ScreenInfo(20, R.drawable.txt20, "txtr20", R.color.color23));
        arrayList.add(new ScreenInfo(19, R.drawable.txt19, "txtr19", R.color.color22));
        arrayList.add(new ScreenInfo(18, R.drawable.txt18, "txtr18", R.color.color220));
        arrayList.add(new ScreenInfo(17, R.drawable.txt17, "txtr17", R.color.color21));
        arrayList.add(new ScreenInfo(16, R.drawable.txt16, "txtr16", R.color.color20));
        arrayList.add(new ScreenInfo(15, R.drawable.txt15, "txtr15", R.color.color19));
        arrayList.add(new ScreenInfo(14, R.drawable.txt14, "txtr14", R.color.color18));
        arrayList.add(new ScreenInfo(13, R.drawable.txt13, "txtr13", R.color.color17));
        arrayList.add(new ScreenInfo(12, R.drawable.txt12, "txtr12", R.color.color16));
        arrayList.add(new ScreenInfo(11, R.drawable.txt11, "txtr11", R.color.color15));
        arrayList.add(new ScreenInfo(10, R.drawable.txt10, "txtr10", R.color.color14));
        arrayList.add(new ScreenInfo(9, R.drawable.txt9, "txtr9", R.color.color13));
        arrayList.add(new ScreenInfo(8, R.drawable.txt8, "txtr8", R.color.color12));
        arrayList.add(new ScreenInfo(7, R.drawable.txt7, "txtr7", R.color.color120));
        arrayList.add(new ScreenInfo(6, R.drawable.txt6, "txtr6", R.color.color11));
        arrayList.add(new ScreenInfo(5, R.drawable.txt5, "txtr5", R.color.color10));
        arrayList.add(new ScreenInfo(4, R.drawable.txt4, "txtr4", R.color.color9));
        arrayList.add(new ScreenInfo(3, R.drawable.txt3, "txtr3", R.color.color8));
        arrayList.add(new ScreenInfo(2, R.drawable.txt2, "txtr2", R.color.skin));
        arrayList.add(new ScreenInfo(1, R.drawable.txt1, "txtr1", R.color.color80));
        arrayList.add(new ScreenInfo(0, R.drawable.c10_white, "", R.color.color25));
        return arrayList;
    }

    public void setDefaultColor() {
        int color = ContextCompat.getColor(this, R.color.color_new);
        ScreenConstable.DRAW_COLOR = color;


        turnEraserToBrush();
        this.row_index = D(ScreenConstable.TYPE_FILL).size() - 3;
        this.horizontalAdapter.notifyDataSetChanged();
    }


    public void setUpColorPicker() {
        ColorPickerView colorPickerView = new ColorPickerView(this);
        this.colorPickerView = colorPickerView;
        colorPickerView.setInitialColor(this.ints[this.anInt1]);
        this.colorPickerView.subscribe(new CMYKObserver() {
            @Override
            public void onColor(int i, boolean z, boolean z2) {
                try {
                    ScreenManualColorDrawingActivity.this.anInt = i;
                    ScreenManualColorDrawingActivity.this.ints[ScreenManualColorDrawingActivity.this.anInt1] = i;
                    ((ImageView) ScreenManualColorDrawingActivity.this.linearLayout2.getChildAt(ScreenManualColorDrawingActivity.this.anInt1)).setColorFilter(ScreenManualColorDrawingActivity.this.ints[ScreenManualColorDrawingActivity.this.anInt1]);
                    ScreenManualColorDrawingActivity.this.isCol_picked = true;
                    ScreenManualColorDrawingActivity.this.horizontalAdapter.notifyDataSetChanged();
                    if (ScreenConstable.TYPE_FILL == 0 && ScreenManualColorDrawingActivity.this.row_index == ScreenManualColorDrawingActivity.this.horizontalAdapter.getItemCount() - 2) {
                        ScreenConstable.DRAW_COLOR = ScreenManualColorDrawingActivity.this.anInt;
                    }


                } catch (IndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
            }
        });
        this.linearLayout1.removeAllViews();
        this.linearLayout1.addView(this.colorPickerView);
        for (int i = 0; i < this.linearLayout2.getChildCount(); i++) {
            int finalI = i;
            this.linearLayout2.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ScreenManualColorDrawingActivity.this.animateClick(view);
                    ScreenManualColorDrawingActivity.this.mediaPlayer.playClickSound();
                    ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                    int i2 = finalI;
                    drawActivity2.anInt1 = i2;
                    drawActivity2.colorPickerView.setInitialColor(drawActivity2.ints[i2]);
                    ScreenManualColorDrawingActivity drawActivity3 = ScreenManualColorDrawingActivity.this;
                    int i3 = drawActivity3.ints[finalI];
                    drawActivity3.anInt = i3;
                    ScreenConstable.DRAW_COLOR = i3;
                    drawActivity3.horizontalAdapter.notifyDataSetChanged();
                    ScreenManualColorDrawingActivity.this.myDrawView.setPathColor(ScreenConstable.DRAW_COLOR);


                    int i4 = 0;
                    while (true) {
                        ScreenManualColorDrawingActivity drawActivity4 = ScreenManualColorDrawingActivity.this;
                        if (i4 < drawActivity4.ints.length) {
                            if (i4 == drawActivity4.anInt1) {
                                drawActivity4.linearLayout2.getChildAt(i4).setScaleX(0.9f);
                                ScreenManualColorDrawingActivity.this.linearLayout2.getChildAt(i4).setScaleY(0.9f);
                            } else {
                                drawActivity4.linearLayout2.getChildAt(i4).setScaleX(1.0f);
                                ScreenManualColorDrawingActivity.this.linearLayout2.getChildAt(i4).setScaleY(1.0f);
                            }
                            i4++;
                        } else {
                            return;
                        }
                    }
                }
            });
        }
    }

    public void turnEraserToBrush() {
        menuSelectedClick(1);
        ScreenConstable.SELECTED_TOOL = 1;
        ScreenConstable.strokeWidth = ScreenConstable.brushSize;
        ScreenConstable.erase = false;
    }

    public void zoom(Float f, Float f2, PointF pointF) {
        this.zoomFrame.setScaleX(f.floatValue());
        this.zoomFrame.setScaleY(f2.floatValue());
    }


    public class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.MyViewHolder> {
        List<ScreenInfo> a;
        Context b;

        public HorizontalAdapter(List<ScreenInfo> list, Application application) {
            Collections.emptyList();
            this.a = list;
            this.b = application;
        }

        private void isDefaultPosition(int i) {
            if (i == ScreenManualColorDrawingActivity.this.listItemDefaultPos) {
                ScreenManualColorDrawingActivity drawActivity = ScreenManualColorDrawingActivity.this;
                if (!drawActivity.aBoolean) {
                    drawActivity.brushSelectedOnClickButton();
                    if (ScreenConstable.erase) {
                        Toast.makeText(ScreenManualColorDrawingActivity.this, "called!", Toast.LENGTH_SHORT).show();
                        ScreenManualColorDrawingActivity.this.turnEraserToBrush();
                    }
                    ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                    if (drawActivity2.useTexture) {
                        drawActivity2.myDrawView.setTexture(this.a.get(i).getTxt());

                    } else if (ScreenManualColorDrawingActivity.ispatternClicked) {
                        drawActivity2.myDrawView.setPattern(this.a.get(i).getTxt());
                    } else {
                        if (i == getItemCount() - 2) {
                            ScreenConstable.DRAW_COLOR = ScreenManualColorDrawingActivity.this.anInt;
                        } else {
                            ScreenConstable.DRAW_COLOR = ContextCompat.getColor(this.b, this.a.get(i).anInt);
                        }
                        ScreenManualColorDrawingActivity.this.myDrawView.setPathColor(ScreenConstable.DRAW_COLOR);


                    }
                }
            }
        }

        private void setSelectedColorTick(MyViewHolder myViewHolder, int i) {
            if (ScreenManualColorDrawingActivity.this.listItemDefaultPos != -1) {
                if (ScreenManualColorDrawingActivity.this.row_index == ScreenManualColorDrawingActivity.this.listItemDefaultPos) {
                    myViewHolder.b.setVisibility(View.VISIBLE);
                    ScreenManualColorDrawingActivity.this.listItemDefaultPos = -1;
                } else {
                    myViewHolder.b.setVisibility(View.INVISIBLE);
                }
            } else if (ScreenManualColorDrawingActivity.this.row_index == i) {
                myViewHolder.b.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.b.setVisibility(View.INVISIBLE);
            }
            if (ScreenConstable.TYPE_FILL == 0 && i == getItemCount() - 1) {
                myViewHolder.b.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return this.a.size();
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
            myViewHolder.a.setImageResource(this.a.get(i).imageId);
            if (!ScreenManualColorDrawingActivity.this.useTexture && !ScreenManualColorDrawingActivity.ispatternClicked && i == getItemCount() - 2) {
                myViewHolder.a.setImageResource(R.drawable.picked_col_top);
                myViewHolder.c.setImageResource(R.drawable.picked_col_bg);
                myViewHolder.c.setColorFilter(ScreenManualColorDrawingActivity.this.anInt);
            }
            myViewHolder.a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ScreenManualColorDrawingActivity.this.linearLayout.getVisibility() == View.VISIBLE) {
                        ScreenManualColorDrawingActivity.this.linearLayout.setVisibility(View.GONE);
                    }
                    ScreenManualColorDrawingActivity drawActivity = ScreenManualColorDrawingActivity.this;
                    drawActivity.aBoolean = false;
                    drawActivity.mediaPlayer.playSound(R.raw.click);
                    ScreenManualColorDrawingActivity.this.brushSelectedOnClickButton();
                    ScreenManualColorDrawingActivity.this.row_index = i;
                    HorizontalAdapter.this.notifyDataSetChanged();
                    ScreenManualColorDrawingActivity drawActivity2 = ScreenManualColorDrawingActivity.this;
                    if (drawActivity2.useTexture) {
                        drawActivity2.myDrawView.setTexture(HorizontalAdapter.this.a.get(i).getTxt());

                        return;
                    }
                    drawActivity2.myDrawView.isDraw = true;
                    if (ScreenManualColorDrawingActivity.ispatternClicked) {
                        ScreenManualColorDrawingActivity.this.myDrawView.setPattern(HorizontalAdapter.this.a.get(i).getTxt());
                        return;
                    }
                    if (i == HorizontalAdapter.this.getItemCount() - 1) {
                        HorizontalAdapter horizontalAdapter = HorizontalAdapter.this;
                        ScreenManualColorDrawingActivity.this.row_index = horizontalAdapter.getItemCount() - 2;
                        HorizontalAdapter horizontalAdapter2 = HorizontalAdapter.this;
                        ScreenManualColorDrawingActivity drawActivity3 = ScreenManualColorDrawingActivity.this;
                        ScreenConstable.DRAW_COLOR = drawActivity3.anInt;
                        if (drawActivity3.aBoolean1) {
                            horizontalAdapter2.notifyDataSetChanged();
                            ScreenManualColorDrawingActivity.this.disableClick();
                            if (ScreenManualColorDrawingActivity.this.constraintLayout.getVisibility() == View.VISIBLE) {
                                ScreenManualColorDrawingActivity.this.closePicker();
                            } else {
                                ScreenManualColorDrawingActivity.this.closeAllDrawer();
                                ScreenManualColorDrawingActivity.this.openPicker();
                            }
                        }
                    } else if (i == HorizontalAdapter.this.getItemCount() - 2) {
                        ScreenManualColorDrawingActivity drawActivity4 = ScreenManualColorDrawingActivity.this;
                        ScreenConstable.DRAW_COLOR = drawActivity4.anInt;
                        drawActivity4.closePickerIfOpen();
                    } else {
                        HorizontalAdapter horizontalAdapter3 = HorizontalAdapter.this;
                        ScreenConstable.DRAW_COLOR = ContextCompat.getColor(horizontalAdapter3.b, horizontalAdapter3.a.get(i).anInt);
                        ScreenManualColorDrawingActivity.this.closePickerIfOpen();
                    }
                    ScreenManualColorDrawingActivity.this.myDrawView.setPathColor(ScreenConstable.DRAW_COLOR);


                }
            });
            isDefaultPosition(ScreenManualColorDrawingActivity.this.row_index);
            setSelectedColorTick(myViewHolder, i);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vertical_menu, viewGroup, false));
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView a;
            ImageView b;
            ImageView c;

            public MyViewHolder(HorizontalAdapter horizontalAdapter, View view) {
                super(view);
                this.a = (ImageView) view.findViewById(R.id.imageview);
                this.b = (ImageView) view.findViewById(R.id.imageviewTick);
                this.c = (ImageView) view.findViewById(R.id.color);
            }
        }
    }


}
