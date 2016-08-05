package vn.techkid.simplemp3player.Service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Laptop88 on 8/4/2016.
 */
public class PlayingMusicService extends Service implements MediaPlayer.OnPreparedListener{
    String url;
    int fullTime, eslapedTime;
    private Handler durationHandler = new Handler();

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        url = intent.getStringExtra("url");
        Log.d("url", url);
        setMediaPlayer();
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }
    private void setMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        play();
    }
    public void play() {
        mediaPlayer.start();
        durationHandler.postDelayed(updateSeekBarTime, 100);

    }

    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            eslapedTime = mediaPlayer.getCurrentPosition();
            fullTime = mediaPlayer.getDuration();
            Intent updateProgressIntent = new Intent();
            updateProgressIntent.putExtra("fullTime", fullTime);
            int timeRemaining = fullTime - eslapedTime;
            int minutesEslaped = (int) TimeUnit.MILLISECONDS.toMinutes((long)eslapedTime);
            int secondsEslaped = (int) (TimeUnit.MILLISECONDS.toSeconds((long) eslapedTime)-TimeUnit.MINUTES.toSeconds((long)minutesEslaped));
            updateProgressIntent.putExtra("eslapsedTime", String.format("%d:%d", minutesEslaped, secondsEslaped));
            int minutesRemaining = (int) TimeUnit.MILLISECONDS.toMinutes((long)timeRemaining);
            int secondsRemaining = (int) (TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)-TimeUnit.MINUTES.toSeconds((long)minutesRemaining));
            updateProgressIntent.putExtra("timeRemaining", String.format("%d:%d", minutesRemaining, secondsRemaining));
            updateProgressIntent.putExtra("progress", (1000*(float)eslapedTime)/fullTime);
            updateProgressIntent.setAction("updateSeekBar");
            sendBroadcast(updateProgressIntent);
            durationHandler.postDelayed(this, 100);
        }
    };
    public class MyBinder extends Binder {
        public PlayingMusicService getService(){
            return PlayingMusicService.this;
        }
    }

}
