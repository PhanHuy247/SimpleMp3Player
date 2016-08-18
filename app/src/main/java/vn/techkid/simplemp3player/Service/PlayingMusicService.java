package vn.techkid.simplemp3player.Service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaMetadata;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


import vn.techkid.simplemp3player.Activity.MainActivity;
import vn.techkid.simplemp3player.Activity.PlayerActivity;
import vn.techkid.simplemp3player.Fragment.ChartSong;
import vn.techkid.simplemp3player.Fragment.PlayBackCountryFragment;
import vn.techkid.simplemp3player.Getter.SongGetter;

import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;

/**
 * Created by Laptop88 on 8/4/2016.
 */
public class PlayingMusicService extends Service implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, View.OnClickListener{
    public  WindowManager windowManager;
    public  LinearLayout linearLayout, linearLayout1, linearLayout2;
    public  WindowManager.LayoutParams params;
    private ImageButton imageSong;
    private String url;

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
    private ImageButton pauseBtn;
    private ImageButton nextBtn;
    private ImageButton prevBtn;
    private TextView textView;
    private Bitmap artwork;

    public static final String ACTION_PLAY = "action_play";
    public static final String ACTION_PAUSE = "action_pause";
    public static final String ACTION_NEXT = "action_next";
    public static final String ACTION_PREVIOUS = "action_previous";
    public static final String ACTION_STOP = "action_stop";

    Intent resultIntent;
    PendingIntent pendInt;
    MediaSession mediaSession;
    NotiReceiver receiver;


    @Override
    public void onCreate() {
        super.onCreate();
        initView();
    }
    private void initView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        linearLayout = new LinearLayout(this);
        linearLayout1 = new LinearLayout(this);
        linearLayout2 = new LinearLayout(this);
        imageSong = new ImageButton(this);
        textView = new TextView(this);
        textView.setBackgroundColor(0x80388E3C);
        ViewGroup.LayoutParams tvParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(tvParams);
        textView.setGravity(Gravity.CENTER_VERTICAL);
//        textView.setMaxWidth(400);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PlayingMusicService.this, PlayerActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });

        pauseBtn = new ImageButton(this);
        pauseBtn.setImageResource(R.drawable.ic_pause_blue_grey_800_36dp);
        pauseBtn.setBackgroundColor(0x80388E3C);
        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                else {
                    mediaPlayer.start();
                }


            }
        });

        nextBtn = new ImageButton(this);
        nextBtn.setImageResource(R.drawable.ic_skip_next_blue_grey_800_36dp);
        nextBtn.setBackgroundColor(0x80388E3C);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
            }
        });


        prevBtn = new ImageButton(this);
        prevBtn.setImageResource(R.drawable.ic_skip_previous_blue_grey_800_36dp);
        prevBtn.setBackgroundColor(0x80388E3C);
        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.release();
                if (PlayerActivity.isShuffle) {
//            int i = FloatingControlWindow.pService.helperClass.getRandomPos();
//            FloatingControlWindow.pService.setCurrentPos(i);
                } else {
                    currentPos = (currentPos - 1) % 20;
                }
                get320kDownloadLink(currentPos);
                setMediaPlayer();
            }
        });



        ViewGroup.LayoutParams btnParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pauseBtn.setLayoutParams(btnParams);
        nextBtn.setLayoutParams(btnParams);
        pauseBtn.setLayoutParams(btnParams);


//        imageSong.setBackgroundColor(0x80388E3C);
//        imageSong.setLayoutParams(iParams);
//        imageSong.setOnClickListener(this);

        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setBackgroundColor(0x95388E3C);
        linearLayout.setLayoutParams(llParameters);


        LinearLayout.LayoutParams l2Parameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout2.setBackgroundColor(0x80388E3C);
        linearLayout2.setLayoutParams(l2Parameters);

        params = new WindowManager.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.x = 0;
        params.y = 0;
        params.gravity = Gravity.BOTTOM;

        linearLayout.addView(prevBtn);
        linearLayout.addView(pauseBtn);
        linearLayout.addView(nextBtn);
        linearLayout.addView(textView);
        linearLayout.addView(linearLayout2);

//        windowManager.addView(linearLayout, params);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setBroadcastReceiver();
        getSongsList();
        get320kDownloadLink(currentPos);
        setMediaPlayer();
