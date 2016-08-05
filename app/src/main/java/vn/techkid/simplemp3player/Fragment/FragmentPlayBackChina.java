package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.AdapterPlayBack;
import vn.techkid.simplemp3player.Model.PlayBack;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlayBackChina extends Fragment {

    ArrayList<PlayBack> listPlayBack;
    RecyclerView recyclerView;
    AdapterPlayBack adapter;
    public FragmentPlayBackChina() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_playback_china, container, false);
        setupView(view);
        getActivity().setTitle("Play Back");
        listPlayBack = new ArrayList<>();
        createDataForListPlayBack();
        adapter = new AdapterPlayBack(listPlayBack,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new AdapterPlayBack.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d("POSITION", String.valueOf(postion));
            }
        });
        return view;
    }
    private void createDataForListPlayBack() {
        listPlayBack.add(new PlayBack("1","http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
        listPlayBack.add(new PlayBack("2","http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
        listPlayBack.add(new PlayBack("3","http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
        listPlayBack.add(new PlayBack("4","http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
        listPlayBack.add(new PlayBack("5","http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg","chac ai do se ve","lossless","Son Tung"));
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listPlayBack);
    }

}
