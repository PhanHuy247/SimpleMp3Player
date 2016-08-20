package vn.techkid.simplemp3player.Getter;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import vn.techkid.simplemp3player.Model.Song;

/**
 * Created by Laptop88 on 8/20/2016.
 */
public class AlbumSongListGetter extends AsyncTask<Void, Void, Void> {
    public ArrayList<Song> songs;
    private String url;

    public AlbumSongListGetter(String url) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... strings) {
        songs = new ArrayList<>();
        try {
            Document document = Jsoup.connect(url).get();
            Log.d("thangtrang", url);
            Elements elements = document.select("span.gen");
            Log.d("thangkhuong", elements.size()+"");
            int i;
            for (i = 0; i < elements.size(); i++) {
                if (elements.get(i).text().startsWith("Link")){
                    break;
                }
            }
            for (int j = 0; j < i/2; j++){
                Log.d("thangtrang1", elements.get(2*j+1).select("a").size()+"");
                Element e1 = elements.get(2*j+1).select("a").get(2);
                String title, artist, link;
                title = e1.text();
                artist = elements.get(2*j+1).text();
                artist = artist.substring(artist.indexOf("-")+2);
                link = elements.get(2*j+1).select("a").get(1).attr("href");
                songs.add(new Song(title, artist, "http://chiasenhac.vn/"+link, j));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
