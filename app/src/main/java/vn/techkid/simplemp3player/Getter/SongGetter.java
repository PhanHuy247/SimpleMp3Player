package vn.techkid.simplemp3player.Getter;

import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by Laptop88 on 8/3/2016.
 */
public class SongGetter extends AsyncTask<Void, Void, Void>{
    private String url;
    private int pos;

    public SongGetter(String url, int pos) {
        this.url = url;
        this.pos = pos;
    }

    public String getUrl() {
        return url;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document doc = Jsoup.connect(url).get();
            Element e = doc.select ("span.gen").get(2*pos+1).select("a").first();
//            Log.d("hehehehe", e.attr("href"));
            Document document = Jsoup.connect(e.attr("href")).get();
            Elements elements = document.select("div#downloadlink").select("b").select("script");
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
