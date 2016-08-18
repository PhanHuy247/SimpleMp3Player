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
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryUsUk extends Fragment {
    public static String KEY = "categoryUsuk";

    ArrayList<Song> listCategory;
    RecyclerView recyclerView;
    AdapterCategory adapter;
    private String url;
    DownloadTask task;

    public CategoryUsUk() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  view = inflater.inflate(R.layout.fragment_category_us_uk, container, false);
        setupView(view);
        getActivity().setTitle("Category USUK");
        listCategory = new ArrayList<>();
        setupAsyntask();
        createDataForListCategory();
        adapter = new AdapterCategory(listCategory,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterCategory.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d("POSITION", String.valueOf(postion));
                Intent intent = new Intent(getActivity(), FloatingControlWindow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", postion);
                bundle.putString("key", KEY);
                bundle.putSerializable("list",listCategory);
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

    public void setUrl(String url) {
        this.url = url;
        Log.d("Category",url);
    }

    private void createDataForListCategory() {
        listCategory = task.listNews;
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listCategory);
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
                            listNews.add(new Song(i,link,title,category,artist));
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
