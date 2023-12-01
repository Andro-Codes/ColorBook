package pavishka.coloring.book.Activities.OtherScreens;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Locale;

import pavishka.coloring.book.R;
import pavishka.coloring.book.Activities.Screens.ScreenConstable;
import pavishka.coloring.book.Activities.Screens.ScreenLanguage;
import pavishka.coloring.book.Activities.Screens.ScreenOwnAuidioPlayer;


public class ScreenConfigActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
    public ScreenTempDataStore settingSp;
    int anInt;
    Switch aSwitch;
    Switch aSwitch1;
    ImageView iv_sound;
    ImageView iv_music;
    ImageView imageView;
    ImageView imageView1;
    ScreenOwnAuidioPlayer myMediaPlayer;
    ArrayList<Lang> langs = new ArrayList<>();
    Locale locale;

    public static void shareApp(Context context) {
        String packageName = context.getPackageName();
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.putExtra("android.intent.extra.SUBJECT", "PreSchool Coloring");
        intent.putExtra("android.intent.extra.TEXT", "Try this awesome coloring game: https://play.google.com/store/apps/details?id=" + packageName);
        intent.setType("text/plain");
        context.startActivity(intent);
    }

    public static void updateTTF(String str) {
        int hashCode = str.hashCode();
        if (hashCode == 3201) {
            str.equals("de");
        } else if (hashCode == 3241) {
            str.equals("en");
        } else if (hashCode == 3246) {
            str.equals("es");
        } else if (hashCode == 3276) {
            str.equals("fr");
        } else if (hashCode == 3371) {
            str.equals("it");
        } else if (hashCode == 3588) {
            str.equals("pt");
        }
    }

    private void animateClick(View view) {
        view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_in_low));
    }

    public void finishAcivity() {
        ScreenConstable.showNewApp = true;
        finish();
    }

    private void hideNavigation() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 14) {
            getWindow().getDecorView().setSystemUiVisibility(7686);
        } else if (i >= 11) {
            getWindow().getDecorView().setSystemUiVisibility(1);
        }
    }

    private void initLang() {
        this.langs.clear();
        this.langs.add(new Lang("English", "en"));
        this.langs.add(new Lang("Español", "es"));
        this.langs.add(new Lang("Português", "pt"));
        this.langs.add(new Lang("Deutsche", "de"));
        this.langs.add(new Lang("Italiano", "it"));
        this.langs.add(new Lang("Français", "fr"));
        this.langs.add(new Lang("русский", "ru"));
        this.langs.add(new Lang("Indonesian", "in"));
        this.langs.add(new Lang("Vietnamese", "vi"));
        this.langs.add(new Lang("Malay", "ms"));
        int indexLang = this.settingSp.getIndexLang(this);
        this.anInt = indexLang;
    }

    private void refreshText() {
    }

    private void setLang(int i) {
        updateTTF(this.langs.get(i).getCode());
        ScreenTempDataStore.saveIndexLang(this, i);
        this.settingSp.saveLocale(this, this.langs.get(i).getCode());
        jem(this.langs.get(i).getCode());
        new ScreenLanguage().setUpLocale(this);
        refreshText();
    }

    private void setvalueMusic() {
        boolean settingMusic = this.settingSp.getSettingMusic(this);
        ScreenConstable.MUSIC_SETTING = settingMusic;
        if (settingMusic) {
            this.aSwitch.setChecked(true);
        } else {
            this.aSwitch.setChecked(false);
        }
    }

    private void setvalueSound() {
        boolean settingSound = this.settingSp.getSettingSound(this);
        ScreenConstable.SOUND_SETTING = settingSound;
        if (settingSound) {
            this.aSwitch1.setChecked(true);
        } else {
            this.aSwitch1.setChecked(false);
        }
    }

    protected void jem(String str) {
        this.locale = new Locale(str);
        this.settingSp.saveLocale(this, str);
        Resources resources = getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = this.locale;
        resources.updateConfiguration(configuration, displayMetrics);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAcivity();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        switch (compoundButton.getId()) {
            case R.id.switch_music :
                if (this.aSwitch.isChecked()) {
                    iv_music.setBackgroundResource(R.drawable.ic_music);
                    ScreenConstable.MUSIC_SETTING = true;
                    this.settingSp.saveSettingMusic(this, true);
                    return;
                }
                iv_music.setBackgroundResource(R.drawable.ic_music_off);
                ScreenConstable.MUSIC_SETTING = false;
                this.settingSp.saveSettingMusic(this, false);
                return;
            case R.id.switch_sound :
                if (this.aSwitch1.isChecked()) {
                    iv_sound.setBackgroundResource(R.drawable.ic_sound);
                    ScreenConstable.SOUND_SETTING = true;
                } else {
                    iv_sound.setBackgroundResource(R.drawable.ic_sound_off);
                    ScreenConstable.SOUND_SETTING = false;
                }
                this.settingSp.saveSettingSound(this, ScreenConstable.SOUND_SETTING);
                return;
            default:
                return;
        }
    }

    @Override
    public void onClick(View view) {
        animateClick(view);
        playClickSound();
        switch (view.getId()) {
            case R.id.iv_music :
                if (this.aSwitch.isChecked()) {
                    aSwitch.setChecked(false);
                } else {
                    aSwitch.setChecked(true);
                }

                iv_music.setImageDrawable(aSwitch.isChecked() ? ContextCompat.getDrawable(this, R.drawable.ic_music) :ContextCompat.getDrawable(this, R.drawable.ic_music_off) );

                return;
            case R.id.iv_sound :
                if (this.aSwitch1.isChecked()) {
                    aSwitch1.setChecked(false);
                } else {
                    aSwitch1.setChecked(true);
                }
                iv_sound.setImageDrawable(aSwitch1.isChecked() ? ContextCompat.getDrawable(this, R.drawable.ic_sound) :ContextCompat.getDrawable(this, R.drawable.ic_sound_off) );
                return;
            case R.id.Share :
                shareApp(this);
                return;
            case R.id.close :
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ScreenConfigActivity.this.finishAcivity();
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
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_settings);
        this.myMediaPlayer = new ScreenOwnAuidioPlayer(this);
        if (this.settingSp == null) {
            this.settingSp = new ScreenTempDataStore(ScreenTempDataStore.PREF_NAME_SOUND, ScreenTempDataStore.PREF_KEY_SOUND);
        }
        this.iv_sound = (ImageView) findViewById(R.id.iv_sound);
        this.iv_music = (ImageView) findViewById(R.id.iv_music);
        this.aSwitch = (Switch) findViewById(R.id.switch_music);
        aSwitch1 = (Switch) findViewById(R.id.switch_sound);
        this.imageView1 = (ImageView) findViewById(R.id.close);
        this.imageView = (ImageView) findViewById(R.id.Share);


        iv_music.setOnClickListener(this);
        iv_sound.setOnClickListener(this);

        this.imageView1.setOnClickListener(this);
        this.imageView.setOnClickListener(this);
        this.aSwitch.setOnCheckedChangeListener(this);
        aSwitch1.setOnCheckedChangeListener(this);
        setvalueMusic();
        setvalueSound();
        initLang();
        setLang(this.settingSp.getIndexLang(this));

        iv_sound.setImageDrawable(aSwitch1.isChecked() ? ContextCompat.getDrawable(this, R.drawable.ic_sound) :ContextCompat.getDrawable(this, R.drawable.ic_sound_off) );
        iv_music.setImageDrawable(aSwitch.isChecked() ? ContextCompat.getDrawable(this, R.drawable.ic_music) :ContextCompat.getDrawable(this, R.drawable.ic_music_off) );
    }

    @Override
    public void onResume() {
        super.onResume();
        hideNavigation();
    }

    public void playClickSound() {
        if (ScreenConstable.SOUND_SETTING) {
            this.myMediaPlayer.playSound(R.raw.click);
        }
    }

    public static class Lang {
        private String code;
        private String name;

        public Lang(String str, String str2) {
            this.name = str;
            this.code = str2;
        }

        public String getCode() {
            return this.code;
        }

        public String getName() {
            return this.name;
        }
    }
}
