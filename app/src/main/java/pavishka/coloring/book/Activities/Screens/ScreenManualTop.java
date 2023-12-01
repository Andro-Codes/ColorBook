package pavishka.coloring.book.Activities.Screens;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.PointerIconCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pavishka.coloring.book.Activities.OtherScreens.ScreenSkillActivity;
import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;
import pavishka.coloring.book.Activities.OtherScreens.SnapShotUtils;
import pavishka.coloring.book.R;
import pavishka.coloring.book.Advertisement.Adshandler;


public class ScreenManualTop extends ScreenPillarActivity implements View.OnClickListener {
    private static final String TAG = "DrawActivity";
    public static ScreenManualTop drawActivityGlow;
    public static ImageView iv;
    public static Bitmap myart;
    public static int newHeight;
    public static int newWidth;
    public ScreenOwnAuidioPlayer mediaPlayer;
    ScreenTempDataStore c;
    private FrameLayout ad;
    private ImageView back;
    private ImageView dashedPen;
    private ImageView dottedPen;
    private DrawerLayout drLayoutBrush;
    private View drawerViewBrush;
    private ImageView eraser;
    private HorizontalAdapter horizontalAdapter;
    private RecyclerView horizontalRecyclerView;
    private ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;
    private ScreenDrawFuncAloe myDrawView;
    private ImageView newpage;
    private ImageView pen;
    private ImageView save;
    private boolean writePermission;
    private boolean isdraweropenedBrush = false;
    private int listItemDefaultPos = -1;
    private int rowIndex = -1;

    public static ScreenManualTop getDrawActivity() {
        return drawActivityGlow;
    }

