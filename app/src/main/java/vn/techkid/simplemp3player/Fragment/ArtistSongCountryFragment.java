package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Activity.PlayerActivity;
import vn.techkid.simplemp3player.Adapter.AdapterSongArtist;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistSongCountryFragment extends Fragment {
    public static String URL = "http://chiasenhac.vn/";
    public static final String KEY = "artist";
    ArrayList<Song> listSong;
    String url;
    RecyclerView recyclerView;
    AdapterSongArtist adapter;
    DownloadTask task;

    public ArtistSongCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Artist");
        View view = inflater.inflate(R.layout.fragment_artist_song_viet_nam, container, false);
        setupView(view);

        listSong = new ArrayList<>();
        setupAsyntask();
        createDataForListSong();
        adapter = new AdapterSongArtist(listSong,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterSongArtist.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Intent intent = new Intent(getActivity(),  FloatingControlWindow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", postion);
                bundle.putString("key",KEY);
                bundle.putSerializable("list",listSong);
                intent.putExtra("bundle",bundle);
                getActivity().startService(intent);
            }
        });
        return view;
    }


    private void setupAsyntask() {
        task = new DownloadTask();
        try {
            task.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void createDataForListSong() {
        listSong = task.listNews;
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listchartsong);
    }
    static class DownloadTask extends AsyncTask<String, Void, Void> {
        ArrayList<Song> listNews;

        @Override
        protected Void doInBackground(String... strings) {
            listNews = new ArrayList<>();
            Document document = null;
            int length = 20;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
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
                            listNews.add(new Song(name,artist,category,URL+link,i));
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
