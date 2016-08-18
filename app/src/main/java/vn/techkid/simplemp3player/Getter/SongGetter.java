package vn.techkid.simplemp3player.Getter;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Laptop88 on 8/3/2016.
 */
public class SongGetter extends AsyncTask<Void, Void, Void>{
    private String url;

    public SongGetter(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        url = url.substring (0, url.length()-5) + "_download.html";
        try {
            Document document = Jsoup.connect(url).get();
            Elements elements = document.select("div.tip-text").select("b").select("script");
            String text = elements.get(1).data();
            String temp[] = text.split("a href=\"", 6);
            for (String s:temp){
                if (s.startsWith("http") && s.contains("320kbps")){
                    int i = s.indexOf(".mp3");
                    url = s.substring(0, i+4);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
             }
        return null;
    }
}
