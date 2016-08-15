package vn.techkid.simplemp3player.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Handler;

import vn.techkid.simplemp3player.Adapter.AdapterAlbum;
import vn.techkid.simplemp3player.Model.Album;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumCountry extends Fragment {

    ArrayList<Album> listAlbum;
    RecyclerView recyclerView;
    AdapterAlbum adapter;
    DownloadTask task;
    String url;

    public AlbumCountry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Album");
        View view =  inflater.inflate(R.layout.fragment_album, container, false);
        listAlbum = new ArrayList<>();

        setupView(view);
        setupAsyntask();
        createDataForListAlbum();
        if(listAlbum.size() > 0){
            adapter = new AdapterAlbum(listAlbum,getActivity());
            StaggeredGridLayoutManager girdManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(girdManager);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new AdapterAlbum.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    Log.d("POSITION", String.valueOf(postion));

                }
            });
        }


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    public void setUrl(String url) {
        this.url = url;
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

    private void createDataForListAlbum() {
        listAlbum = task.listNews;
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listAlbum);
    }
    static class DownloadTask extends AsyncTask<String, Void, Void> {
        ArrayList<Album> listNews;

        @Override
        protected Void doInBackground(String... strings) {
            listNews = new ArrayList<>();
            Document document = null;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                Elements firstElements = document.select("span.genmed");
                Elements secondElements = document.select("span.gen");

                if (firstElements != null && firstElements.size() > 0 ) {
                    Log.d("www",secondElements.size()+"");
                    for (int i = 0,j = 0; i < firstElements.size(); i++,j++) {
                        Element imageSubject = firstElements.get(i).getElementsByTag("img").first();
                        Element titleSubject = secondElements.get(i).getElementsByTag("a").first();
                        Element categorySubject = secondElements.get(i).getElementsByTag("span").first();
                        if (titleSubject != null && imageSubject != null) {
                            String title = titleSubject.text();
                            String link = titleSubject.attr("href");
                            String category = categorySubject.text();
                            String image = imageSubject.attr("src");
                            listNews.add(new Album(image,title,"lossless",link));
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
