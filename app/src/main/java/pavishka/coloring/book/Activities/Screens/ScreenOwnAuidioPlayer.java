package pavishka.coloring.book.Activities.Screens;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.util.Log;

import java.util.Random;

import pavishka.coloring.book.R;


public class ScreenOwnAuidioPlayer {
    Context context;
    MediaPlayer aNull = null;
    private String colorSoundString;
    private int length = 0;

    public ScreenOwnAuidioPlayer(Context context) {
        this.context = context;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }

    private String getRandomApplause() {
        int nextInt = new Random().nextInt(4) + 1;
        return nextInt == 1 ? "applause_excellent" : nextInt == 2 ? "applause_greatjob" : nextInt == 3 ? "applause_intelligent" : nextInt != 4 ? nextInt != 5 ? "applause_excellent" : "applause_youdid" : "applause_terrific";
    }

    public void StopMp() {
        MediaPlayer mediaPlayer = this.aNull;
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                this.aNull.reset();
                this.aNull.release();
                this.aNull = null;
                this.length = 0;
            } catch (Exception e) {
                Log.d("error", e.toString());
            }
        }
    }

    public String getRandomColorSound() {
        switch (new Random().nextInt(14) + 1) {
            case 1:
                this.colorSoundString = "colortouch1";
                break;
            case 2:
                this.colorSoundString = "colortouch2";
                break;
            case 3:
                this.colorSoundString = "colortouch3";
                break;
            case 4:
                this.colorSoundString = "colortouch4";
                break;
            case 5:
                this.colorSoundString = "colortouch5";
                break;
            case 6:
                this.colorSoundString = "colortouch6";
                break;
            case 7:
                this.colorSoundString = "colortouch7";
                break;
            case 8:
                this.colorSoundString = "colortouch8";
                break;
            case 9:
                this.colorSoundString = "colortouch9";
                break;
            case 10:
                this.colorSoundString = "colortouch10";
                break;
            case 11:
                this.colorSoundString = "colortouch11";
                break;
            case 12:
                this.colorSoundString = "colortouch12";
                break;
            case 13:
                this.colorSoundString = "colortouch13";
                break;
            case 14:
                this.colorSoundString = "colortouch14";
                break;
            case 15:
                this.colorSoundString = "colortouch15";
                break;
            default:
                this.colorSoundString = "colortouch1";
                break;
        }
        return this.colorSoundString;
    }

    public String getRandomSelectArtSound() {
        switch (new Random().nextInt(7) + 1) {
            case 1:
                return "art1";
            case 2:
                return "art2";
            case 3:
            default:
                return "art3";
            case 4:
                return "art4";
            case 5:
                return "art5";
            case 6:
                return "art6";
            case 7:
                return "art7";
            case 8:
                return "art8";
        }
    }

    public void playClickSound() {
        StopMp();
        if (ScreenConstable.SOUND_SETTING) {
            playSound(R.raw.click);
        }
    }

    public void playColorRandomSound() {
        int identifier = this.context.getResources().getIdentifier(getRandomColorSound().toLowerCase(), "raw", this.context.getPackageName());
        if (ScreenConstable.SOUND_SETTING && identifier != 0) {
            playSound(identifier);
        }
    }

    public void playSelectArtRandomSound() {
        int identifier = this.context.getResources().getIdentifier(getRandomSelectArtSound().toLowerCase(), "raw", this.context.getPackageName());
        if (ScreenConstable.SOUND_SETTING && identifier != 0) {
            playSound(identifier);
        }
    }

    public void playSound(int i) {
        MediaPlayer create = MediaPlayer.create(this.context, i);
        this.aNull = create;
        if (ScreenConstable.SOUND_SETTING && create != null) {
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    PlaybackParams playbackParams = new PlaybackParams();
                    playbackParams.setPitch(1.18f);
                    this.aNull.setPlaybackParams(playbackParams);
                }
                this.aNull.seekTo(this.length);
                this.aNull.start();
                this.aNull.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        ScreenOwnAuidioPlayer.this.length = 0;
                    }
                });
            } catch (Exception e) {
                Log.d("error", e.toString());
            }
        }
    }

    public void speakApplause() {
        int identifier = this.context.getResources().getIdentifier(getRandomApplause().toLowerCase(), "raw", this.context.getPackageName());
        if (ScreenConstable.SOUND_SETTING && identifier != 0) {
            playSound(identifier);
        }
    }

}
