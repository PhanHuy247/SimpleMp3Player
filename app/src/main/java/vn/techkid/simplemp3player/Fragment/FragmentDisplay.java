package vn.techkid.simplemp3player.Fragment;


import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Activity.MainActivity;
import vn.techkid.simplemp3player.Activity.TabLayoutActivity;
import vn.techkid.simplemp3player.Adapter.NavDrawerListAdapter;
import vn.techkid.simplemp3player.Model.NavDrawerItem;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDisplay extends Fragment {
    ListView listview;
    NavDrawerListAdapter adapter;
    ArrayList<NavDrawerItem> listDisPlay = new ArrayList<>();
    FragmentManager fragmentManager;
    public FragmentDisplay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_display, container, false);
        setUpView(view);
        return view;
    }
    public void getfragmentManager(FragmentManager fragmentManager){
        this.fragmentManager  = fragmentManager;
    }

    private void setUpView(View view) {
        listview = (ListView) view.findViewById(R.id.lvTabLayout);
        if(listDisPlay.size() == 0){
            listDisPlay.add(new NavDrawerItem("PlayList",R.drawable.ic_library_music_black_24dp,true,"0"));
            listDisPlay.add(new NavDrawerItem("Song",R.drawable.ic_audiotrack_black_24dp,true,"0"));
            listDisPlay.add(new NavDrawerItem("Album",R.drawable.ic_album_black_24dp,true,"0"));
            listDisPlay.add(new NavDrawerItem("Artist",R.drawable.ic_person_black_24dp,true,"0"));
        }
        adapter = new NavDrawerListAdapter(getActivity(),listDisPlay);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), TabLayoutActivity.class);
                intent.putExtra("Position",position);
                startActivity(intent);
                Log.d("phanhuy","123");
            }
        });
    }


}
