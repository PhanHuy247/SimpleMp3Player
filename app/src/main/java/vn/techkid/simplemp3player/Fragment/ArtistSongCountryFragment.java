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
import vn.techkid.simplemp3player.Adapter.AdapterCategory;
import vn.techkid.simplemp3player.Adapter.AdapterChartSong;
import vn.techkid.simplemp3player.Model.Artist;
import vn.techkid.simplemp3player.Model.Category;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistSongCountryFragment extends Fragment {
    public static String URL = "http://chiasenhac.vn/";
    ArrayList<Song> listSong;
    String url;
    RecyclerView recyclerView;
    AdapterChartSong adapter;
    DownloadTask task;

    ArrayList<CharSequence> titles = new ArrayList<>();
    ArrayList<CharSequence> artists = new ArrayList<>();
    ArrayList<CharSequence> urls = new ArrayList<>();


    public ArtistSongCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist_song_viet_nam, container, false);
        setupView(view);
        getActivity().setTitle("Category VietNam");
        listSong = new ArrayList<>();
        setupAsyntask();
        createDataForListSong();
        adapter = new AdapterChartSong(listSong,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterChartSong.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d("POSITION", String.valueOf(postion));
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putCharSequenceArrayListExtra("titles", titles);
                intent.putCharSequenceArrayListExtra("artists", artists);
                intent.putCharSequenceArrayListExtra("urls", urls);
                intent.putExtra("pos", postion);
                intent.putExtra("playlist", true);
                startActivity(intent);
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
        for (Song song:listSong){
            titles.add(song.getTitle());
            artists.add(song.getArtist());
            urls.add(song.getAccessLink());
        }
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
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                Elements subjectElements = document.select("div.tenbh");
                if (subjectElements != null && subjectElements.size() > 0) {
                    for (int i = 0; i< subjectElements.size(); i++) {
                        Element titleSubject = subjectElements.get(i).getElementsByTag("a").first();
                        Element artistSubject = subjectElements.get(i).getElementsByTag("p").last();
                        if (titleSubject != null) {
                            String link = titleSubject.attr("href");
                            String name = titleSubject.text();
                            String artist = artistSubject.text();
                            listNews.add(new Song(name,artist,"lossless",URL+link,i+1));
                            Log.e("phanhuy123", "doInBackground: " +name +"/n" +artist+ "   "+link);
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
