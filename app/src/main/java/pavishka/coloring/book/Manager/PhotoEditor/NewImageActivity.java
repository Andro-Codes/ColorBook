package pavishka.coloring.book.Manager.PhotoEditor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
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

import pavishka.coloring.book.Advertisement.Adshandler;
import pavishka.coloring.book.Activities.OtherScreens.SnapShotUtils;
import pavishka.coloring.book.Activities.Screens.ScreenManualDrawActivity;
import pavishka.coloring.book.Activities.Screens.ScreenInfo;
import pavishka.coloring.book.Activities.Screens.ScreenAudioPlayerActivity;
import pavishka.coloring.book.Activities.Screens.ScreenMyRevenueView;
import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenLanguage;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.OtherScreens.ScreenTempDataStore;
import pavishka.coloring.book.Activities.OtherScreens.ScreenThumbSticekrAdapter;
import pavishka.coloring.book.Activities.OtherScreens.ScreenSearchSeekBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NewImageActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "DrawActivity";
    public static NewImageActivity ImageEditorActivity = null;
    public static boolean ispatternClicked = false;
    public static ImageView iv;
    public static Bitmap myart;
    public static String pattern;
    private static float seekCosX;
    private static float seekCosY;
    public ScreenOwnAuidioPlayer mediaPlayer;
    ImageView imageView;
    FrameLayout frameLayout;
    LinearLayout linearLayout;
    ImageView back;
    RecyclerView recyclerView;
    ImageView chooseColortype;
    DrawerLayout drLayoutBrush;
    DrawerLayout drLayoutColor;
    View drawerViewBrush;
    View drawerViewColor;
    ScreenTempDataStore sharedPreference;
    ImageView eraser;
    HorizontalAdapter horizontalAdapter;
    RecyclerView horizontalRecyclerView;
    ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;
    ScreenMyRevenueView myAdView;
    EdiablePhoto myDrawView;
    ImageView newPage;
    ImageView pen;
    ImageView save;
    SeekBar seekbarHor;
    RelativeLayout seekbarLay;
    ScreenSearchSeekBar seekbarVar;
    boolean writePermission;
    ImageView zoom;
    FrameLayout zoomFrame;
    ArrayList<Integer> integers = new ArrayList<>();
    boolean isZoom = false;
    boolean isdraweropenedBrush = false;
    boolean isdraweropenedColor = false;
    int listItemDefaultPos = -1;
    int rowIndex = -1;
    boolean useTexture = false;

    public static NewImageActivity getImageEditorActivity() {
        return ImageEditorActivity;
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
        ((RelativeLayout) dialog.findViewById(R.id.parent)).setLayoutParams(layoutParams);
        ImageView button = (ImageView) dialog.findViewById(R.id.dialogbtn_close);
        ImageView button2 = (ImageView) dialog.findViewById(R.id.dialogbtn_retry);
        ((LinearLayout) dialog.findViewById(R.id.lay_permissions)).setVisibility(View.VISIBLE);
        if (i == 1) {
        } else if (i == 2) {
            button2.setVisibility(View.VISIBLE);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                mediaPlayer.playClickSound();
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                Toast.makeText(imageEditorActivity, imageEditorActivity.getString(R.string.longpress), Toast.LENGTH_SHORT).show();
            }
        });
        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                dialog.dismiss();
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                if (imageEditorActivity.sharedPreference.getStoragePermissionNever(imageEditorActivity)) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                    startActivityForResult(intent, 1111);
                    return true;
                }
                ActivityCompat.requestPermissions(NewImageActivity.this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PointerIconCompat.TYPE_CONTEXT_MENU);
                return true;
            }
        });
        dialog.show();
    }

    public void animateClicked(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.pop_in_low));
    }

    public void brushSelectedOnClickButton() {
        if (ScreenConstable.SELECTED_TOOL == 2) {
            menuSelectedClick(1);
            ScreenConstable.SELECTED_TOOL = 1;
            ScreenConstable.strokeWidth = ScreenConstable.brushSize;
            ScreenConstable.erase = false;
        }
        if (ScreenConstable.SELECTED_TOOL == 0 && ispatternClicked) {
            menuSelectedClick(1);
            ScreenConstable.SELECTED_TOOL = 1;
            ScreenConstable.strokeWidth = ScreenConstable.brushSize;
            ScreenConstable.erase = false;
        }
    }

    private void closeAllDrawer() {
        this.drLayoutColor.closeDrawer(this.drawerViewColor);
        this.drLayoutBrush.closeDrawer(this.drawerViewBrush);
        this.isdraweropenedColor = false;
        this.isdraweropenedBrush = false;
    }

    public void disableBrushDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.GONE);
        drawerLayout.setFocusable(false);
        drawerLayout.setClickable(false);
        drawerLayout.setEnabled(false);
    }

    public void disableColorDrawer(DrawerLayout drawerLayout) {
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
                drLayoutColor.closeDrawer(drawerViewColor);
                drLayoutBrush.closeDrawer(drawerViewBrush);
                isdraweropenedColor = false;
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
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.setBrushClick(imageEditorActivity.drLayoutBrush, drawerViewBrush, 20);
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.setBrushClick(imageEditorActivity.drLayoutBrush, drawerViewBrush, 30);
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.setBrushClick(imageEditorActivity.drLayoutBrush, drawerViewBrush, 40);
            }
        });
        this.drLayoutBrush.addDrawerListener(new ActionBarDrawerToggle(this, this.drLayoutBrush,  R.string.app_name, R.string.black_bat) {
            @Override

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mediaPlayer.playSound(R.raw.drawer_close);
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.disableBrushDrawer(imageEditorActivity.drLayoutBrush);
            }

            @Override

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                mediaPlayer.playSound(R.raw.drawer);
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.enableBrushDrawer(imageEditorActivity.drLayoutBrush);
            }
        });
    }

    private void drawerImplementationForColor() {
        this.drLayoutColor = (DrawerLayout) findViewById(R.id.dr_layout_color);
        this.drawerViewColor = findViewById(R.id.drawer_color);
        this.drLayoutColor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                drLayoutBrush.closeDrawer(drawerViewBrush);
                drLayoutColor.closeDrawer(drawerViewColor);
                isdraweropenedBrush = false;
                isdraweropenedColor = false;
                return true;
            }
        });
        View findViewById = findViewById(R.id.ivColorSize1);
        View findViewById2 = findViewById(R.id.ivColorSize2);
        View findViewById3 = findViewById(R.id.ivColorSize3);
        View findViewById4 = findViewById(R.id.ivColorSize4);
        this.drLayoutColor.closeDrawer(this.drawerViewColor);
        this.isdraweropenedColor = false;
        findViewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useTexture = false;
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.setFillType(imageEditorActivity.drLayoutColor, drawerViewColor, 0, false);
                ScreenConstable.TYPE_FILL = 0;
            }
        });
        findViewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useTexture = false;
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.setFillType(imageEditorActivity.drLayoutColor, drawerViewColor, 1, true);
                ScreenConstable.TYPE_FILL = 1;
            }
        });
        findViewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                useTexture = false;
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.setFillType(imageEditorActivity.drLayoutColor, drawerViewColor, 2, true);
                ScreenConstable.TYPE_FILL = 2;
            }
        });
        findViewById4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateClicked(view);
                useTexture = true;
                ScreenConstable.TYPE_FILL = 3;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        NewImageActivity imageEditorActivity = NewImageActivity.this;
                        imageEditorActivity.setFillType(imageEditorActivity.drLayoutColor, drawerViewColor, 3, false);
                    }
                }, 100L);
            }
        });
        this.drLayoutColor.addDrawerListener(new ActionBarDrawerToggle(this, this.drLayoutColor,  R.string.app_name, R.string.black_bat) {
            @Override

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                mediaPlayer.playSound(R.raw.drawer_close);
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.disableColorDrawer(imageEditorActivity.drLayoutColor);
            }

            @Override

            public void onDrawerOpened(View view) {
                super.onDrawerOpened(view);
                mediaPlayer.playSound(R.raw.drawer);
                NewImageActivity imageEditorActivity = NewImageActivity.this;
                imageEditorActivity.enableColorDrawer(imageEditorActivity.drLayoutColor);
            }
        });
    }

    public void enableBrushDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);
    }

    public void enableColorDrawer(DrawerLayout drawerLayout) {
        drawerLayout.setVisibility(View.VISIBLE);
        drawerLayout.setFocusable(true);
        drawerLayout.setClickable(true);
        drawerLayout.setEnabled(true);
    }

    private void findByViewIds() {
        this.imageView = (ImageView) findViewById(R.id.stickers);
        this.linearLayout = (LinearLayout) findViewById(R.id.lay_sticker);
        this.recyclerView = (RecyclerView) findViewById(R.id.st_recyclerview);
        this.zoom = (ImageView) findViewById(R.id.zoom);
        this.seekbarHor = (SeekBar) findViewById(R.id.seekbar_hor);
        this.seekbarVar = (ScreenSearchSeekBar) findViewById(R.id.seekbar_var);
        this.seekbarLay = (RelativeLayout) findViewById(R.id.seekbar_lay);
        this.zoomFrame = (FrameLayout) findViewById(R.id.zoomFrame);
        this.horizontalRecyclerView = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        this.myDrawView = (EdiablePhoto) findViewById(R.id.canvas_draw);
        this.pen = (ImageView) findViewById(R.id.pen);
        this.chooseColortype = (ImageView) findViewById(R.id.choose_colortype);
        this.frameLayout = (FrameLayout) findViewById(R.id.adViewTop);
        this.back = (ImageView) findViewById(R.id.back);
        this.eraser = (ImageView) findViewById(R.id.eraser);
        this.save = (ImageView) findViewById(R.id.save);
        this.newPage = (ImageView) findViewById(R.id.newPage);
        iv = (ImageView) findViewById(R.id.iv);
        this.pen.setOnClickListener(this);
        this.eraser.setOnClickListener(this);
        this.save.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.newPage.setOnClickListener(this);
        this.chooseColortype.setOnClickListener(this);
        this.back.setOnClickListener(this);
        this.zoom.setOnClickListener(this);
        this.imageView.setOnClickListener(this);
        this.seekbarHor.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                NewImageActivity imageEditorActivity = ImageEditorActivity.ImageEditorActivity;
                float unused = ImageEditorActivity.seekCosX = (zoomFrame.getWidth() * i) / 100;
                NewImageActivity imageEditorActivity2 = ImageEditorActivity.ImageEditorActivity;
                if (ImageEditorActivity.seekCosX < zoomFrame.getHeight()) {
                    zoomFrame.setPivotX((i * zoomFrame.getWidth()) / 100);
                    FrameLayout frameLayout = zoomFrame;
                    NewImageActivity imageEditorActivity3 = ImageEditorActivity.ImageEditorActivity;
                    frameLayout.setPivotY(ImageEditorActivity.seekCosY);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("sddd", "");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("sddd", "");
            }
        });
        this.seekbarVar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                NewImageActivity imageEditorActivity = ImageEditorActivity.ImageEditorActivity;
                float unused = ImageEditorActivity.seekCosY = (zoomFrame.getHeight() * i) / 100;
                NewImageActivity imageEditorActivity2 = ImageEditorActivity.ImageEditorActivity;
                if (ImageEditorActivity.seekCosY < zoomFrame.getHeight()) {
                    FrameLayout frameLayout = zoomFrame;
                    NewImageActivity imageEditorActivity3 = ImageEditorActivity.ImageEditorActivity;
                    frameLayout.setPivotX(ImageEditorActivity.seekCosX);
                    zoomFrame.setPivotY((i * zoomFrame.getHeight()) / 100);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.e("sddd", "");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.e("sddd", "");
            }
        });
    }

    public void finishActivityNoSave() {
        ScreenConstable.isBackFromDrawActivity = true;
        this.mediaPlayer.StopMp();
        this.mediaPlayer.playSound(R.raw.click);
        finish();
        Adshandler.showAd(this, new Adshandler.OnClose() {
            @Override
            public void click() {
            }
        });
    }

    private List<ScreenInfo> getFillTypeDate(int i) {
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
        this.writePermission = false;
        ispatternClicked = false;
    }

    private void initializeMediaPlayer() {
        ScreenOwnAuidioPlayer myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        this.mediaPlayer = myMediaPlayer;
        myMediaPlayer.playSelectArtRandomSound();
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = new ScreenAudioPlayerActivity();
        this.mediaPlayerSoundAndMusic = mediaPlayerSoundAndMusic;
        mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.coloring);
    }

    private void initializeOnSizeChangedValue() {
        ScreenConstable.onSizeCalled = 0;
    }

    private void isBrushSelected() {
        int i = ScreenConstable.TYPE_FILL;
        if (i == 1 || i == 2) {
            this.pen.setImageResource(R.drawable.menu1pencil_sel);
        }
    }

    public void launchCameraIntent() {
        Intent intent = new Intent(this, ScreenChoosePhotoActivity.class);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_IMAGE_PICKER_OPTION, 0);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_ASPECT_RATIO_X, ScreenConstable.ratioX);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_ASPECT_RATIO_Y, ScreenConstable.ratioY);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);
        startActivityForResult(intent, 100);
    }

    public void launchGalleryIntent() {
        Intent intent = new Intent(this, ScreenChoosePhotoActivity.class);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_IMAGE_PICKER_OPTION, 1);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_ASPECT_RATIO_X, ScreenConstable.ratioX);
        intent.putExtra(ScreenChoosePhotoActivity.INTENT_ASPECT_RATIO_Y, ScreenConstable.ratioY);
        startActivityForResult(intent, 100);
    }

    private void loadImage() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"}, 111);
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
        this.recyclerView.setAdapter(new ScreenThumbSticekrAdapter(this, this.integers, 1));
    }

    private void menuSelectedClick(int i) {
        if (i == 1) {
            this.pen.setImageResource(R.drawable.menu1pencil_sel);
        } else if (i == 2) {
            this.pen.setImageResource(R.drawable.menu1pencil);
        } else if (i == 7) {
            this.pen.setImageResource(R.drawable.menu1pencil);
        }
    }

    private void refreshData(List<ScreenInfo> list) {
        HorizontalAdapter horizontalAdapter = new HorizontalAdapter(list, getApplication());
        this.horizontalAdapter = horizontalAdapter;
        this.horizontalRecyclerView.setAdapter(horizontalAdapter);
        int itemCount = this.horizontalAdapter.getItemCount() - 1;
        this.listItemDefaultPos = itemCount;
        this.rowIndex = itemCount;
        this.horizontalAdapter.notifyDataSetChanged();
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
                ScreenConstable.isBackFromDrawActivity = false;
                SnapShotUtils.insertImage(this, getContentResolver(), this.myDrawView.getDrawingCache(), "drawing", "storage");
                this.mediaPlayer.playSound(R.raw.camera_click);
                myart = this.myDrawView.getDrawingCache();
            } catch (Exception unused) {
            }
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

    private void setBottomColorLayout(List<ScreenInfo> list) {
        this.horizontalAdapter = new HorizontalAdapter(list, getApplication());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, true);
        this.horizontalRecyclerView.setLayoutManager(linearLayoutManager);
        this.horizontalRecyclerView.setAdapter(this.horizontalAdapter);
        linearLayoutManager.setStackFromEnd(true);
    }

    public void setBrushClick(DrawerLayout drawerLayout, View view, int i) {
        ScreenConstable.SELECTED_TOOL = 1;
        drawerLayout.closeDrawer(view);
        this.mediaPlayer.playSound(R.raw.select);
        this.isdraweropenedBrush = false;
        ScreenConstable.brushSize = i;
        ScreenConstable.erase = false;
    }

    private void setDefaultColor() {
        int color = ContextCompat.getColor(this, R.color.color2);
        ScreenConstable.DRAW_COLOR = color;
        turnEraserToBrush();
        this.rowIndex = 27;
        this.horizontalAdapter.notifyDataSetChanged();
    }

    public void setFillType(DrawerLayout drawerLayout, View view, int i, boolean z) {
        this.myDrawView.isDraw = true;
        ispatternClicked = false;
        drawerLayout.closeDrawer(view);
        ScreenConstable.TYPE_FILL = i;
        this.isdraweropenedColor = false;
        ispatternClicked = z;
        refreshData(getFillTypeDate(i));
        isBrushSelected();
    }

    private void showImagePickerOptions() {
        ScreenChoosePhotoActivity.showImagePickerOptions(this, new ScreenChoosePhotoActivity.PickerOptionListener() {
            @Override

            public void onCancelSelected() {
                mediaPlayer.playSound(R.raw.click);
                finish();
                startActivity(new Intent(NewImageActivity.this, ScreenManualDrawActivity.class));
            }

            @Override

            public void onChooseGallerySelected() {
                mediaPlayer.playSound(R.raw.click);
                launchGalleryIntent();
            }

            @Override

            public void onTakeCameraSelected() {
                mediaPlayer.playSound(R.raw.click);
                launchCameraIntent();
            }
        });
    }

    public void addStickerToCanvas(int i) {
        this.myDrawView.addNewSticker(i);
        this.linearLayout.setVisibility(View.GONE);
    }


    public void insertBitmap() {
        if (ScreenConstable.fromGridActivityColoringBook) {
            ScreenConstable.fromGridActivityColoringBook = false;
        }
    }

    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100) {
            if (i2 == -1) {
                Uri uri = null;
                if (intent != null) {
                    uri = (Uri) intent.getParcelableExtra("path");
                }
                try {
                    this.myDrawView.setBackgroundDrawable(new BitmapDrawable(getResources(), MediaStore.Images.Media.getBitmap(getContentResolver(), uri)));
                    ScreenChoosePhotoActivity.clearCache(this);
                } catch (IOException e) {
                    Log.d("GALLERY_PICKER", "onActivityResult: " + e.toString());
                }
            } else {
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (this.linearLayout.getVisibility() == View.VISIBLE) {
            this.linearLayout.setVisibility(View.GONE);
        } else {
            savePageDialogOnBackPressed();
        }
    }

    @Override
    public void onClick(View view) {
        animateClicked(view);
        this.mediaPlayer.playSound(R.raw.click);
        switch (view.getId()) {
            case R.id.back :
                savePageDialogOnBackPressed();
                closeAllDrawer();
                return;
            case R.id.choose_colortype :
                enableColorDrawer(this.drLayoutColor);
                if (!this.isdraweropenedColor) {
                    this.drLayoutColor.openDrawer(this.drawerViewColor);
                    this.isdraweropenedColor = true;
                    return;
                }
                this.drLayoutColor.closeDrawer(this.drawerViewColor);
                this.isdraweropenedColor = false;
                return;
            case R.id.eraser :
                this.myDrawView.Undo();
                return;
            case R.id.newPage :
                this.myDrawView.Redo();
                return;
            case R.id.pen :
                this.useTexture = false;
                this.myDrawView.isDraw = true;
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
            case R.id.save :
                menuSelectedClick(4);
                savePageDialog();
                closeAllDrawer();
                return;
            case R.id.stickers :
                if (this.linearLayout.getVisibility() == View.VISIBLE) {
                    this.linearLayout.setVisibility(View.GONE);
                    return;
                } else {
                    this.linearLayout.setVisibility(View.VISIBLE);
                    return;
                }
            case R.id.zoom :
                this.seekbarHor.setProgress(0);
                this.seekbarVar.setProgress(0);
                if (this.isZoom) {
                    this.seekbarLay.setVisibility(View.GONE);
                    zoom(Float.valueOf(1.0f), Float.valueOf(1.0f), new PointF(0.0f, 0.0f));
                    this.isZoom = false;
                    this.zoom.setImageResource(R.drawable.zoom_in);
                    return;
                }
                this.seekbarLay.setVisibility(View.VISIBLE);
                zoom(Float.valueOf(2.0f), Float.valueOf(2.0f), new PointF(0.0f, 0.0f));
                this.isZoom = true;
                this.zoom.setImageResource(R.drawable.zoom_out);
                return;
            default:
                return;
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        requestWindowFeature(1);
        super.onCreate(bundle);
        getWindow().setFlags(1024, 1024);
        new ScreenLanguage().setUpLocale(this);
        if (this.sharedPreference == null) {
            this.sharedPreference = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        }
        ImageEditorActivity = this;
        this.useTexture = false;
        initialize();
        initializeOnSizeChangedValue();
        initializeMediaPlayer();
        setContentView(R.layout.activity_image_editor);
        findByViewIds();
        List<ScreenInfo> fillTypeDate = getFillTypeDate(0);
        setBottomColorLayout(fillTypeDate);
        drawerImplementationForBrush();
        drawerImplementationForColor();
        setDefaultColor();
        this.myDrawView.cleanUp();
        refreshData(fillTypeDate);
        loadImage();
        loadStickerToList();
        this.myDrawView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                myDrawView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                float width = myDrawView.getWidth() / 100.0f;
                float height = myDrawView.getHeight() / 100.0f;
                double round = Math.round(width);
                Double.isNaN(round);
                if (width > round + 0.5d) {
                    ScreenConstable.ratioX = Math.round(width) + 1;
                } else {
                    ScreenConstable.ratioX = Math.round(width);
                }
                double round2 = Math.round(height);
                Double.isNaN(round2);
                if (height > round2 + 0.5d) {
                    ScreenConstable.ratioY = Math.round(height) + 1;
                } else {
                    ScreenConstable.ratioY = Math.round(height);
                }
            }
        });
        this.myAdView = new ScreenMyRevenueView(this);
        setAd();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Bitmap bitmap = EdiablePhoto.canvasBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            EdiablePhoto.canvasBitmap = null;
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
//error
    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 111 && iArr.length > 0) {
            boolean z = iArr[0] == 0;
            boolean z2 = iArr[1] == 0;
            if (z && z2) {
                showImagePickerOptions();
            } else if (this.sharedPreference.getStoragePermissionNever(this)) {
                showdialogpermissions(2);
            } else {
                loadImage();
            }
            int length = strArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                String str = strArr[i2];
                if (iArr[i2] == -1 && (Build.VERSION.SDK_INT < 23 || !shouldShowRequestPermissionRationale(str))) {
                    this.sharedPreference.saveStoragePermissionNever(this, true);
                }
            }
        }
    }

    @Override
    protected void onResume() {
        hideNavigation();
        disableColorDrawer(this.drLayoutColor);
        disableBrushDrawer(this.drLayoutBrush);
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

    public void requestPermissionWrite() {
        if (Build.VERSION.SDK_INT < 23) {
            Log.v(TAG, "Permission is granted");
            this.writePermission = true;
        } else if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission is granted");
            this.writePermission = true;
        } else {
            Log.v(TAG, "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
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
            ((ImageView) dialog.findViewById(R.id.picture)).setImageResource(R.drawable.ic_save_pic_bg);
            ((ImageView) dialog.findViewById(R.id.yes)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateClicked(view);
                    mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    saveBitmap();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateClicked(view);
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
                    animateClicked(view);
                    mediaPlayer.playSound(R.raw.click);
                    dialog.dismiss();
                    saveBitmapOnBackPressed();
                }
            });
            ((ImageView) dialog.findViewById(R.id.no)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    animateClicked(view);
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
        arrayList.add(new ScreenInfo(1, R.drawable.c14_light_black, "Image 2", R.color.color26));
        arrayList.add(new ScreenInfo(2, R.drawable.c13_gray, "Image 1", R.color.grey));
        arrayList.add(new ScreenInfo(3, R.drawable.c9_grey2, "Image 1", R.color.color24));
        arrayList.add(new ScreenInfo(4, R.drawable.c8_brown2, "Image 2", R.color.color23));
        arrayList.add(new ScreenInfo(5, R.drawable.c8_brown1, "Image 1", R.color.color22));
        arrayList.add(new ScreenInfo(6, R.drawable.c8_brown0, "Image 1", R.color.color220));
        arrayList.add(new ScreenInfo(7, R.drawable.c7_pink3, "Image 3", R.color.color21));
        arrayList.add(new ScreenInfo(8, R.drawable.c7_pink2, "Image 2", R.color.color20));
        arrayList.add(new ScreenInfo(9, R.drawable.c7_pink1, "Image 1", R.color.color19));
        arrayList.add(new ScreenInfo(10, R.drawable.c6_purple3, "Image 3", R.color.color18));
        arrayList.add(new ScreenInfo(11, R.drawable.c6_purple2, "Image 2", R.color.color17));
        arrayList.add(new ScreenInfo(12, R.drawable.c6_purple1, "Image 1", R.color.color16));
        arrayList.add(new ScreenInfo(13, R.drawable.c5_blue4, "Image 3", R.color.color15));
        arrayList.add(new ScreenInfo(14, R.drawable.c5_blue3, "Image 2", R.color.color14));
        arrayList.add(new ScreenInfo(15, R.drawable.c5_blue2, "Image 1", R.color.color13));
        arrayList.add(new ScreenInfo(16, R.drawable.c5_blue1, "Image 3", R.color.color12));
        arrayList.add(new ScreenInfo(17, R.drawable.c5_blue0, "Image 3", R.color.color120));
        arrayList.add(new ScreenInfo(18, R.drawable.c4_green4, "Image 2", R.color.color11));
        arrayList.add(new ScreenInfo(19, R.drawable.c4_green3, "Image 1", R.color.color10));
        arrayList.add(new ScreenInfo(20, R.drawable.c4_green2, "Image 3", R.color.color9));
        arrayList.add(new ScreenInfo(21, R.drawable.c4_green1, "Image 2", R.color.color8));
        arrayList.add(new ScreenInfo(22, R.drawable.c12_skin, "Image 2", R.color.skin));
        arrayList.add(new ScreenInfo(23, R.drawable.c4_green0, "Image 2", R.color.color80));
        arrayList.add(new ScreenInfo(24, R.drawable.c3_yellow2, "Image 1", R.color.color7));
        arrayList.add(new ScreenInfo(25, R.drawable.c3_yellow1, "Image 3", R.color.color6));
        arrayList.add(new ScreenInfo(26, R.drawable.c3_yellow0, "Image 3", R.color.color60));
        arrayList.add(new ScreenInfo(27, R.drawable.c2_orange2, "Image 2", R.color.color5));
        arrayList.add(new ScreenInfo(28, R.drawable.c2_orange1, "Image 1", R.color.color4));
        arrayList.add(new ScreenInfo(29, R.drawable.c1_red3, "Image 3", R.color.color3));
        arrayList.add(new ScreenInfo(30, R.drawable.c1_red2, "Image 2", R.color.color2));
                arrayList.add(new ScreenInfo(3, R.drawable.c8_brown2, "Image 2", R.color.color23));
        arrayList.add(new ScreenInfo(4, R.drawable.c8_brown1, "Image 1", R.color.color22));

        arrayList.add(new ScreenInfo(31, R.drawable.c11_black, "Image 2", R.color.color27));
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
        Collections.reverse(arrayList);
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
        Collections.reverse(arrayList);
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

    public void turnEraserToBrush() {
        menuSelectedClick(1);
        ScreenConstable.SELECTED_TOOL = 1;
        ScreenConstable.strokeWidth = ScreenConstable.brushSize;
        ScreenConstable.erase = false;
    }

    public void zoom(Float f, Float f2, PointF pointF) {
        this.zoomFrame.setPivotX(pointF.x);
        this.zoomFrame.setPivotY(pointF.y);
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
            if (i == listItemDefaultPos) {
                brushSelectedOnClickButton();
                if (ScreenConstable.erase) {
                    turnEraserToBrush();
                }
                if (useTexture) {
                    myDrawView.setTexture(this.a.get(i).getTxt());
                    return;
                }
                NewImageActivity imageEditorActivity = ImageEditorActivity.ImageEditorActivity;
                if (ImageEditorActivity.ispatternClicked) {
                    myDrawView.setPattern(this.a.get(i).getTxt());
                    NewImageActivity imageEditorActivity2 = ImageEditorActivity.ImageEditorActivity;
                    ImageEditorActivity.pattern = this.a.get(i).getTxt();
                    return;
                }
                ScreenConstable.DRAW_COLOR = ContextCompat.getColor(this.b, this.a.get(i).getColorId());
                myDrawView.setPathColor(ScreenConstable.DRAW_COLOR);
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
                    if (linearLayout.getVisibility() == View.VISIBLE) {
                        linearLayout.setVisibility(View.GONE);
                    }
                    myDrawView.isDraw = true;
                    mediaPlayer.playSound(R.raw.click);
                    brushSelectedOnClickButton();
                    rowIndex = i;
                    HorizontalAdapter.this.notifyDataSetChanged();
                    if (useTexture) {
                        myDrawView.setTexture(HorizontalAdapter.this.a.get(i).getTxt());
                        return;
                    }
                    NewImageActivity imageEditorActivity = ImageEditorActivity.ImageEditorActivity;
                    if (ImageEditorActivity.ispatternClicked) {
                        myDrawView.setPattern(HorizontalAdapter.this.a.get(i).getTxt());
                        NewImageActivity imageEditorActivity2 = ImageEditorActivity.ImageEditorActivity;
                        ImageEditorActivity.pattern = HorizontalAdapter.this.a.get(i).getTxt();
                        return;
                    }
                    HorizontalAdapter horizontalAdapter = HorizontalAdapter.this;
                    ScreenConstable.DRAW_COLOR = ContextCompat.getColor(horizontalAdapter.b, horizontalAdapter.a.get(i).getColorId());
                    myDrawView.setPathColor(ScreenConstable.DRAW_COLOR);
                }
            });
            isDefaultPosition(rowIndex);
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


    class paintClass1 implements DialogInterface.OnClickListener {
        final int a;

        paintClass1(int i) {
            this.a = i;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            saveBitmap();
            if (this.a < 0) {
                myDrawView.startNew();
            }
        }
    }


    class paintClass2 implements DialogInterface.OnClickListener {
        final int a;

        paintClass2(int i) {
            this.a = i;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            if (this.a < 0) {
                myDrawView.startNew();
            }
        }
    }
}