//        setBroadcastReceiver();
        return START_NOT_STICKY;
    }

    private void setBroadcastReceiver() {
        receiver = new NotiReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_NEXT);
        filter.addAction(ACTION_PAUSE);
        filter.addAction(ACTION_PREVIOUS);
        registerReceiver(receiver, filter);

    }

    public int getCurrentPos() {
        return currentPos;
    }

    public void setCurrentPos(int currentPos) {
        this.currentPos = currentPos;
    }

    private void getSongsList() {
        maxSongs = 20;
        if (FloatingControlWindow.getKey().equals("chartSong")){
            songs = FloatingControlWindow.arrayList;
        }
        if(FloatingControlWindow.getKey().equals("PlayBackCountryFragment")){
            songs = FloatingControlWindow.arrayList;
        }
        if(FloatingControlWindow.getKey().equals("artist")){
            songs = FloatingControlWindow.arrayList;
        }
        if(FloatingControlWindow.getKey().equals("categoryKorea")){
            songs = FloatingControlWindow.arrayList;
        }
        if(FloatingControlWindow.getKey().equals("categoryUsuk")){
            songs = FloatingControlWindow.arrayList;
        }
        if(FloatingControlWindow.getKey().equals("categoryVietnam")){
            songs = FloatingControlWindow.arrayList;
        }
        currentPos = FloatingControlWindow.getCurrentPos();
        helperClass = new HelperClass(maxSongs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onPrepared(MediaPlayer mp) {
        title = songs.get(currentPos).getTitle();
        artist = songs.get(currentPos).getArtist();
        play();
        artwork = BitmapFactory.decodeResource(getResources(), R.drawable.image_music);

// Create a new MediaSession
        mediaSession = new MediaSession(this, "debug tag");
//         Update the current metadata
        mediaSession.setMetadata(new MediaMetadata.Builder()
                .putBitmap(MediaMetadata.METADATA_KEY_ALBUM_ART, artwork)
                .putString(MediaMetadata.METADATA_KEY_ARTIST, "Pink Floyd")
                .putString(MediaMetadata.METADATA_KEY_ALBUM, "Dark Side of the Moon")
                .putString(MediaMetadata.METADATA_KEY_TITLE, "The Great Gig in the Sky")
                .build());
        // Indicate you're ready to receive media commands
        mediaSession.setActive(true);
        // Attach a new Callback to receive MediaSession updates
        mediaSession.setCallback(new MediaSession.Callback() {

            // Implement your callbacks

        });
        // Indicate you want to receive transport controls via your Callback
        mediaSession.setFlags(MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        resultIntent = new Intent(getApplicationContext(), PlayerActivity.class);
        pendInt = PendingIntent.getActivity(getApplicationContext(), 0,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        setupNotification();
    }
    private PendingIntent retreivePlaybackAction(int which) {
        Intent action;
        PendingIntent pendingIntent;
        switch (which) {
            case 1:
                // Play and pause
                action = new Intent(ACTION_PAUSE);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, action, 0);
                return pendingIntent;
            case 2:
                // Skip tracks
                action = new Intent(ACTION_NEXT);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 2, action, 0);
                return pendingIntent;
            case 3:
                // Previous tracks
                action = new Intent(ACTION_PREVIOUS);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 3, action, 0);
                return pendingIntent;
            case 4:
                //stop foreground
                action = new Intent(ACTION_STOP);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 4, action, 0);
                return pendingIntent;
            default:
                break;
        }
        return null;
    }

    public void play() {
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
            if (isVisible){
                textView.setText("\t"+songs.get(currentPos).getTitle()+"\n\t"+songs.get(currentPos).getArtist());
                if (mediaPlayer.isPlaying()){
                    pauseBtn.setImageResource(R.drawable.ic_pause_blue_grey_800_36dp);
                }
                else {
                    pauseBtn.setImageResource(R.drawable.ic_play_arrow_blue_grey_800_36dp);
                }
            }
            sendBroadcast(updateProgressIntent);
            if (MainActivity.isAlive){
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
            durationHandler.postDelayed(this, 100);
        }
    };
    public  void nextAction(){
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
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mediaPlayer.reset();
        return false;

    }
    public void get320kDownloadLink(int pos) {
        Log.d("before", songs.get(pos).getAccessLink());
        SongGetter getter = new SongGetter(songs.get(pos).getAccessLink(), songs.get(pos).getPosition());
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
        nextAction();
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
        mediaPlayer.release();
        stopForeground(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mediaSession.release();
        }
        super.onDestroy();
    }
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
    private class NotiReceiver extends BroadcastReceiver{
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ACTION_NEXT)){
                nextAction();

            }
            else if (intent.getAction().equals(ACTION_PAUSE)){

                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                }
                else {
                    mediaPlayer.start();
                }
                setupNotification();

            }
            else if (intent.getAction().equals(ACTION_PREVIOUS)){
                mediaPlayer.release();
                Log.d("bombaya", currentPos+"");
                currentPos = (currentPos-1)%20;
                Log.d("bombaya", currentPos+"");

                get320kDownloadLink(currentPos);
                setMediaPlayer();
            }
            else if (intent.getAction().equals(ACTION_STOP)){
                stopSelf();
            }

        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupNotification () {
        Notification.Builder mBuilder = new Notification.Builder(PlayingMusicService.this);
        int id;
        if (mediaPlayer.isPlaying()){
            id = R.drawable.ic_pause_green_800_36dp;
        }
        else {
            id = R.drawable.ic_play_arrow_green_800_36dp;
        }
        mBuilder.setContentIntent(pendInt)
                .setColor(0x388E3C)
                .setLargeIcon(artwork)
                .setSmallIcon(R.drawable.image_music)
                .setShowWhen(false)
//                .setContentTitle("New Message")
//                .setContentText("You've received new message.")
//                .setTicker("New Message Alert!")
                .setStyle(new Notification.MediaStyle()
                        // Attach our MediaSession token
                        .setMediaSession(mediaSession.getSessionToken())
                        // Show our playback controls in the compat view
                        .setShowActionsInCompactView(0, 1, 2))
                .setContentText(songs.get(currentPos).getArtist())
                .setContentTitle(songs.get(currentPos).getTitle())
                // Add some playback controls

                .setContentIntent(pendInt)

                .addAction(R.drawable.ic_skip_previous_green_800_36dp, "prev", retreivePlaybackAction(3))
                .addAction(id, "pause", retreivePlaybackAction(1))
                .addAction(R.drawable.ic_skip_next_green_800_36dp, "next", retreivePlaybackAction(2));
        Notification not = mBuilder.build();
        startForeground(NOTIFY_ID, not);
    }

}
