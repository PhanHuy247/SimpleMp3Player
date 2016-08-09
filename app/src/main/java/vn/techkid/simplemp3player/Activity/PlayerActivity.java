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
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Getter.SongGetter;
import vn.techkid.simplemp3player.Service.PlayingMusicService;

public class PlayerActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView tv_songName, tv_artistName;
    private TextView tv_eslapedTime, tv_timeLeft;
    private SeekBar sb_timeProgress;
    private ImageButton ibt_shuffle, ibt_previous, ibt_play, ibt_next, ibt_repeat;
    private String eslapedTime, remainingTime;
    private int progressTime, fullTime;
    String url;
    boolean isBound;
    boolean isShuffle, isLooping, isRepeat;
    int startPos;
    static PlayingMusicService pService = null;
    ArrayList<Song> songs = new ArrayList<>();
    ServiceConnection connection;
    PlayingMusicReceiver receiver;
    static boolean isCompleted;
    static int currentPos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSongListInfo();
        initView();
        get320kDownloadLink(currentPos);
        if (pService!=null){
            refreshService();
        }

        setUpService();

    }

    private void refreshService() {
        if (pService.getMediaPlayer()!=null){
            pService.getMediaPlayer().stop();
            pService.getMediaPlayer().release();
            pService.setMediaPlayer(null);
            Log.d("check1", "refreshService");
        }
    }

    private void getSongListInfo() {
        if (getIntent().getBooleanExtra("playlist", false)){
            ArrayList<CharSequence> titles = getIntent().getCharSequenceArrayListExtra("titles");
            ArrayList<CharSequence> artists = getIntent().getCharSequenceArrayListExtra("artists");
            ArrayList<CharSequence> urls = getIntent().getCharSequenceArrayListExtra("urls");
            for (int i = 0; i < 20; i++) {
                Song song = new Song((String)titles.get(i), (String)artists.get(i), (String)urls.get(i), i);
                songs.add(song);
            }
            currentPos = getIntent().getIntExtra("pos", 0);
            Log.d("pos", currentPos+"");

        }
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
        tv_songName.setText(songs.get(currentPos).getTitle());
        tv_artistName.setText(songs.get(currentPos).getArtist());
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
                pService.getMediaPlayer().seekTo(seekBar.getProgress());



            }
        });
    }



    private void setUpService() {

        // Khởi tạo ServiceConnection
         connection = new ServiceConnection() {

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

        startNewMusicService();

    }

    private void startNewMusicService (){
        Intent intent = new Intent(this, PlayingMusicService.class);
        intent.putExtra("url", url);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        receiver = new PlayingMusicReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("updateSeekBar");
        filter.addAction("completed");
        registerReceiver(receiver, filter);
        Log.d("check2","startNewMusicService");
    }


    private void get320kDownloadLink(int pos) {
        Log.d("check3", "get320kDownloadLink");
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
        if (pService.getMediaPlayer().isPlaying()){
            ibt_play.setImageResource(R.drawable.ic_play_circle_outline_red_300_18dp);
            pService.getMediaPlayer().pause();
        }
        else {
            ibt_play.setImageResource(R.drawable.ic_pause_circle_outline_red_300_18dp);
            pService.getMediaPlayer().start();
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
            }
            else if (intent.getAction().equals("completed")){
                nextAction();
            }
        }
    }

    private void nextAction() {
        Log.d("check00", "nextAction");
        refreshService();
        if (isShuffle){

        }
        else {
            currentPos= (currentPos++)%20;
            Log.d("currentPos", currentPos+"");

        }
        get320kDownloadLink(currentPos);
        unbindService(connection);
        startNewMusicService();
        tv_songName.setText(songs.get(currentPos).getTitle());
        tv_artistName.setText(songs.get(currentPos).getArtist());

    }
}