    private void showdialogpermissions(int i) {
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
        TextView textView = (TextView) dialog.findViewById(R.id.msg);
        ((RelativeLayout) dialog.findViewById(R.id.parent)).setLayoutParams(layoutParams);
        ImageView button = (ImageView) dialog.findViewById(R.id.dialogbtn_close);
        ImageView button2 = (ImageView) dialog.findViewById(R.id.dialogbtn_retry);
        ((LinearLayout) dialog.findViewById(R.id.lay_permissions)).setVisibility(View.VISIBLE);
        if (i == 1) {
            textView.setText(getString(R.string.msg_save_coloring_pages));
        } else if (i == 2) {
            textView.setText(getString(R.string.msg_save_coloring_pages2));
            button2.setVisibility(View.INVISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                mediaPlayer.playClickSound();
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
                animateClick(view);
                mediaPlayer.playClickSound();
                ScreenManualTop drawActivityGlow2 = ScreenManualTop.this;
                Toast.makeText(drawActivityGlow2, drawActivityGlow2.getString(R.string.longpress), Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialog.dismiss();
                ScreenManualTop drawActivityGlow2 = ScreenManualTop.this;
                if (drawActivityGlow2.c.getStoragePermissionNever(drawActivityGlow2)) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivityForResult(intent, 1111);
                    return true;
                }
                ActivityCompat.requestPermissions(ScreenManualTop.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PointerIconCompat.TYPE_CONTEXT_MENU);
                return true;
            }
        });
        dialog.show();
    }

    public void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    public void brushSelectedOnClickButton() {
        if (ScreenConstable.SELECTED_TOOL == 2) {
            menuSelectedClick(2);
            ScreenConstable.SELECTED_TOOL = 1;
            ScreenConstable.strokeWidth = ScreenConstable.brushSize;
            ScreenConstable.erase = false;
        }
    }

    public void checkTypeOfPenSelected() {
        if (ScreenConstable.anInt8 == 1) {
            menuSelectedClick(1);
        }
        if (ScreenConstable.anInt8 == 3) {
            menuSelectedClick(8);
        }
        if (ScreenConstable.anInt8 == 2) {
            menuSelectedClick(9);
        }
    }

    private void chooseDrawingType() {
        if (ScreenConstable.anInt5 == 8) {
            ScreenConstable.selected_bitmapIds = ScreenConstable.integers13;
        }
    }

    private void closeAllDrawer() {
        this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedBrush = false;
    }

    public void disableBrushDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.GONE);
        drawerLayout.setFocusable(false);
        drawerLayout.setClickable(false);
        drawerLayout.setEnabled(false);
    }

    private void drawerImplementationForBrush() {
        this.drLayoutBrush = (DrawerLayout) findViewById(R.id.dr_layout_brush);
        this.drawerViewBrush = findViewById(R.id.drawer_brush);
        this.drLayoutBrush.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                drLayoutBrush.closeDrawer(drawerViewBrush);
                isdraweropenedBrush = false;
                return true;
            }
        });
        View findViewById = findViewById(R.id.ivBrushSize1);
        View findViewById2 = findViewById(R.id.ivBrushSize2);
        View findViewById3 = findViewById(R.id.ivBrushSize3);
        this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedBrush = false;
        findViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualTop drawActivityGlow2 = ScreenManualTop.this;
                        drawActivityGlow2.setBrushClick(drawActivityGlow2.drLayoutBrush, drawerViewBrush, 20, 5);
                    }
                }, 100L);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualTop drawActivityGlow2 = ScreenManualTop.this;
                        drawActivityGlow2.setBrushClick(drawActivityGlow2.drLayoutBrush, drawerViewBrush, 30, 8);
                    }
                }, 100L);
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClick(view);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenManualTop drawActivityGlow2 = ScreenManualTop.this;
                        drawActivityGlow2.setBrushClick(drawActivityGlow2.drLayoutBrush, drawerViewBrush, 40, 10);
                    }
                }, 100L);
            }
        });
        this.drLayoutBrush.addDrawerListener(new ActionBarDrawerToggle(this, this.drLayoutBrush, R.string.app_name, R.string.black_bat) {
            @Override

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mediaPlayer.playSound(R.raw.drawer_close);
                ScreenManualTop drawActivityGlow2 = ScreenManualTop.this;
                drawActivityGlow2.disableBrushDrawer(drawActivityGlow2.drLayoutBrush);
            }

            @Override

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                mediaPlayer.playSound(R.raw.drawer);
                ScreenManualTop drawActivityGlow2 = ScreenManualTop.this;
                drawActivityGlow2.enableBrushDrawer(drawActivityGlow2.drLayoutBrush);
            }
        });
    }

    public void enableBrushDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);
    }

    private void findByViewIds() {
        this.horizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        this.myDrawView = (ScreenDrawFuncAloe) findViewById(R.id.draw);
        this.pen = (ImageView) findViewById(R.id.pen);
        this.dottedPen = (ImageView) findViewById(R.id.dotted_pen);
        this.dashedPen = (ImageView) findViewById(R.id.dashed_pen);
        this.ad = (FrameLayout) findViewById(R.id.adViewTop);
        this.back = (ImageView) findViewById(R.id.back);
        this.eraser = (ImageView) findViewById(R.id.eraser);
        this.save = (ImageView) findViewById(R.id.save);
        this.newpage = (ImageView) findViewById(R.id.newPage);
        iv = (ImageView) findViewById(R.id.iv);
        this.pen.setOnClickListener(this);
        this.dottedPen.setOnClickListener(this);
        this.dashedPen.setOnClickListener(this);
        this.eraser.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.newpage.setOnClickListener(this);
        this.back.setOnClickListener(this);
    }

    private void finishActivity() {
        if (SnapShotUtils.file.getAbsolutePath() != null) {
            ScreenConstable.isBackFromDrawActivity = true;
            this.mediaPlayer.StopMp();
            this.mediaPlayer.playSound(R.raw.click);
            finish();
            Intent intent = new Intent(this, ScreenSkillActivity.class);
            intent.putExtra("BitmapImage", SnapShotUtils.file.getAbsolutePath());
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            finish();
            return;
        }
        finishActivityNoSave();
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

    private void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    private void initialize() {
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        ScreenConstable.heightInPixels = defaultDisplay.getHeight();
        ScreenConstable.widthInPixels = defaultDisplay.getWidth();
        double d = ScreenConstable.heightInPixels;
        double d2 = ScreenConstable.widthInPixels;
        Double.isNaN(d);
        Double.isNaN(d2);
        ScreenConstable.screenRatio = d / d2;
        ScreenConstable.erase = false;
        ScreenConstable.SELECTED_TOOL = -1;
        ScreenConstable.anInt8 = 1;
        ScreenConstable.anInt14 = 5;
        this.writePermission = false;
    }

    private void initializeMediaPlayer() {
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = new ScreenAudioPlayerActivity();
        this.mediaPlayerSoundAndMusic = mediaPlayerSoundAndMusic;
        mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.coloring);
        ScreenOwnAuidioPlayer myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        this.mediaPlayer = myMediaPlayer;
        myMediaPlayer.playSelectArtRandomSound();
    }

    private void initializeOnSizeChangedValue() {
        ScreenConstable.onSizeCalled = 0;
    }

    private void menuSelectedClick(int i) {
        if (i == 1) {
            this.pen.setImageResource(R.drawable.glow_pen_plain_sel);
            this.eraser.setImageResource(R.drawable.menu4eraser);
            this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
            this.dashedPen.setImageResource(R.drawable.glow_pen_dashed);
        } else if (i == 2) {
            this.pen.setImageResource(R.drawable.glow_pen_plain);
            this.eraser.setImageResource(R.drawable.menu4eraser);
            this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
            this.dashedPen.setImageResource(R.drawable.glow_pen_dashed);
        } else if (i == 3) {
            this.pen.setImageResource(R.drawable.glow_pen_plain);
            this.eraser.setImageResource(R.drawable.menu4eraser_sel);
            this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
            this.dashedPen.setImageResource(R.drawable.glow_pen_dashed);
        } else if (i == 7) {
            this.pen.setImageResource(R.drawable.glow_pen_plain);
            this.eraser.setImageResource(R.drawable.menu4eraser);
            this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
            this.dashedPen.setImageResource(R.drawable.glow_pen_dashed);
        } else if (i == 8) {
            this.pen.setImageResource(R.drawable.glow_pen_plain);
            this.eraser.setImageResource(R.drawable.menu4eraser);
            this.dottedPen.setImageResource(R.drawable.glow_pen_dotted);
            this.dashedPen.setImageResource(R.drawable.glow_pen_dashed_sel);
        } else if (i == 9) {
            this.pen.setImageResource(R.drawable.glow_pen_plain);
            this.eraser.setImageResource(R.drawable.menu4eraser);
            this.dottedPen.setImageResource(R.drawable.glow_pen_dotted_sel);
            this.dashedPen.setImageResource(R.drawable.glow_pen_dashed);
        }
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

    private void setBottomColorLayout(List<ScreenInfo> list) {
        this.horizontalAdapter = new HorizontalAdapter(list, getApplication());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        this.horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        this.horizontalRecyclerView.setAdapter(this.horizontalAdapter);
        linearLayoutManager.setStackFromEnd(true);
    }

    public void setBrushClick(DrawerLayout drawerLayout, View view, int i, int i2) {
        ScreenConstable.SELECTED_TOOL = 1;
        ScreenConstable.anInt14 = i2;
        this.mediaPlayer.playSound(R.raw.select);
        drawerLayout.closeDrawer(view);
        this.isdraweropenedBrush = false;
        ScreenConstable.brushSize = i;
        ScreenConstable.erase = false;
    }

    private void setDefaultColor() {
        int color = ContextCompat.getColor(this, R.color.color2);
        ScreenConstable.DRAW_COLOR = color;
        turnEraserToBrush();
        this.rowIndex = 28;
        this.horizontalAdapter.notifyDataSetChanged();
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
                    animateClick(view);
                    mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    myDrawView.startNew();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateClick(view);
                    mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                }
            });
            dialog.show();
            dialog.getWindow().clearFlags(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                ScreenDrawFuncAloe drawingPictureGlow = this.myDrawView;
                drawingPictureGlow.kidBitmap = Bitmap.createScaledBitmap(drawingPictureGlow.kidBitmap, newWidth, i, true);
                if (ScreenConstable.selectedTool == -1) {
                    ScreenConstable.selectedTool = 1;
                }
            }
            ScreenConstable.SELECTED_TOOL = 0;
            ScreenConstable.strokeWidth = 0;
            ScreenConstable.erase = false;
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        ScreenConstable.isBackFromGlow = true;
        savePageDialogOnBackPressed();
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        this.mediaPlayer.playSound(R.raw.click);
        switch (view.getId()) {
            case R.id.back:
                ScreenConstable.isBackFromGlow = true;
                savePageDialogOnBackPressed();
                closeAllDrawer();
                return;
            case R.id.dashed_pen:
                ScreenConstable.anInt8 = 3;
                ScreenConstable.SELECTED_TOOL = 1;
                ScreenConstable.erase = false;
                menuSelectedClick(8);
                enableBrushDrawer(this.drLayoutBrush);
                if (!this.isdraweropenedBrush) {
                    this.drLayoutBrush.openDrawer(this.drawerViewBrush);
                    this.isdraweropenedBrush = true;
                    return;
                }
                this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
                this.isdraweropenedBrush = false;
                return;
            case R.id.dotted_pen:
                ScreenConstable.anInt8 = 2;
                ScreenConstable.SELECTED_TOOL = 1;
                ScreenConstable.erase = false;
                menuSelectedClick(9);
                enableBrushDrawer(this.drLayoutBrush);
                if (!this.isdraweropenedBrush) {
                    this.drLayoutBrush.openDrawer(this.drawerViewBrush);
                    this.isdraweropenedBrush = true;
                    return;
                }
                this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
                this.isdraweropenedBrush = false;
                return;
            case R.id.eraser:
                menuSelectedClick(3);
                ScreenConstable.SELECTED_TOOL = 2;
                ScreenConstable.strokeWidth = ScreenConstable.eraseWidth;
                ScreenConstable.erase = true;
                closeAllDrawer();
                return;
            case R.id.newPage:
                menuSelectedClick(5);
                createNewPageDialog(-1);
                setDefaultColor();
                closeAllDrawer();
                return;
            case R.id.pen:
                ScreenConstable.anInt8 = 1;
                ScreenConstable.SELECTED_TOOL = 1;
                ScreenConstable.erase = false;
                menuSelectedClick(1);
                enableBrushDrawer(this.drLayoutBrush);
                if (!this.isdraweropenedBrush) {
                    this.drLayoutBrush.openDrawer(this.drawerViewBrush);
                    this.isdraweropenedBrush = true;
                    return;
                }
                this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
                this.isdraweropenedBrush = false;
                return;
            case R.id.save:
                savePageDialog();
                closeAllDrawer();
                return;
            default:
                return;
        }
    }


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        System.err.print("glow 1");
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (this.c == null) {
            this.c = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        }
        drawActivityGlow = this;
        initialize();
        initializeOnSizeChangedValue();
        initializeMediaPlayer();
        new ScreenLanguage().setUpLocale(this);
        setContentView(R.layout.activity_draw_glow);
        findByViewIds();
        setAd(this.ad);
        setBottomColorLayout(setBottomColorsList());
        drawerImplementationForBrush();
        chooseDrawingType();
        setDefaultColor();
        checkTypeOfPenSelected();
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
        }
    }

    @Override
    public void onPause() {
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
                    this.c.saveStoragePermissionNever(this, true);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        hideNavigation();
        disableBrushDrawer(this.drLayoutBrush);
        this.mediaPlayerSoundAndMusic.startMainMusic();
        super.onResume();
        if (ScreenTempDataStore.getBuyChoise(this) > 0) {
            this.ad.setVisibility(View.GONE);
        }
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

    public void requestPermissionWrite() {
        if (Build.VERSION.SDK_INT < 23) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PointerIconCompat.TYPE_CONTEXT_MENU);
            Log.v(TAG, "Permission is granted");
            this.writePermission = true;
        } else if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            this.writePermission = true;
            Log.v(TAG, "Permission is granted");
        } else {
            if (this.c.getStoragePermissionNever(this)) {
                showdialogpermissions(2);
            } else {
                showdialogpermissions(1);
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
                    animateClick(view);
                    mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    saveBitmap();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateClick(view);
                    mediaPlayer.playSound(R.raw.click);
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
                    animateClick(view);
                    mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    saveBitmapOnBackPressed();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateClick(view);
                    mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    finishActivityNoSave();
                }
            });
            dialog.show();
            dialog.getWindow().clearFlags(8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<ScreenInfo> setBottomColorsList() {
        ArrayList arrayList = new ArrayList();
        arrayList.clear();
        arrayList.add(new ScreenInfo(1, R.drawable.c10_white, "Image 2", R.color.color25));
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
        arrayList.add(new ScreenInfo(21, R.drawable.c4_green0, "Image 2", R.color.color80));
        arrayList.add(new ScreenInfo(22, R.drawable.c3_yellow2, "Image 1", R.color.color7));
        arrayList.add(new ScreenInfo(23, R.drawable.c3_yellow1, "Image 3", R.color.color6));
        arrayList.add(new ScreenInfo(24, R.drawable.c3_yellow0, "Image 3", R.color.color60));
        arrayList.add(new ScreenInfo(25, R.drawable.c2_orange2, "Image 2", R.color.color5));
        arrayList.add(new ScreenInfo(26, R.drawable.c2_orange1, "Image 1", R.color.color4));
        arrayList.add(new ScreenInfo(27, R.drawable.c1_red3, "Image 3", R.color.color3));
        arrayList.add(new ScreenInfo(28, R.drawable.c1_red2, "Image 2", R.color.color2));
                arrayList.add(new ScreenInfo(3, R.drawable.c8_brown2, "Image 2", R.color.color23));
        arrayList.add(new ScreenInfo(4, R.drawable.c8_brown1, "Image 1", R.color.color22));

        arrayList.add(new ScreenInfo(31, R.drawable.c11_black, "Image 2", R.color.color27));
        arrayList.add(new ScreenInfo(29, R.drawable.rainbow_color, "Image 2", R.color.color2));
        return arrayList;
    }


    public void turnEraserToBrush() {
        ScreenConstable.SELECTED_TOOL = 1;
        ScreenConstable.strokeWidth = ScreenConstable.brushSize;
        ScreenConstable.erase = false;
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
            ScreenConstable.aBoolean1 = i == 28;
            if (i == listItemDefaultPos) {
                brushSelectedOnClickButton();
                if (ScreenConstable.erase) {
                    turnEraserToBrush();
                }
                ScreenConstable.DRAW_COLOR = ContextCompat.getColor(this.b, this.a.get(i).anInt);
            }
        }

        private void setSelectedColorTick(MyViewHolder myViewHolder, int i) {
            if (listItemDefaultPos != -1) {
                if (rowIndex == listItemDefaultPos) {
                    myViewHolder.b.setVisibility(View.VISIBLE);
                    listItemDefaultPos = -1;
                    return;
                }
                myViewHolder.b.setVisibility(View.INVISIBLE);
            } else if (rowIndex == i) {
                myViewHolder.b.setVisibility(View.VISIBLE);
            } else {
                myViewHolder.b.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public int getItemCount() {
            return this.a.size();
        }

        public void onBindViewHolder(MyViewHolder myViewHolder, @SuppressLint("RecyclerView") final int i) {
            myViewHolder.a.setImageResource(this.a.get(i).imageId);
            myViewHolder.a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    brushSelectedOnClickButton();
                    rowIndex = i;
                    ScreenConstable.aBoolean1 = rowIndex == 28;
                    HorizontalAdapter.this.notifyDataSetChanged();
                    HorizontalAdapter horizontalAdapter = HorizontalAdapter.this;
                    ScreenConstable.DRAW_COLOR = ContextCompat.getColor(horizontalAdapter.b, horizontalAdapter.a.get(i).anInt);
                    checkTypeOfPenSelected();
                }
            });
            isDefaultPosition(rowIndex);
            PrintStream printStream = System.err;
            printStream.println("def pos::" + rowIndex);
            setSelectedColorTick(myViewHolder, i);
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new MyViewHolder(this, LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.vertical_menu, viewGroup, false));
        }


        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView a;
            ImageView b;

            public MyViewHolder(HorizontalAdapter horizontalAdapter, View view) {
                super(view);
                this.a = (ImageView) view.findViewById(R.id.imageview);
                this.b = (ImageView) view.findViewById(R.id.imageviewTick);
            }
        }
    }


}
