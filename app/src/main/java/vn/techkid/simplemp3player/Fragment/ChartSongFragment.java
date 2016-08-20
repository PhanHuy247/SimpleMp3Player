package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartSongFragment extends Fragment {
    ImageButton imgVietnam;
    ImageButton imgEurope;
    ImageButton imgKorea;

    public static String URLVIETNAM = "http://chiasenhac.vn/mp3/vietnam/";
    public static String URLUSUK = "http://chiasenhac.vn/mp3/us-uk/";
    public static String URLKOREA = "http://chiasenhac.vn/mp3/korea/";

    ChartSong chartSong;

    public ChartSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Song");
        View view =  inflater.inflate(R.layout.fragment_chart_song, container, false);
        setupView(view);
        setupImage();
        return view;
    }

    private void setupImage() {
        Picasso.with(getActivity())
                .load("https://i.ytimg.com/vi/MzCbEdtNbJ0/hqdefault.jpg")
                .into(imgKorea);
        Picasso.with(getActivity())
                .load("https://i.ytimg.com/vi/37Z_bIqIWpg/hqdefault.jpg")
                .into(imgEurope);
        Picasso.with(getActivity())
                .load("https://i.ytimg.com/vi/gMDowtmgOyo/hqdefault.jpg")
                .into(imgVietnam);
    }

    private void setupView(View view) {
        imgVietnam = (ImageButton) view.findViewById(R.id.imgVietNam);
        imgEurope = (ImageButton) view.findViewById(R.id.imgEurope);
        imgKorea = (ImageButton) view.findViewById(R.id.imgKorea);
        setOnclick(imgVietnam,URLVIETNAM);
        setOnclick(imgEurope,URLUSUK);
        setOnclick(imgKorea,URLKOREA);
    }

    private void setOnclick(ImageButton imgVietnam, final String url) {
        imgVietnam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chartSong = new ChartSong();
                chartSong.setupUrl(url);
                getFragmentManager().beginTransaction().replace(R.id.frame_container,chartSong)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }



}
