package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import vn.techkid.simplemp3player.Adapter.AdapterSong;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.Manager.SongManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSong extends Fragment {
    final String secStore = System.getenv("SECONDARY_STORAGE");
    final File secExtDir = new File(secStore);
    final File priExtDir = Environment.getExternalStorageDirectory();
    ListView songsView;
    ArrayList<Song> songList = new ArrayList<>();
    public FragmentSong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        songsView = (ListView)view.findViewById(R.id.songList);
//        SongManager songManager = new SongManager();
//        songList = songManager.getPlayList(secExtDir);
//        songList.addAll(songManager.getPlayList(priExtDir));
//        Collections.sort(songList, new Comparator<Song>() {
//
//            @Override
//            public int compare(Song lhs, Song rhs) {
//                return lhs.getTitle().compareTo(rhs.getTitle());
//            }
//        });
//        AdapterSong adapterSong = new AdapterSong(songList);
//        songsView.setAdapter(adapterSong);

    }

}
