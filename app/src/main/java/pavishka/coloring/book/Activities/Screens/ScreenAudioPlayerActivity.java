package pavishka.coloring.book.Activities.Screens;

import android.content.Context;
import android.media.MediaPlayer;


public class ScreenAudioPlayerActivity {
    Context context;
    MediaPlayer mPlayer1;

    public void destroyMusic() {
        try {
            this.mPlayer1.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void instializeMusic(Context context, int i) {
        this.context = context;
        try {
            MediaPlayer create = MediaPlayer.create(context, i);
            this.mPlayer1 = create;
            create.setLooping(true);
            this.mPlayer1.setAudioStreamType(3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void pauseMainMusic() {
        MediaPlayer mediaPlayer = this.mPlayer1;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            this.mPlayer1.pause();
        }
    }

    public void startMainMusic() {
        MediaPlayer mediaPlayer = this.mPlayer1;
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && ScreenConstable.MUSIC_SETTING) {
            this.mPlayer1.start();
        }
    }
}
