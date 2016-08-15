package vn.techkid.simplemp3player.Service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import vn.techkid.simplemp3player.Activity.ChartSong;
import vn.techkid.simplemp3player.Activity.MainActivity;
import vn.techkid.simplemp3player.Activity.PlayerActivity;
import vn.techkid.simplemp3player.Getter.SongGetter;

import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;

/**
 * Created by Laptop88 on 8/4/2016.
 */
public class PlayingMusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, View.OnClickListener{
    public  WindowManager windowManager;
    public  LinearLayout linearLayout;
    public  WindowManager.LayoutParams params;
    private ImageButton imageSong;private String url;

    private String title, artist;
    private int fullTime, eslapedTime;
    private Handler durationHandler = new Handler();
    public static final int NOTIFY_ID = 1912;
    private int currentPos;
    public static int maxSongs;
    private int count;
    ArrayList<Song> songs = new ArrayList<>();
    public HelperClass helperClass;
    public static boolean isWait;
//    private MusicController musicController;
    private MediaPlayer mediaPlayer;
    private boolean isVisible;

    @Override
    public void onCreate() {
        super.onCreate();
        initView();
    }
    private void initView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        linearLayout = new LinearLayout(this);
        imageSong = new ImageButton(this);

        ViewGroup.LayoutParams iParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageSong.setBackgroundColor(0x00388E3C);
        imageSong.setLayoutParams(iParams);
        imageSong.setOnClickListener(this);

        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.setLayoutParams(llParameters);

        params = new WindowManager.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, 100, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.BOTTOM;

        linearLayout.addView(imageSong);

//        windowManager.addView(linearLayout, params);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getSongsList();
        get320kDownloadLink(currentPos);
        setMediaPlayer();
//        setBroadcastReceiver();
        return START_NOT_STICKY;
    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    private void getSongsList() {
        if (FloatingControlWindow.getKey().equals("chartSong")){
            songs = ChartSong.getSongs();
            maxSongs = 20;
        }
        currentPos = FloatingControlWindow.getCurrentPos();
        helperClass = new HelperClass(maxSongs);
    }

    public void setMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        try {
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
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        title = songs.get(currentPos).getTitle();
        artist = songs.get(currentPos).getArtist();
        play();


        Intent resultIntent = new Intent(getApplicationContext(), PlayerActivity.class);
        PendingIntent pendInt = PendingIntent.getActivity(getApplicationContext(), 0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder mBuilder = new Notification.Builder(this);

        mBuilder.setContentIntent(pendInt)
                .setSmallIcon(R.drawable.image_music)
                .setContentTitle("New Message")
                .setContentText("You've received new message.")
                .setTicker("New Message Alert!")
                .setContentIntent(pendInt);
        Notification not = mBuilder.build();
        startForeground(NOTIFY_ID, not);
    }


    public void play() {
        count++;
        mediaPlayer.start();
        if (isWait){
            mediaPlayer.pause();
        }
        fullTime = mediaPlayer.getDuration();
        durationHandler.postDelayed(updateSeekBarTime, 100);

    }

    public Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            if (mediaPlayer == null){
                durationHandler.removeCallbacks(updateSeekBarTime);
                return;
            }
            eslapedTime = mediaPlayer.getCurrentPosition();
            Intent updateProgressIntent = new Intent();
            updateProgressIntent.putExtra("progress", eslapedTime);
            updateProgressIntent.putExtra("fullTime", fullTime);
            updateProgressIntent.putExtra("title", title);
            updateProgressIntent.putExtra("artist", artist);
            int timeRemaining = fullTime - eslapedTime;
            int minutesEslaped = (int) TimeUnit.MILLISECONDS.toMinutes((long)eslapedTime);
            int secondsEslaped = (int) (TimeUnit.MILLISECONDS.toSeconds((long) eslapedTime)-TimeUnit.MINUTES.toSeconds((long)minutesEslaped));
            updateProgressIntent.putExtra("eslapsedTime", String.format("%d:%d", minutesEslaped, secondsEslaped));
            int minutesRemaining = (int) TimeUnit.MILLISECONDS.toMinutes((long)timeRemaining);
            int secondsRemaining = (int) (TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)-TimeUnit.MINUTES.toSeconds((long)minutesRemaining));
            updateProgressIntent.putExtra("timeRemaining", String.format("%d:%d", minutesRemaining, secondsRemaining));
            updateProgressIntent.setAction("updateSeekBar");
            sendBroadcast(updateProgressIntent);
            if (MainActivity.isAlive){
                if (PlayerActivity.isBackPressed && !MainActivity.isForceClose){
                    if (!isVisible){
                       isVisible = true;
                        windowManager.addView(linearLayout, params);
                    }

                }
                else {
                    if (isVisible){
                        isVisible = false;
                        windowManager.removeView(linearLayout);
                    }
                }
            }
            else {
                if (isVisible){
                    isVisible = false;
                    windowManager.removeView(linearLayout);
                }
            }
            durationHandler.postDelayed(this, 100);
        }
    };


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mediaPlayer.reset();
        return false;

    }
    public void get320kDownloadLink(int pos) {
        SongGetter getter = new SongGetter(songs.get(pos).getAccessLink());
        try {
            getter.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        url = getter.getUrl();
        Log.d("final", url);


    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        mediaPlayer.release();
        if (!PlayerActivity.isLooping){
            helperClass.integers.remove((Integer)currentPos);

            Log.d("khuong", "size: "+helperClass.integers.size());
            if (helperClass.integers.size()==0){
                for (int i = 0; i < maxSongs; i++) {
                    helperClass.integers.add(i);
                }
                if (!PlayerActivity.isRepeat){
                    isWait = true;
                    Log.d("khuong", "isWait: "+isWait);
                }
            }
            if (PlayerActivity.isShuffle) {
                currentPos = helperClass.getRandomPos();
                Log.d("khuong1", currentPos+"");
            }
            else {
                currentPos = (currentPos+1)%20;
                Log.d("khuong2", currentPos+"");
            }
        }

        get320kDownloadLink(currentPos);
        setMediaPlayer();
    }

    @Override
    public void onClick(View v) {

    }

    public class MyBinder extends Binder {
        public PlayingMusicService getService(){
            return PlayingMusicService.this;
        }
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }
    //////////////////////////////////////
    /////////////////////////////////////
    public class HelperClass {
        public ArrayList<Integer> integers = new ArrayList<>();
        private int n;


        public HelperClass(int n){
            this.n = n;
            for (int i = 0; i < n; i++) {
                integers.add(i);
            }

        }
        public int getRandomPos(){
            int rand = (int)(Math.random()*integers.size());
            return integers.remove(rand);
        }



    }
}
