package vn.techkid.simplemp3player.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import vn.techkid.simplemp3player.Activity.PlayerActivity;
import vn.techkid.simplemp3player.R;

/**
 * Created by Laptop88 on 8/4/2016.
 */
public class PlayingMusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{
    private String url;
    private int fullTime, eslapedTime;
    public Handler durationHandler = new Handler();


    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    private MediaPlayer mediaPlayer;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        url = intent.getStringExtra("url");
        setMediaPlayer();
        return new MyBinder();
    }

    public int getEslapedTime() {
        return eslapedTime;
    }

    public void setEslapedTime(int eslapedTime) {
        this.eslapedTime = eslapedTime;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return START_NOT_STICKY;
    }
    private void setMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
            Log.d("lm", url);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(url);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {

        play();
//        Intent notIntent = new Intent(this, PlayerActivity.class);
//        notIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendInt = PendingIntent.getActivity(this, 0,
//                notIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Notification.Builder mBuilder = new Notification.Builder(this);
//
//        mBuilder.setContentIntent(pendInt)
//                .setSmallIcon(R.drawable.image_music)
//                .setContentTitle("New Message")
//                .setContentText("You've received new message.")
//                .setTicker("New Message Alert!");
//        Notification not = mBuilder.build();
//        startForeground(NOTIFY_ID, not);
    }


    @Override
    public void onDestroy() {
        Log.d("destroy", "Yes");
        super.onDestroy();


    }

    public void play() {
        mediaPlayer.start();
        fullTime = mediaPlayer.getDuration();
        durationHandler.postDelayed(updateSeekBarTime, 100);

    }

    public Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            if (mediaPlayer==null){
                durationHandler.removeCallbacks(this);
                return;
            }
            eslapedTime = mediaPlayer.getCurrentPosition();
            Intent updateProgressIntent = new Intent();
            updateProgressIntent.putExtra("progress", eslapedTime);
            updateProgressIntent.putExtra("fullTime", fullTime);
            int timeRemaining = fullTime - eslapedTime;
            int minutesEslaped = (int) TimeUnit.MILLISECONDS.toMinutes((long)eslapedTime);
            int secondsEslaped = (int) (TimeUnit.MILLISECONDS.toSeconds((long) eslapedTime)-TimeUnit.MINUTES.toSeconds((long)minutesEslaped));
            updateProgressIntent.putExtra("eslapsedTime", String.format("%d:%d", minutesEslaped, secondsEslaped));
            int minutesRemaining = (int) TimeUnit.MILLISECONDS.toMinutes((long)timeRemaining);
            int secondsRemaining = (int) (TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)-TimeUnit.MINUTES.toSeconds((long)minutesRemaining));
            updateProgressIntent.putExtra("timeRemaining", String.format("%d:%d", minutesRemaining, secondsRemaining));
            updateProgressIntent.setAction("updateSeekBar");
            sendBroadcast(updateProgressIntent);
            durationHandler.postDelayed(this, 100);
        }
    };

    public  void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        PlayerActivity.isCompleted = true;
    }

    public class MyBinder extends Binder {
        public PlayingMusicService getService(){
            return PlayingMusicService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);

    }
}
