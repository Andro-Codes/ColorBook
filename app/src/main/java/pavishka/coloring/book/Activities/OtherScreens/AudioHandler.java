package pavishka.coloring.book.Activities.OtherScreens;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

import java.util.HashMap;

import pavishka.coloring.book.Activities.Screens.ScreenConstable;


public class AudioHandler {
    public static Context mContext;
    public static SoundPool mSoundPool;
    public static HashMap<Integer, Integer> mSoundPoolMap;
    private static AudioHandler instance;
    private static AudioManager mAudioManager;

    private AudioHandler() {
    }


    public static synchronized AudioHandler getInstance() {
        AudioHandler soundManager;
        synchronized (AudioHandler.class) {
            synchronized (AudioHandler.class) {
                if (instance == null) {
                    instance = new AudioHandler();
                }
                soundManager = instance;
            }
            return soundManager;
        }
    }

    public static void initSounds(Context context) {
        mContext = context;
        if (Build.VERSION.SDK_INT >= 21) {
            mSoundPool = new SoundPool.Builder().setMaxStreams(4).build();
        } else {
            mSoundPool = new SoundPool(4, 3, 1);
        }
        mSoundPoolMap = new HashMap<>();
        mAudioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
    }


    public static void playSound(int i, float f) {
        if (ScreenConstable.SOUND_SETTING) {
            try {
                mSoundPool.play(mSoundPoolMap.get(Integer.valueOf(i)).intValue(), 1.0f, 1.0f, 1, 0, f);
            } catch (Exception e) {
                System.out.print("testing");
                e.printStackTrace();
            }
        }
    }

}
