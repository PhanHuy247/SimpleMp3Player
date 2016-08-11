package vn.techkid.simplemp3player.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Adapter.AdapterSong;
import vn.techkid.simplemp3player.Getter.PlaylistGetter;
import vn.techkid.simplemp3player.Model.Song;

import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;


public class ChartSong extends AppCompatActivity{
    private Toolbar toolbar;
    public  ListView lv_songs;
    public static ArrayList<Song> songs;
    ArrayList<CharSequence> titles = new ArrayList<>();
    ArrayList<CharSequence> artists = new ArrayList<>();
    ArrayList<CharSequence> urls = new ArrayList<>();
    public String intentKey = "chartSong";



    String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_song_viet_nam);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initView();
    }

    private void initView() {
        lv_songs = (ListView) findViewById(R.id.listchartvietnam);
        URL = getIntent().getStringExtra("URL");
        PlaylistGetter getter = new PlaylistGetter(URL);
        try {
            getter.execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        songs = getter.songs;
        for (Song song:songs){
            titles.add(song.getTitle());
            artists.add(song.getArtist());
            urls.add(song.getAccessLink());

        }
        AdapterSong adaper = new AdapterSong(songs);
        lv_songs.setAdapter(adaper);
        lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), FloatingControlWindow.class);
//                intent.putCharSequenceArrayListExtra("titles", titles);
//                intent.putCharSequenceArrayListExtra("artists", artists);
//                intent.putCharSequenceArrayListExtra("urls", urls);
                intent.putExtra("pos", position);
//                intent.putExtra("playlist", true);
                intent.putExtra("key", intentKey);
                startService(intent);

            }
        });


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_chartsongvietnam, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
