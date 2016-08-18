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
import android.os.Bundle;
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

import vn.techkid.simplemp3player.Activity.PlayerActivity;
import vn.techkid.simplemp3player.Getter.SongGetter;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;

/**
 * Created by Laptop88 on 8/10/2016.
 */
public class FloatingControlWindow extends Service implements View.OnClickListener{

    private static int currentPos;

    private String url;
    public static ArrayList<Song> arrayList;
//    private MusicControlReceiver receiver;
    ServiceConnection connection;
    public static PlayingMusicService pService = null;
    boolean isBound;
    private static String key;

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        FloatingControlWindow.key = key;
    }

    public static int getCurrentPos() {
        return currentPos;
    }

    public static void setCurrentPos(int currentPos) {
        FloatingControlWindow.currentPos = currentPos;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        initView();



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        getSongListInfo(intent);
        if (pService!=null){
            refreshService();
            pService.stopSelf();
        }
        setUpService();
        setUpPlayer();
        return START_NOT_STICKY;
    }

    private void setUpPlayer() {
        Intent i = new Intent(this, PlayerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }




    private void getSongListInfo(Intent intent) {
        Bundle bundle = intent.getBundleExtra("bundle");
        key = bundle.getString("key");
        currentPos = bundle.getInt("pos", 0);
        arrayList = (ArrayList<Song>) bundle.getSerializable("list");
    }

    @Override
    public void onClick(View v) {


    }
    private void refreshService() {
        if (pService.getMediaPlayer()!=null){

            pService.getMediaPlayer().release();

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
        startService(intent);
        if (isBound==false){
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
            isBound = true;
        }
        Log.d("check2","startNewMusicService");
    }
}
