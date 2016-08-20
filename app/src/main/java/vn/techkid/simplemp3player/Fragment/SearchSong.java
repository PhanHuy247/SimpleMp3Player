package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Activity.MainActivity;
import vn.techkid.simplemp3player.Adapter.AdapterChartSong;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchSong extends ListSongsFragment {
    public static final String URL = "http://search.chiasenhac.vn/search.php?s=";
    String query;
    DownloadTask task;
    public SearchSong() {
        intentKey = "search";
        isHot = false;

    }


    public void setQuery(String query) {
        this.query = query;
        title = query;
    }


    @Override
    public void setupAsynTask() {
        query = normalizedString(query);
        task = new DownloadTask();
        try {
            task.execute(URL+query).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private String normalizedString(String str){
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        str = str.replace(' ','+');
        return str;
    }

    private class DownloadTask extends AsyncTask<String, Void, Void> {
        ArrayList<Song> listNews;
        public DownloadTask(){

        }
        @Override
        protected Void doInBackground(String... strings) {
            listNews = new ArrayList<>();
            Document document = null;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                Elements subjectElements = document.select("div.tenbh");
                if (subjectElements != null && subjectElements.size() > 0) {
                    for (int i = 0; i < 20; i++) {
                        Element titleSubject = subjectElements.get(i).getElementsByTag("a").first();
                        Element artistSubject = subjectElements.get(i).getElementsByTag("p").last();
                        if (titleSubject != null) {
                            String title = titleSubject.text();
                            String link = titleSubject.attr("href");
                            String artist = artistSubject.text();
                            listNews.add(new Song(title, artist,"http://chiasenhac.vn/"+link,i));
                            Log.d("meHuy", link);
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

}
