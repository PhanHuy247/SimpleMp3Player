package vn.techkid.simplemp3player.Activity;

import android.content.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;


import vn.techkid.simplemp3player.R;

import vn.techkid.simplemp3player.Service.FloatingControlWindow;
import vn.techkid.simplemp3player.Service.PlayingMusicService;


public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{
    public TextView tv_songName, tv_artistName;
    private TextView tv_eslapedTime, tv_timeLeft;
    private SeekBar sb_timeProgress;
    private ImageButton ibt_shuffle, ibt_previous, ibt_play, ibt_next, ibt_repeat, ibt_download;
    private String eslapedTime, remainingTime;
    private int progressTime, fullTime;


    public static boolean isShuffle, isLooping, isRepeat;
    PlayingMusicReceiver receiver;
    public static boolean isBackPressed;
    public static boolean isShowing;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
        setBroadcastReceiver();
    }

    private void setBroadcastReceiver() {
        receiver = new PlayingMusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("updateSeekBar");
        registerReceiver(receiver, filter);
    }



    private void initView() {

        tv_songName = (TextView)findViewById(R.id.text_songName);
        tv_artistName = (TextView)findViewById(R.id.text_artist);
        tv_eslapedTime = (TextView)findViewById(R.id.text_eslapedTime);
        tv_timeLeft = (TextView)findViewById(R.id.text_timeLeft);
        sb_timeProgress = (SeekBar)findViewById(R.id.seekBar_progressBar);
        ibt_shuffle = (ImageButton)findViewById(R.id.button_shuffle);
        if (isShuffle){
            ibt_shuffle.setImageResource(R.drawable.ic_shuffle_red_200_18dp);
        }
        ibt_previous = (ImageButton)findViewById(R.id.button_previous);
        ibt_play = (ImageButton)findViewById(R.id.button_play);
        ibt_next = (ImageButton)findViewById(R.id.button_next);
        ibt_repeat = (ImageButton)findViewById(R.id.button_repeat);
        if (isRepeat){
            if (isLooping){
                ibt_repeat.setImageResource(R.drawable.ic_repeat_one_red_200_18dp);
            }
            else {
                ibt_repeat.setImageResource(R.drawable.ic_repeat_red_200_18dp);
            }
        }
        ibt_download = (ImageButton)findViewById(R.id.button_download);
        setOnButtonClick();


    }

    private void setOnButtonClick() {
        ibt_play.setOnClickListener(this);
        ibt_previous.setOnClickListener(this);
        ibt_next.setOnClickListener(this);
        ibt_repeat.setOnClickListener(this);
        ibt_shuffle.setOnClickListener(this);
        ibt_download.setOnClickListener(this);
        sb_timeProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (FloatingControlWindow.pService.getMediaPlayer()!=null) {
                    int pgr = (seekBar.getProgress() < fullTime - 1000) ? seekBar.getProgress() : (fullTime - 1000);
                    FloatingControlWindow.pService.getMediaPlayer().seekTo(pgr);
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        isShowing = true;
        isBackPressed = false;
    }

    @Override
    public void onClick(View v) {
        if (FloatingControlWindow.pService.getMediaPlayer()!=null){
            switch (v.getId()){
                case R.id.button_play:
                    playAction();
                    break;
                case R.id.button_previous:
                    previousAction();
                    break;
                case R.id.button_next:
                    nextAction();
                    break;
                case R.id.button_shuffle:
                    shuffleAction();
                    break;
                case R.id.button_repeat:
                    repeatAction();
                    break;
            }
        }

    }

    private void shuffleAction() {
        if (isShuffle){
            isShuffle = false;
            ibt_shuffle.setImageResource(R.drawable.ic_shuffle_red_300_18dp);
        }
        else {
            isShuffle = true;
            ibt_shuffle.setImageResource(R.drawable.ic_shuffle_red_200_18dp);
        }
    }

    private void repeatAction() {
        if (isRepeat){
            isLooping = true;
            isRepeat = false;
            ibt_repeat.setImageResource(R.drawable.ic_repeat_one_red_200_18dp);
        }
        else {
            if (isLooping){
                isLooping = false;
                ibt_repeat.setImageResource(R.drawable.ic_repeat_red_300_18dp);
            }
            else {
                isRepeat = true;
                ibt_repeat.setImageResource(R.drawable.ic_repeat_red_200_18dp);
            }
        }
    }

    private void previousAction() {
        FloatingControlWindow.pService.getMediaPlayer().reset();
        if (isShuffle){
//            int i = FloatingControlWindow.pService.helperClass.getRandomPos();
//            FloatingControlWindow.pService.setCurrentPos(i);
        }
        else {
            int i = FloatingControlWindow.pService.getCurrentPos();
            FloatingControlWindow.pService.setCurrentPos((i-1)%20);
        }
        int currentPos = FloatingControlWindow.pService.getCurrentPos();
        FloatingControlWindow.pService.get320kDownloadLink(currentPos);
        FloatingControlWindow.pService.setMediaPlayer();
    }

    private void playAction() {
        if (FloatingControlWindow.pService.getMediaPlayer().isPlaying()){
            ibt_play.setImageResource(R.drawable.ic_play_circle_outline_red_300_18dp);
            FloatingControlWindow.pService.getMediaPlayer().pause();
        }
        else {
            if (PlayingMusicService.isWait){
                PlayingMusicService.isWait = false;
            }
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
                tv_songName.setText(intent.getStringExtra("title"));
                tv_artistName.setText(intent.getStringExtra("artist"));
                Log.d("music", "isplaying");
                if (FloatingControlWindow.pService.getMediaPlayer().isPlaying()){
                    ibt_play.setImageResource(R.drawable.ic_pause_circle_outline_red_300_18dp);

                }
                else {
                    ibt_play.setImageResource(R.drawable.ic_play_circle_outline_red_300_18dp);
                }
            }
        }
    }

    private void nextAction() {
        FloatingControlWindow.pService.getMediaPlayer().reset();
        if (!isLooping){
            FloatingControlWindow.pService.helperClass
                    .integers.remove((Integer)FloatingControlWindow.pService.getCurrentPos());

            if (FloatingControlWindow.pService.helperClass.integers.size()==0){
                for (int i = 0; i < PlayingMusicService.maxSongs; i++) {
                    FloatingControlWindow.pService.helperClass
                            .integers.add(i);
                }
                if (!PlayerActivity.isRepeat){
                    PlayingMusicService.isWait = true;

                }
            }
            if (isShuffle){
                int i = FloatingControlWindow.pService.helperClass.getRandomPos();
                FloatingControlWindow.pService.setCurrentPos(i);

            }
            else {
                int i = FloatingControlWindow.pService.getCurrentPos();
                FloatingControlWindow.pService.setCurrentPos((i+1)%20);

            }

        }
        int currentPos = FloatingControlWindow.pService.getCurrentPos();
        FloatingControlWindow.pService.get320kDownloadLink(currentPos);
        FloatingControlWindow.pService.setMediaPlayer();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        isBackPressed = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isBackPressed){
            MainActivity.isForceClose = true;
            Intent intent = new Intent();
            intent.setAction("finish");
            sendBroadcast(intent);
        }
        isShowing = false;
    }
}
