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
            Elements elements = document.select("div[id=downloadlink]").select("b").select("a");
            for (int i = 0; i < elements.size(); i++) {
                String link = elements.get(i).attr("href");
                Log.d("aaa", link);
                if (link.contains("320kbps")){
                    url = link;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
