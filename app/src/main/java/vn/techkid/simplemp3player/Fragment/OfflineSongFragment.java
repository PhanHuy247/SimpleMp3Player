package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;


import vn.techkid.simplemp3player.Adapter.AdapterChartSong;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineSongFragment extends Fragment {
    private String intentKey = "offline";
    FloatingActionButton fab_shuffle;
    ListView songsView;
    ArrayList<Song> songList = new ArrayList<>();
    public OfflineSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_offline_song, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        songsView = (ListView)view.findViewById(R.id.songList);
        fab_shuffle = (FloatingActionButton) view.findViewById(R.id.fab_shuffle) ;
        songList = DisplayFragment.songs;
        fab_shuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (int) (Math.random()*songList.size());
                Intent intent = new Intent(getActivity(), FloatingControlWindow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("key", intentKey);
                bundle.putSerializable("list",songList);
                intent.putExtra("bundle",bundle);
                getActivity().startService(intent);
            }
        });

        AdapterChartSong adapterSong = new AdapterChartSong(songList, getActivity(), R.layout.item_offline);
        songsView.setAdapter(adapterSong);
        songsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), FloatingControlWindow.class);
                Bundle bundle = new Bundle();
                bundle.putInt("pos", position);
                bundle.putString("key", intentKey);
                bundle.putSerializable("list",songList);
                intent.putExtra("bundle",bundle);
                getActivity().startService(intent);

            }
        });
    }

}
