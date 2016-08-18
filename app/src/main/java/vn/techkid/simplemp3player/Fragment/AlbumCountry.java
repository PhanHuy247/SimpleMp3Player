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

    ArrayList<Album> listAlbum = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterAlbum adapter;

    String url;

    public AlbumCountry() {
        // Required empty public constructor
    }

    public void setListAlbum(ArrayList<Album> listAlbum) {
        this.listAlbum = listAlbum;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Album");
        View view =  inflater.inflate(R.layout.fragment_album, container, false);
        setupView(view);

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

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listAlbum);
    }
}
