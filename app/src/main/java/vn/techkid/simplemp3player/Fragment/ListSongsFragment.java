package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.AdapterChartSong;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class ListSongsFragment extends Fragment {
    public String title;
    public ListView lv_songs;
    public  ArrayList<Song> songs;
    public String intentKey;
    public String URL;
    public boolean isHot;
    public ListSongsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle(title);
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chart_song2, container, false);
        initView(view);
        return view;
    }
    public void setupUrl(String url){
        this.URL = url;
    }
    private void initView(View view) {
        lv_songs = (ListView) view.findViewById(R.id.listchartvietnam);
        setupAsynTask();

        AdapterChartSong adaper = new AdapterChartSong(songs,getActivity(), isHot);
        lv_songs.setAdapter(adaper);
        lv_songs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FloatingControlWindow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("key", intentKey);
                bundle.putSerializable("list",songs);
                intent.putExtra("bundle",bundle);
                getActivity().startService(intent);

            }
        });


    }

    public void setupAsynTask(){

    }



}
