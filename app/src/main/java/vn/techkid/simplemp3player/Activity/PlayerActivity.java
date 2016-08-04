package vn.techkid.simplemp3player.Activity;

import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Getter.SongGetter;

public class PlayerActivity extends AppCompatActivity {
    private TextView tv_songName, tv_artistName;
    private TextView tv_eslapedTime, tv_timeLeft;
    private SeekBar sb_timeProgress;
    private ImageButton ibt_shuffle, ibt_previous, ibt_play, ibt_next, ibt_repeat;
    private MediaPlayer mediaPlayer;
    private Handler durationHandler = new Handler();
    private int eslapedTime, fullTime;
    String url;
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
        ibt_shuffle = (ImageButton)findViewById(R.id.button_random);
        ibt_previous = (ImageButton)findViewById(R.id.button_previous);
        ibt_play = (ImageButton)findViewById(R.id.button_play);
        ibt_next = (ImageButton)findViewById(R.id.button_next);
        ibt_repeat = (ImageButton)findViewById(R.id.button_repeat);
        url = getIntent().getStringExtra("url");
        Log.d("123",url);
        tv_songName.setText(getIntent().getStringExtra("title"));
        tv_artistName.setText(getIntent().getStringExtra("artist"));
        get320kDownloadLink();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = new MediaPlayer();
        try {
            Log.d("lm", url);
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fullTime = mediaPlayer.getDuration();
        sb_timeProgress.setMax(fullTime);
        play();
    }

    private void get320kDownloadLink() {
        SongGetter getter = new SongGetter(url);
        try {
            getter.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        url = getter.getUrl();


    }



    public void play() {
        mediaPlayer.start();
        eslapedTime = mediaPlayer.getCurrentPosition();
        sb_timeProgress.setProgress(eslapedTime);
        durationHandler.postDelayed(updateSeekBarTime, 100);

    }

    //handler to change seekBarTime
    private Runnable updateSeekBarTime = new Runnable() {
        public void run() {
            //get current position
            eslapedTime = mediaPlayer.getCurrentPosition();
            //set seekbar progress
            sb_timeProgress.setProgress(eslapedTime);
            //set time remaing
            int timeRemaining = fullTime - eslapedTime;
            int minutesEslaped = (int) TimeUnit.MILLISECONDS.toMinutes((long)eslapedTime);
            int secondsEslaped = (int) (TimeUnit.MILLISECONDS.toSeconds((long) eslapedTime)-TimeUnit.MINUTES.toSeconds((long)minutesEslaped));
            tv_eslapedTime.setText(String.format("%d:%d", minutesEslaped, secondsEslaped));
            int minutesRemaining = (int) TimeUnit.MILLISECONDS.toMinutes((long)timeRemaining);
            int secondsRemaining = (int) (TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)-TimeUnit.MINUTES.toSeconds((long)minutesRemaining));
            tv_timeLeft.setText(String.format("%d:%d", minutesRemaining, secondsRemaining));
            //repeat yourself that again in 100 miliseconds
            durationHandler.postDelayed(this, 100);

        }
    };
}
