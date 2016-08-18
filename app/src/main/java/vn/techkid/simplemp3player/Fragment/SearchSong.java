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
public class SearchSong extends Fragment {
    public static final String URL = "http://search.chiasenhac.vn/search.php?s=";
    String query;
    DownloadTask task;
    public ListView lv_songs;
    AdapterChartSong adaper;
    private static ArrayList<Song> songs;
    public static final String KEY = "search";
    public SearchSong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        MainActivity.isAlive = true;
        // Inflate the layout for this fragment
        getActivity().setTitle(query);
        View view = inflater.inflate(R.layout.fragment_search_song, container, false);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        handleIntent();
        initView(view);
    }

    public void setQuery(String query) {
        this.query = query;
    }

    private void initView(View view) {
        lv_songs = (ListView) view.findViewById(R.id.listchartvietnamsearch);
        task = new DownloadTask();
        try {
            task.execute(URL+query).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        songs = task.listNews;
        adaper = new AdapterChartSong(songs);
        lv_songs.setAdapter(adaper);
        lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),  FloatingControlWindow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("key",KEY);
                bundle.putSerializable("list",songs);
                intent.putExtra("bundle",bundle);
                getActivity().startService(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private String normalizedString(String str){
        str = str.trim();
        str = str.replaceAll("\\s+", " ");
        str = str.replace(' ','+');
        return str;
    }

    private void handleIntent() {
        query = normalizedString(query);
    }
    static class DownloadTask extends AsyncTask<String, Void, Void> {
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
