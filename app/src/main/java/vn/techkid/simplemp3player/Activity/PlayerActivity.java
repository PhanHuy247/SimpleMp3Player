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

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Getter.SongGetter;
import vn.techkid.simplemp3player.Service.PlayingMusicService;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_songName, tv_artistName;
    private TextView tv_eslapedTime, tv_timeLeft;
    private SeekBar sb_timeProgress;
    private ImageButton ibt_shuffle, ibt_previous, ibt_play, ibt_next, ibt_repeat;
    private String eslapedTime, remainingTime;
    private float progressTime;
    String url;
    boolean isBound;
    PlayingMusicService pService = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initView();
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

        tv_songName.setText(getIntent().getStringExtra("title"));
        tv_artistName.setText(getIntent().getStringExtra("artist"));
        setOnButtonClick();
        get320kDownloadLink();
        setUpService();

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
                if (fromUser){
                    pService.getMediaPlayer().seekTo(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setUpService() {

        // Khởi tạo ServiceConnection
        ServiceConnection connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {
                isBound = false;
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayingMusicService.MyBinder binder = (PlayingMusicService.MyBinder) service;
                pService = binder.getService(); // lấy đối tượng MyService
                isBound = true;
            }
        };

        Intent intent = new Intent(this, PlayingMusicService.class);
        intent.putExtra("url", url);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        sb_timeProgress.setMax(1000);
        PlayingMusicReceiver receiver = new PlayingMusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("updateSeekBar");
        registerReceiver(receiver, filter);
    }


    private void get320kDownloadLink() {
        SongGetter getter = new SongGetter(getIntent().getStringExtra("url"));
        try {
            getter.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        url = getter.getUrl();


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
        if (pService.getMediaPlayer().isPlaying()){
            ibt_play.setImageResource(R.drawable.ic_pause_circle_outline_red_300_18dp);
            pService.getMediaPlayer().pause();
        }
        else {
            ibt_play.setImageResource(R.drawable.ic_play_circle_outline_red_300_18dp);
            pService.getMediaPlayer().start();
        }
    }


    private class PlayingMusicReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("updateSeekBar")){
                eslapedTime = intent.getStringExtra("eslapsedTime");
                remainingTime = intent.getStringExtra("timeRemaining");
                progressTime = intent.getFloatExtra("progress", 0.0f);
                sb_timeProgress.setProgress((int)(progressTime));
                tv_eslapedTime.setText(eslapedTime);
                tv_timeLeft.setText(remainingTime);
            }
        }
    }
}
