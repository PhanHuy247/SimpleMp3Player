package vn.techkid.simplemp3player;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vn.techkid.simplemp3player.Activity.ChartSongVietNam;
import vn.techkid.simplemp3player.Activity.PlayerActivity;
import vn.techkid.simplemp3player.Adapter.AdapterSong;
import vn.techkid.simplemp3player.Model.Song;

/**
 * Created by Laptop88 on 8/1/2016.
 */
public class PlaylistGetter extends AsyncTask<Void, Void, Void> {
    String url;
    public ArrayList<Song> songs;

    public PlaylistGetter(String url) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        songs = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("div[class=text2 text2x]");
            Log.d("hey", elements.size()+"");

            for (int i = 0; i < elements.size(); i++) {
                Elements elements1 = elements.get(i).select("a");
                Elements elements2 = elements.get(i).select("p");
                String title = elements1.text();
                String artist = elements2.text();
                String linkSong = elements1.attr("href");
                Log.d("link", linkSong);
                Song song = new Song(title, artist, linkSong, i+1);
                songs.add(song);
                Log.d("tag", songs.size()+"");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
