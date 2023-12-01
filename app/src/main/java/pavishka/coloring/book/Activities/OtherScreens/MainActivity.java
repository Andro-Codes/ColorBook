package pavishka.coloring.book.Activities.OtherScreens;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.view.PointerIconCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnFailureListener;
import com.google.android.play.core.tasks.OnSuccessListener;

import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.Screens.ScreenAudioPlayerActivity;
import pavishka.coloring.book.Activities.Screens.ScreenBabyFunActivity;
import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenFillColorActivity;
import pavishka.coloring.book.Activities.Screens.ScreenItemsActivity;
import pavishka.coloring.book.Activities.Screens.ScreenLanguage;
import pavishka.coloring.book.Activities.Screens.ScreenManualDrawActivity;
import pavishka.coloring.book.Activities.Screens.ScreenMyRevenueView;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;
import pavishka.coloring.book.Activities.Screens.ScreenSetting;
import pavishka.coloring.book.Advertisement.Adshandler;
import pavishka.coloring.book.VectorArt.ScreenCardingPixbasedActivity;
import pavishka.coloring.book.Utilities.AlertAlarm;


public class MainActivity extends Activity implements View.OnClickListener, View.OnLongClickListener {
    public static String getImageName;
    public static Boolean isBuy = Boolean.FALSE;
    public static ScreenTempDataStore sharedPreference;
    public static ScreenTempDataStore sharedpreferenceIsshownewapp;
    public static ScreenTempDataStore sharedpreferenceNever;
    public boolean isRateDialogeShow = false;
    SharedPreferences sharedPreferences;
    Intent intent;
    ScreenOwnAuidioPlayer myMediaPlayer;
    ImageView coloringBook;
    ImageView customPaint;
    ScreenTempDataStore sharedPreference1;
    ScreenTempDataStore sharedPreference2;
    ScreenTempDataStore sharedPreference3;
    ImageView kidsGame;
    BroadcastReceiver mRegistrationBroadcastReceiver;
    ScreenAudioPlayerActivity mediaPlayerSoundAndMusic;
    MediaPlayer mp;
    ImageView myWork;
    ImageView pixelArt;
    ImageView settings;
    AppUpdateManager aNull = null;
    InstallStateUpdatedListener j = null;
    private ImageView back;
    private int requestUpdate = 1111;

