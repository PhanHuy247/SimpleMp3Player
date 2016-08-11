package vn.techkid.simplemp3player.Activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Getter.SongGetter;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;
import vn.techkid.simplemp3player.Service.PlayingMusicService;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_songName, tv_artistName;
    private TextView tv_eslapedTime, tv_timeLeft;
    private SeekBar sb_timeProgress;
    private ImageButton ibt_shuffle, ibt_previous, ibt_play, ibt_next, ibt_repeat;
    private String eslapedTime, remainingTime;
    private int progressTime, fullTime;
    private String url;
    private String title;
    private String artist;

    public static boolean isShuffle, isLooping, isRepeat;
    PlayingMusicReceiver receiver;
    public static boolean isCompleted;
//    static int currentPos;
//    private boolean fromUser;
    public static boolean isDestroyed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        if (getIntent().getStringExtra("resume").equals("yes")){
//
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        isDestroyed = false;
        getSongInfo();
        initView();


//        if (FloatingControlWindow.windowManager!=null){
//            FloatingControlWindow.windowManager.removeView();
//        }
        setBroadcastReceiver();
    }
    private void getSongInfo() {
        title = getIntent().getStringExtra("title");
        artist = getIntent().getStringExtra("artist");
    }

    private void setBroadcastReceiver() {
        receiver = new PlayingMusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("updateSeekBar");
        filter.addAction("updateSong");
//        filter.addAction("completed");
        registerReceiver(receiver, filter);
    }



    private void initView() {
        tv_songName = (TextView)findViewById(R.id.text_songName);
        tv_artistName = (TextView)findViewById(R.id.text_artist);
        tv_eslapedTime = (TextView)findViewById(R.id.text_eslapedTime);
        tv_timeLeft = (TextView)findViewById(R.id.text_timeLeft);
        sb_timeProgress = (SeekBar)findViewById(R.id.seekBar_progressBar);
        ibt_shuffle = (ImageButton)findViewById(R.id.button_shuffle);
        ibt_previous = (ImageButton)findViewById(R.id.button_previous);
        ibt_play = (ImageButton)findViewById(R.id.button_play);
        ibt_next = (ImageButton)findViewById(R.id.button_next);
        ibt_repeat = (ImageButton)findViewById(R.id.button_repeat);
        tv_songName.setText(title);
        tv_artistName.setText(artist);
        setOnButtonClick();


    }

    private void setOnButtonClick() {
        ibt_play.setOnClickListener(this);
        ibt_previous.setOnClickListener(this);
        ibt_next.setOnClickListener(this);
        ibt_repeat.setOnClickListener(this);
        ibt_shuffle.setOnClickListener(this);
        sb_timeProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int pgr = (seekBar.getProgress()<fullTime-1000)?seekBar.getProgress():(fullTime-1000);
                FloatingControlWindow.pService.getMediaPlayer().seekTo(pgr);
            }
        });
    }






    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_play:
                playAction();
                break;
            case R.id.button_previous:
                break;
            case R.id.button_next:
                nextAction();
                break;
            case R.id.button_shuffle:
                break;
            case R.id.button_repeat:
                repeatAction();
                break;
        }
    }

    private void repeatAction() {

    }

    private void playAction() {
        if (FloatingControlWindow.pService.getMediaPlayer().isPlaying()){
            ibt_play.setImageResource(R.drawable.ic_play_circle_outline_red_300_18dp);
            FloatingControlWindow.pService.getMediaPlayer().pause();
        }
        else {
            ibt_play.setImageResource(R.drawable.ic_pause_circle_outline_red_300_18dp);
            FloatingControlWindow.pService.getMediaPlayer().start();
        }
    }


    private class PlayingMusicReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("updateSeekBar")){
                fullTime = intent.getIntExtra("fullTime", 0);
                sb_timeProgress.setMax(fullTime);
                eslapedTime = intent.getStringExtra("eslapsedTime");
                remainingTime = intent.getStringExtra("timeRemaining");
                progressTime = intent.getIntExtra("progress", 0);
                sb_timeProgress.setProgress(progressTime);
                tv_eslapedTime.setText(eslapedTime);
                tv_timeLeft.setText(remainingTime);
                Log.d("music", "isplaying");
            }
            else if (intent.getAction().equals("updateSong")){
                tv_songName.setText(intent.getStringExtra("title"));
                tv_artistName.setText(intent.getStringExtra("artist"));
            }
        }
    }

    private void nextAction() {
        Intent nextSongIntent = new Intent();
        if (isShuffle){
            nextSongIntent.setAction("nextRand");
        }
        else {
            nextSongIntent.setAction("next");
        }
        sendBroadcast(nextSongIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isDestroyed = true;
    }
}
