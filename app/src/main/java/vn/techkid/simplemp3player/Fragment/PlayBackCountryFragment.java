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
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Activity.SplashScreenActivity;
import vn.techkid.simplemp3player.Adapter.AdapterPlayBack;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayBackCountryFragment extends Fragment {

    public  String KEY = "PlayBackCountryFragment";
    public  ArrayList<Song> listPlayBack = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterPlayBack adapter;

    public PlayBackCountryFragment() {
        // Required empty public constructor
    }

    public void setListPlayBack(ArrayList<Song> listPlayBack) {
        this.listPlayBack = listPlayBack;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Play Back");
        View view=  inflater.inflate(R.layout.fragment_play_back_country, container, false);
        setupView(view);
      //  createrDataforList();
        adapter = new AdapterPlayBack(listPlayBack,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterPlayBack.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), FloatingControlWindow.class);
                Bundle bundle  = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("key", KEY);
                bundle.putSerializable("list",listPlayBack);
                intent.putExtra("bundle",bundle);
                getActivity().startService(intent);
            }
        });
        return view;
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listPlayBack);
    }

}
