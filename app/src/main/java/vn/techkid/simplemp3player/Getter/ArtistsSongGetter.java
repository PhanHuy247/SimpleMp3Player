package vn.techkid.simplemp3player.Getter;

import android.os.AsyncTask;

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
public class ArtistsSongGetter extends AsyncTask<Void, Void, Void> {
    public ArrayList<Song> songs;
    private String url;
    public ArtistsSongGetter(String url) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... strings) {
        songs = new ArrayList<>();

        int length = 20;
        try {
            Document document =  Jsoup.connect(url).get();
            Elements subjectElements = document.select("div.tenbh");
            Elements categoryElements = document.select("span.gen");
            if (subjectElements != null && subjectElements.size() > 0) {
                if(subjectElements.size() < 20) {
                    length = subjectElements.size();
                }
                for (int i = 0; i< length; i++) {
                    Element titleSubject = subjectElements.get(i).getElementsByTag("a").first();
                    Element artistSubject = subjectElements.get(i).getElementsByTag("p").last();
                    Element categorySubject = categoryElements.get(i).getElementsByTag("span").last();
                    if (titleSubject != null) {
                        String link = titleSubject.attr("href");
                        String name = titleSubject.text();
                        String artist = artistSubject.text();
                        String category = categorySubject.text();
                        songs.add(new Song(name,artist,category,"http://chiasenhac.vn/"+link,i));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

