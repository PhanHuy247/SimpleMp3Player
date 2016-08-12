package vn.techkid.simplemp3player.Service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Activity.ChartSong;
import vn.techkid.simplemp3player.Activity.PlayerActivity;
import vn.techkid.simplemp3player.Getter.SongGetter;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;

/**
 * Created by Laptop88 on 8/10/2016.
 */
public class FloatingControlWindow extends Service implements View.OnClickListener{
    public  WindowManager windowManager;
    LinearLayout linearLayout;
    private ImageButton imageSong;
    private int currentPos;
    ArrayList<Song> songs = new ArrayList<>();
    private String url;
    private MusicControlReceiver receiver;
    ServiceConnection connection;
    public static PlayingMusicService pService = null;
    boolean isBound;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initView();



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getSongListInfo(intent);
        get320kDownloadLink(currentPos);
        if (pService!=null){
            refreshService();
            pService.stopSelf();
        }
        setUpService();
        setBroadcastReceiver();
        setUpPlayer();
        return START_NOT_STICKY;
    }

    private void setUpPlayer() {
        Intent i = new Intent(this, PlayerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);

    }



    private void setBroadcastReceiver() {
        receiver = new MusicControlReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("nextRand");
        filter.addAction("next");
        registerReceiver(receiver, filter);
    }

    private void initView() {
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        linearLayout = new LinearLayout(this);
        imageSong = new ImageButton(this);

        ViewGroup.LayoutParams iParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageSong.setImageResource(R.drawable.image_music);
        imageSong.setLayoutParams(iParams);
        imageSong.setOnClickListener(this);

        LinearLayout.LayoutParams llParameters = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        linearLayout.setBackgroundColor(Color.WHITE);
        linearLayout.setLayoutParams(llParameters);

        WindowManager.LayoutParams  params = new WindowManager.LayoutParams (LinearLayout.LayoutParams.MATCH_PARENT, 100, WindowManager.LayoutParams.TYPE_PHONE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, PixelFormat.TRANSLUCENT);
        params.gravity = Gravity.BOTTOM;

        linearLayout.addView(imageSong);
//        windowManager.addView(linearLayout, params);
    }
    private void getSongListInfo(Intent intent) {
        if (intent.getStringExtra("key").equals("chartSong")){
            songs = ChartSong.songs;
            currentPos = intent.getIntExtra("pos", 0);
        }
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


    }

    private class MusicControlReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("nextRand")){

            }
            else if (intent.getAction().equals("next")){
                currentPos = (currentPos+1)%20;
                pService.stopSelf();
                get320kDownloadLink(currentPos);
                Intent updateSongIntent = new Intent(getApplicationContext(), PlayingMusicService.class);
                updateSongIntent.putExtra("title", songs.get(currentPos).getTitle());
                updateSongIntent.putExtra("artist", songs.get(currentPos).getArtist());
                updateSongIntent.putExtra("url", url);
                startService(updateSongIntent);

            }
        }
    }
    private void refreshService() {
        if (pService.getMediaPlayer()!=null){
            pService.getMediaPlayer().stop();
            pService.getMediaPlayer().release();
            pService.setMediaPlayer(null);
            Log.d("check1", "refreshService");
        }
    }
    private void setUpService() {

        // Khởi tạo ServiceConnection
        connection = new ServiceConnection() {

            // Phương thức này được hệ thống gọi khi kết nối tới service bị lỗi
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }

            // Phương thức này được hệ thống gọi khi kết nối tới service thành công
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                PlayingMusicService.MyBinder binder = (PlayingMusicService.MyBinder) service;
                pService = binder.getService(); // lấy đối tượng MyService
            }
        };

        startNewMusicService();

    }

    private void startNewMusicService (){
        Intent intent = new Intent(this, PlayingMusicService.class);
        intent.putExtra("url", url);
        intent.putExtra("title", songs.get(currentPos).getTitle());
        intent.putExtra("artist", songs.get(currentPos).getArtist());
        startService(intent);
        if (isBound==false){
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
        Log.d("check2","startNewMusicService");
    }
}
