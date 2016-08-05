package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.AdapterPlayBack;
import vn.techkid.simplemp3player.Adapter.AlbumAdapter;
import vn.techkid.simplemp3player.Adapter.ListCardArtistAdapter;
import vn.techkid.simplemp3player.Model.Album;
import vn.techkid.simplemp3player.Model.PlayBack;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPlayBack extends Fragment implements View.OnClickListener{
    Button btnVietNam,btnUsUk,btnChiNa,btnKorea,btnJapan;

    public FragmentPlayBack() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_play_back, container, false);
        setupView(view);
        btnUsUk.setOnClickListener(this);
        btnVietNam.setOnClickListener(this);
        btnChiNa.setOnClickListener(this);
        btnKorea.setOnClickListener(this);
        btnJapan.setOnClickListener(this);
       return view;
    }

    private void setupView(View view) {
        btnVietNam = (Button) view.findViewById(R.id.btnplaybackvietnam);
        btnChiNa = (Button) view.findViewById(R.id.btnplaybackchina);
        btnJapan = (Button) view.findViewById(R.id.btnplaybackjapan);
        btnKorea = (Button) view.findViewById(R.id.btnplaybackKorea);
        btnUsUk = (Button) view.findViewById(R.id.btnplaybackusuk);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnplaybackvietnam:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentPlayBackVietNam())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btnplaybackchina:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentPlayBackChina())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btnplaybackjapan:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentPlayBackJapan())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btnplaybackKorea:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new PlayBackKoreaFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btnplaybackusuk:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new FragmentPlayBackUSUK())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