    private void showdialogFirstTime(boolean z) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        int i2 = displayMetrics.widthPixels;
        int i3 = i - (i / 3);
        int i4 = i2 - (i2 / 7);
        int i5 = Build.VERSION.SDK_INT >= 19 ? 5894 : 0;
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.height = i3;
        layoutParams.width = i4;
        final Dialog dialog = new Dialog(this, R.style.AlertDialogCustom);
        dialog.requestWindowFeature(1);
        dialog.getWindow().setFlags(8, 8);
        dialog.getWindow().getDecorView().setSystemUiVisibility(i5);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.first_time_dialog);
        ((RelativeLayout) dialog.findViewById(R.id.parent)).setLayoutParams(layoutParams);
        LinearLayout linearLayout2 = (LinearLayout) dialog.findViewById(R.id.lay_permissions);
        ImageView button = (ImageView) dialog.findViewById(R.id.dialogbtn_close);
        ImageView button2 = (ImageView) dialog.findViewById(R.id.dialogbtn_retry);
        linearLayout2.setVisibility(View.VISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.animateClick(view);
                MainActivity.this.myMediaPlayer.playClickSound();
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
                MainActivity.this.animateClick(view);
                MainActivity.this.myMediaPlayer.playClickSound();
                MainActivity.sharedPreference.saveSettingFirstTime(MainActivity.this, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        MainActivity.this.requestPermissionWrite();
                    }
                }, 100L);
            }
        });
        dialog.show();
        dialog.getWindow().clearFlags(8);
    }

    public void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }


    private void checkMoreAppshow() {
        if (isNetworkAvailable()) {
            System.err.println("checkMoreAppshow1");
            if (!this.isRateDialogeShow) {
                System.err.println("checkMoreAppshow2");
                if (ScreenConstable.showNewApp) {
                    System.err.println("checkMoreAppshow3");
                    if (!sharedpreferenceIsshownewapp.getDialogNoShow()) {
                        System.err.println("checkMoreAppshow4");
                    }
                }
            }
        }
    }

    private void checkforUpdate() {
        if (isNetworkAvailable() && !ScreenConstable.updateChecked) {
            ScreenConstable.updateChecked = true;
            AppUpdateManager create = AppUpdateManagerFactory.create(this);
            this.aNull = create;
            create.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener() {
                @Override
                public final void onSuccess(Object obj) {
                    MainActivity.this.clso((AppUpdateInfo) obj);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception exc) {
                    Log.d("KidsPreschool", "onFailure: " + exc);
                }
            });
        }
    }

    private void hideNavigation() {
        getWindow().getDecorView().setSystemUiVisibility(7686);
    }

    private void intialize() {
        this.isRateDialogeShow = false;
        getResources();
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        seReciverForPushNotification();
        if (sharedpreferenceIsshownewapp == null) {
            sharedpreferenceIsshownewapp = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_ISSHOWNEWAPP, ScreenTempDataStore.PREFS_KEY_ISSHOWNEWAPP);
        }
        if (sharedpreferenceNever == null) {
            sharedpreferenceNever = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_NS, ScreenTempDataStore.PREFS_KEY_NS);
        }
        if (sharedPreference == null) {
            sharedPreference = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_AL, ScreenTempDataStore.PREFS_KEY_AL);
        }
        if (this.sharedPreference1 == null) {
            this.sharedPreference1 = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_IMAGE, ScreenTempDataStore.PREFS_KEY_IMAGE);
        }
        if (this.sharedPreference2 == null) {
            this.sharedPreference2 = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_IMAGE_NAME, ScreenTempDataStore.PREFS_KEY_IMAGE_NAME);
        }
        if (this.sharedPreference3 == null) {
            this.sharedPreference3 = new ScreenTempDataStore(ScreenTempDataStore.PREFS_NAME_IMAGE_LINK, ScreenTempDataStore.PREFS_KEY_IMAGE_LINK);
        }
        int value = sharedPreference.getValue(this);
        sharedPreference.save(this, value + 1);
        if (sharedpreferenceNever.getValue(this) == 0 && value % 3 == 0 && value != 0) {
            Log.e("ssd", "");
        }
        ScreenConstable.SOUND_SETTING = sharedPreference.getSettingSound(this);
        ScreenConstable.MUSIC_SETTING = sharedPreference.getSettingMusic(this);
    }

    private void intializeIds() {
        this.back = (ImageView) findViewById(R.id.back);
        this.settings = (ImageView) findViewById(R.id.settings);
        this.pixelArt = (ImageView) findViewById(R.id.pixel_art);
        this.back.setOnClickListener(this);
        this.settings.setOnClickListener(this);
        this.settings.setOnLongClickListener(this);
        this.pixelArt.setOnClickListener(this);
        getImageName = this.sharedPreference2.getImageName(this);
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    private void seReciverForPushNotification() {
        this.mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (!intent.getAction().equals(ScreenSetting.REGISTRATION_COMPLETE) && intent.getAction().equals(ScreenSetting.PUSH_NOTIFICATION)) {
                    intent.getStringExtra("message");
                }
            }
        };
    }

    private void setUpUpdateProgressListner() {
        InstallStateUpdatedListener installStateUpdatedListener = new InstallStateUpdatedListener() {
            public final void onStateUpdate(InstallState installState) {
                MainActivity.this.deav(installState);
            }
        };
        this.j = installStateUpdatedListener;
        this.aNull.registerListener(installStateUpdatedListener);
    }

    private void setupMediaPlayer() {
        this.mp = MediaPlayer.create(getBaseContext(), (int) R.raw.click);
        ScreenAudioPlayerActivity mediaPlayerSoundAndMusic = new ScreenAudioPlayerActivity();
        this.mediaPlayerSoundAndMusic = mediaPlayerSoundAndMusic;
        mediaPlayerSoundAndMusic.instializeMusic(this, R.raw.mainmenu);
    }

    private void softKeyboardVisibility() {
        ((InputMethodManager) getApplicationContext().getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 2);
    }

    public void clso(AppUpdateInfo appUpdateInfo) {
        if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
            Log.d("KidsPreschool", "checkforUpdate: Update Available");
            if (appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)) {
                Log.d("KidsPreschool", "checkforUpdate:Flexible Update Allowed");
                try {
                    this.aNull.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.FLEXIBLE, this, this.requestUpdate);
                    setUpUpdateProgressListner();
                } catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("KidsPreschool", "checkforUpdate: Update Not Available");
            }
        } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
            AppUpdateManager appUpdateManager = this.aNull;
            if (appUpdateManager != null) {
                appUpdateManager.completeUpdate();
            }
        } else {
            Log.d("KidsPreschool", "checkforUpdate: Update Not Available");
        }
    }

    public void deav(InstallState installState) {
        AppUpdateManager appUpdateManager;
        if (installState.installStatus() == InstallStatus.DOWNLOADED) {
            AppUpdateManager appUpdateManager2 = this.aNull;
            if (appUpdateManager2 != null) {
                appUpdateManager2.completeUpdate();
            }
        } else if (installState.installStatus() == InstallStatus.INSTALLED && (appUpdateManager = this.aNull) != null) {
            appUpdateManager.unregisterListener(this.j);
        }
    }

    public int getBuyChoise() {
        SharedPreferences sharedPreferences = getSharedPreferences(ScreenTempDataStore.PREF_NAME_PURCHASE, 0);
        this.sharedPreferences = sharedPreferences;
        return sharedPreferences.getInt(ScreenTempDataStore.PREF_KEY_PURCHASE, 0);
    }


    @Override
    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == this.requestUpdate && i2 != -1) {
            Log.d("TAG", "Update flow failed! Result code: " + i2);
            Toast.makeText(this, "Cancelled!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        playClickSound();
        this.myMediaPlayer.StopMp();
        finish();
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        this.myMediaPlayer.StopMp();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
        this.myMediaPlayer.playSound(R.raw.click);
        int id = view.getId();
        if (id == R.id.back) {
            playClickSound();
            this.myMediaPlayer.StopMp();
            this.myMediaPlayer.StopMp();
            finish();
        } else if (id == R.id.pixel_art) {
            Intent intent = new Intent(this, ScreenCardingPixbasedActivity.class);
            this.intent = intent;
            startActivity(intent);
        } else if (id == R.id.settings) {
            startActivity(new Intent(this, ScreenConfigActivity.class));
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AudioHandler.getInstance();
        AudioHandler.initSounds(this);
        new ScreenLanguage().setUpLocale(this);
        intialize();
        setContentView(R.layout.activity_main);
        new ScreenMyRevenueView(this).setad((FrameLayout) findViewById(R.id.adView));
        ImageView imageView = (ImageView) findViewById(R.id.coloring_book);
        this.coloringBook = imageView;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adshandler.showAd(MainActivity.this, new Adshandler.OnClose() {
                    @Override
                    public void click() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, ScreenFillColorActivity.class));
                    }
                });
            }
        });
        ImageView imageView2 = (ImageView) findViewById(R.id.kids_game);
        this.kidsGame = imageView2;
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adshandler.showAd(MainActivity.this, new Adshandler.OnClose() {
                    @Override
                    public void click() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, ScreenBabyFunActivity.class));
                    }
                });
            }
        });
        ImageView imageView3 = (ImageView) findViewById(R.id.custom_paint);
        this.customPaint = imageView3;
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adshandler.showAd(MainActivity.this, new Adshandler.OnClose() {
                    @Override
                    public void click() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, ScreenManualDrawActivity.class));
                    }
                });
            }
        });
        ImageView imageView4 = (ImageView) findViewById(R.id.my_work);
        this.myWork = imageView4;
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Adshandler.showAd(MainActivity.this, new Adshandler.OnClose() {
                    @Override
                    public void click() {
                        MainActivity.this.startActivity(new Intent(MainActivity.this, ScreenItemsActivity.class));
                    }
                });
            }
        });
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        checkMoreAppshow();
        intializeIds();
        setupMediaPlayer();
        softKeyboardVisibility();
        if (sharedPreference.getSettingFirstTime(this)) {
            showdialogFirstTime(false);
        }
        checkforUpdate();
    }

    @Override
    protected void onDestroy() {
        this.myMediaPlayer.StopMp();
        this.mediaPlayerSoundAndMusic.destroyMusic();
        super.onDestroy();
    }

    @Override
    public boolean onLongClick(View view) {
        if (view.getId() != R.id.settings) {
            return true;
        }
        startActivity(new Intent(this, ScreenConfigActivity.class));
        return true;
    }

    @Override
    public void onPause() {
        super.onPause();

        this.myMediaPlayer.StopMp();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        if (i == 1001) {
            int length = strArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                String str = strArr[i2];
                if (iArr[i2] == -1) {
                    if (Build.VERSION.SDK_INT < 23 || !shouldShowRequestPermissionRationale(str)) {
                        sharedPreference.saveStoragePermissionNever(this, true);
                    } else {
                        showdialogFirstTime(true);
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideNavigation();
        if (ScreenTempDataStore.getBuyChoise(this) > 0) {
            isBuy = Boolean.TRUE;
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mRegistrationBroadcastReceiver, new IntentFilter(ScreenSetting.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(this.mRegistrationBroadcastReceiver, new IntentFilter(ScreenSetting.PUSH_NOTIFICATION));
        AlertAlarm.clearNotifications(getApplicationContext());
        this.mediaPlayerSoundAndMusic.startMainMusic();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mediaPlayerSoundAndMusic.startMainMusic();
    }

    @Override
    public void onStop() {
        this.myMediaPlayer.StopMp();
        this.mediaPlayerSoundAndMusic.pauseMainMusic();
        super.onStop();
    }

    public void playClickSound() {
        this.myMediaPlayer.playSound(R.raw.click);
    }

    public void requestPermissionWrite() {
        if (Build.VERSION.SDK_INT < 23) {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PointerIconCompat.TYPE_CONTEXT_MENU);
            Log.v("KidsPreschool", "Permission is granted");
        } else if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
            Log.v("KidsPreschool", "Permission is granted");
        } else {
            Log.v("KidsPreschool", "Permission is revoked");
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, PointerIconCompat.TYPE_CONTEXT_MENU);
        }
    }
}
