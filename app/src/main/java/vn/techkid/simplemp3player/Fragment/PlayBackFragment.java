package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayBackFragment extends Fragment implements View.OnClickListener{

    public static String URLVIETNAM = "http://chiasenhac.vn/mp3/beat-playback/v-instrumental/";
    public static String URLUSUK = "http://chiasenhac.vn/mp3/beat-playback/u-instrumental/";
    public static String URLKOREA = "http://chiasenhac.vn/mp3/beat-playback/k-instrumental/";
    public static String URLCHINA = "http://chiasenhac.vn/mp3/beat-playback/c-instrumental/";
    public static String URLJAPAN = "http://chiasenhac.vn/mp3/beat-playback/j-instrumental/";
    Button btnVietNam,btnUsUk,btnChiNa,btnKorea,btnJapan;
    PlayBackCountryFragment fragment;

    public PlayBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_play_back, container, false);
        setupView(view);
        getActivity().setTitle("PlayBack");
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
               setupFragment(URLVIETNAM);
                break;
            case R.id.btnplaybackchina:
                setupFragment(URLCHINA);
                break;
            case R.id.btnplaybackjapan:
                setupFragment(URLJAPAN);
                break;
            case R.id.btnplaybackKorea:
                setupFragment(URLKOREA);
                break;
            case R.id.btnplaybackusuk:
                setupFragment(URLUSUK);
                break;
        }
    }
    private void setupFragment(String url){
        fragment = new PlayBackCountryFragment();
        fragment.getURL(url);
        getFragmentManager().beginTransaction().replace(R.id.frame_container,fragment)
                .addToBackStack(null)
                .commit();
    }
}
