package vn.techkid.simplemp3player.Service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Activity.ChartSongKorea;
import vn.techkid.simplemp3player.Activity.ChartSongUSUK;
import vn.techkid.simplemp3player.Activity.ChartSongVietNam;
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        getSongListInfo(intent);
        get320kDownloadLink(currentPos);
        Intent i = new Intent(this, PlayerActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("title", songs.get(currentPos).getTitle());
        i.putExtra("artist", songs.get(currentPos).getArtist());
        i.putExtra("url", url);
        startActivity(i);
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initView();
        setBroadcastReceiver();


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
        switch (intent.getStringExtra("key")){
            case "vn":
                songs = ChartSongVietNam.songs;
                currentPos = intent.getIntExtra("pos", 0);
                break;
            case "us-uk":
                songs = ChartSongUSUK.songs;
                currentPos = intent.getIntExtra("pos", 0);
                break;
            case "ko":
                songs = ChartSongKorea.songs;
                currentPos = intent.getIntExtra("pos", 0);
                break;

        }
//        if (intent.getBooleanExtra("playlist", false)){
//            ArrayList<CharSequence> titles = intent.getCharSequenceArrayListExtra("titles");
//            ArrayList<CharSequence> artists = intent.getCharSequenceArrayListExtra("artists");
//            ArrayList<CharSequence> urls = intent.getCharSequenceArrayListExtra("urls");
//            for (int i = 0; i < 20; i++) {
//                Song song = new Song((String)titles.get(i), (String)artists.get(i), (String)urls.get(i), i);
//                songs.add(song);
//            }
//            currentPos = intent.getIntExtra("pos", 0);
//            Log.d("pos", currentPos+"");
//
//        }
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
                currentPos++;
                get320kDownloadLink(currentPos);
                Intent updateSongIntent = new Intent();
                updateSongIntent.setAction("updateSong");
                updateSongIntent.putExtra("title", songs.get(currentPos).getTitle());
                updateSongIntent.putExtra("artist", songs.get(currentPos).getArtist());
                updateSongIntent.putExtra("url", url);
                sendBroadcast(updateSongIntent);
            }
        }
    }
}
