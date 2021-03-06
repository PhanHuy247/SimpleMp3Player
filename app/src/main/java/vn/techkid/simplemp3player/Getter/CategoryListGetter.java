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
public class CategoryListGetter extends AsyncTask<Void, Void, Void> {
    public ArrayList<Song> songs;
    private String url;

    public CategoryListGetter(String url) {
        this.url = url;
    }

    @Override
    protected Void doInBackground(Void... strings) {
        songs = new ArrayList<>();
        Document document;
        int length = 20;
        try {
            document = Jsoup.connect(url).get();
            Elements subjectElements = document.select("div.text2");
            Elements categoryElements = document.select("div.texte2");
            if (subjectElements != null && subjectElements.size() > 0) {
                if(subjectElements.size() < 20) {
                    length = subjectElements.size();
                }
                for (int i = 0; i < length; i++) {
                    Element titleSubject = subjectElements.get(i).getElementsByTag("a").first();
                    Element artistSubject = subjectElements.get(i).getElementsByTag("p").first();
                    Element categorySubject = categoryElements.get(i).getElementsByTag("p").last();
                    if (titleSubject != null && artistSubject != null) {
                        String title = titleSubject.text();
                        String link = titleSubject.attr("href");
                        String artist = artistSubject.text();
                        String category = categorySubject.text();
                        songs.add(new Song(i,link,title,category,artist));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
