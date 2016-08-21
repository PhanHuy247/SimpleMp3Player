package vn.techkid.simplemp3player.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
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
    TextView txtcheck;
    String url;
    CheckConnection checkConnection;
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
        setupConnect();
        if(listAlbum != null){
            adapter = new AdapterAlbum(listAlbum,getActivity());
            StaggeredGridLayoutManager girdManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(girdManager);
            recyclerView.setAdapter(adapter);

            adapter.setOnItemClickListener(new AdapterAlbum.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int postion) {
                    Log.d("POSITION", String.valueOf(postion));
                    AlbumCountrySongFragment songFragment = new AlbumCountrySongFragment();
                    Log.d("thangtrang", listAlbum.get(postion).getLink());
                    songFragment.setupUrl(listAlbum.get(postion).getLink());
                    FragmentTransaction transaction = getParentFragment().getFragmentManager().beginTransaction();
                    transaction.addToBackStack(null);
                    transaction.replace(R.id.frame_container,songFragment).commit();



                }
            });
        }


        return view;
    }

    private void setupConnect() {
        if(listAlbum.size() == 0) txtcheck.setVisibility(View.GONE);
        if(!checkConnection.checkMobileInternetConn())
            txtcheck.setVisibility(View.VISIBLE);
        else{
            if(listAlbum.size() == 0 ) txtcheck.setVisibility(View.GONE);
        }
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
        txtcheck = (TextView) view.findViewById(R.id.txtcheckalbum);
        checkConnection = new CheckConnection(getActivity());
    }
}
