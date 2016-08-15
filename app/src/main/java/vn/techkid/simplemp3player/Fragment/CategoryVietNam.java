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
import vn.techkid.simplemp3player.Model.Category;
import vn.techkid.simplemp3player.Model.PlayBack;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryVietNam extends Fragment {
    ArrayList<Category> listCategory;
    RecyclerView recyclerView;
    AdapterCategory adapter;
    String url;
    DownloadTask task;

    ArrayList<CharSequence> titles = new ArrayList<>();
    ArrayList<CharSequence> artists = new ArrayList<>();
    ArrayList<CharSequence> urls = new ArrayList<>();


    public CategoryVietNam() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Category VietNam");
        View view = inflater.inflate(R.layout.fragment_category_viet_nam, container, false);
        setupView(view);

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

    private void createDataForListCategory() {
       listCategory = task.listNews;
        for (Category song:listCategory){
            titles.add(song.getNameSongCategory());
            artists.add(song.getNameArtistCategory());
            urls.add(song.getImgCategory());
        }
    }
    public void getURL(String url){
        this.url = url;
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listCategory);
    }
    static class DownloadTask extends AsyncTask<String, Void, Void> {
        ArrayList<Category> listNews;

        @Override
        protected Void doInBackground(String... strings) {
            listNews = new ArrayList<>();
            Document document = null;
            int length = 20;
            try {
                document = (Document) Jsoup.connect(strings[0]).get();
                Elements subjectElements = document.select("div.text2");
                if (subjectElements != null && subjectElements.size() > 0) {
                    if(subjectElements.size() < 20) {
                        length = subjectElements.size();
                    }
                    for (int i = 0; i < length; i++) {
                        Element titleSubject = subjectElements.get(i).getElementsByTag("a").first();
                        Element artistSubject = subjectElements.get(i).getElementsByTag("p").first();
                        if (titleSubject != null && artistSubject != null) {
                            String title = titleSubject.text();
                            String link = titleSubject.attr("href");
                            String artist = artistSubject.text();
                            listNews.add(new Category(i+1+"",link,title,"lossless",artist));
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
