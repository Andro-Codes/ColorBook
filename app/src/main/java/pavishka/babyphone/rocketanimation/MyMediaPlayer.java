package pavishka.babyphone.rocketanimation;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;

import java.io.PrintStream;


public class MyMediaPlayer {
    Context context;
    MediaPlayer aNull = null;
    private int length = 0;

    public MyMediaPlayer(Context context) {
        this.context = context;
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
    }


    public void playCustomSound(String str) {
        int identifier = this.context.getResources().getIdentifier(str.toLowerCase(), "raw", this.context.getPackageName());
        if (identifier != 0) {
            playSound(identifier);
        }
    }

    public void playSound(int i) {
        this.aNull = MediaPlayer.create(this.context, i);
        PrintStream printStream = System.err;
        printStream.println("sound check" + this.aNull);
        if (this.aNull != null) {
            PrintStream printStream2 = System.err;
            printStream2.println("sound check if" + this.aNull);
            try {
                if (Build.VERSION.SDK_INT >= 23) {
                    PlaybackParams playbackParams = new PlaybackParams();
                    playbackParams.setPitch(1.3f);
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
                        MyMediaPlayer.this.length = 0;
                    }
                });
            } catch (Exception unused) {
                PrintStream printStream3 = System.err;
                printStream3.println("sound check catch" + this.aNull);
            }
        }
    }
}
