package vn.techkid.simplemp3player.Fragment;


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.AdapterNavigation;
import vn.techkid.simplemp3player.Model.Navigation;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayFragment extends Fragment {
    ListView listview;
    AdapterNavigation adapter;
    ArrayList<Navigation> listDisPlay = new ArrayList<>();
    FragmentManager fragmentManager;
    OfflineFragment offlineFragment;
    public DisplayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Offline");
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
            listDisPlay.add(new Navigation("PlayList",R.drawable.ic_library_music_black_24dp,true,"0"));
            listDisPlay.add(new Navigation("Song",R.drawable.ic_audiotrack_black_24dp,true,"0"));
            listDisPlay.add(new Navigation("Album",R.drawable.ic_album_black_24dp,true,"0"));
            listDisPlay.add(new Navigation("Artist",R.drawable.ic_person_black_24dp,true,"0"));
        }
        adapter = new AdapterNavigation(getActivity(),listDisPlay);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                offlineFragment = new OfflineFragment();
                offlineFragment.setPosition(position);
                getFragmentManager().beginTransaction().replace(R.id.frame_container,offlineFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }


}
