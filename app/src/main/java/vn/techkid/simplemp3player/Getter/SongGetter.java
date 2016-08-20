package vn.techkid.simplemp3player.Getter;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import vn.techkid.simplemp3player.Model.Artist;

/**
 * Created by Laptop88 on 8/3/2016.
 */
public class SongGetter extends AsyncTask<Void, Void, Void>{
    private String url;
    String remaxURL;
    private int pos;
    boolean isHot;
    public ArrayList<String> listUrl = new ArrayList<>();
    public SongGetter(String url, int pos,boolean isHot) {
        this.url = url;
        this.pos = pos;
        this.isHot = isHot;
    }

    public String getUrl() {
        return remaxURL;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document document = null;
            Log.d("khuongtunha", url);
            if (isHot){
                Document doc = Jsoup.connect(url).get();
                Element e = doc.select ("span.gen").get(2*pos+1).select("a").first();
                url = e.attr("href");
                Log.d("khuongtunha", e.attr("href"));
            }
            else {
                url = url.substring (0, url.length()-5) + "_download.html";
                Log.d("khuongtunha", url);
            }
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            String html = "";
            InputStream in = response.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null)
            {
                str.append(line);
            }
            in.close();
            html = str.toString();
            document = Jsoup.parse(html);
            Elements elements = document.select("div#downloadlink").select("b").select("script");
            String text = elements.get(1).data();
            String temp[] = text.split("a href=\"", 6);
            for (String s:temp){
                if (s.startsWith("http") && s.contains("128kbps")){
                    int i = s.indexOf(".mp3");
                    listUrl.add(s.substring(0, i+4));
                }
                if (s.startsWith("http") && s.contains("320kbps")){
                    int i = s.indexOf(".mp3");
                    remaxURL = s.substring(0, i+4);
                    listUrl.add(remaxURL);
                }
                if(s.startsWith("http") && s.contains("500kbps")){
                    url = remaxURL;
                    url = url.replace("320kbps","500kbps");
                    url = url.replace("320/","m4a/");
                    url = url.replace(".mp3",".m4a");
                    url = url.replace("MP3","M4A");
                    listUrl.add(url);
                }
                if(s.startsWith("http") && s.contains("Lossless")){
                    url = remaxURL;
                    url = url.replace("320kbps","lossless");
                    url = url.replace("320/","flac/");
                    url = url.replace(".mp3",".flac");
                    url = url.replace("MP3","FLAC");
                    listUrl.add(url);
                }
                if(s.startsWith("http") && s.contains("32kbps")){
                    url = remaxURL;
                    url = url.replace("320kbps","32kbps");
                    url = url.replace("320/","32/");
                    url = url.replace(".mp3",".m4a");
                    url = url.replace("MP3","M4A");
                    listUrl.add(url);
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
