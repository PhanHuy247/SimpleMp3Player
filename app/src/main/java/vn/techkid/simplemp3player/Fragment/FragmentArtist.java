package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.ListCardArtistAdapter;
import vn.techkid.simplemp3player.Model.Artist;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentArtist extends Fragment {

    ArrayList<Artist> listNews;
    RecyclerView recyclerView;
    ListCardArtistAdapter listCardAdapter;
    public FragmentArtist() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        listNews  = new ArrayList<>();
        createDataForListNews();
        setupView(view);
        getActivity().setTitle("Chart Artist");

        listCardAdapter = new ListCardArtistAdapter(listNews,getActivity());
        LinearLayoutManager linearManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearManager);
        recyclerView.setAdapter(listCardAdapter);
        for (int i = 0; i < listNews.size(); i++){
            Log.d("FAK", listNews.get(i).getCharNumber());
        }
        listCardAdapter.setOnItemClickListener(new ListCardArtistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                Log.d("POSITION", String.valueOf(postion));
            }
        });

        return view;
    }

    private void createDataForListNews() {
        listNews.add(new Artist("1","phanhuy"));
        listNews.add(new Artist("2","phanhuy"));
        listNews.add(new Artist("3","phanhuy"));
        listNews.add(new Artist("4","phanhuy"));
    }

    private void setupView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listCard);
    }


}
