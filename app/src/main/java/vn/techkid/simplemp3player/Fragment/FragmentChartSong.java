package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import vn.techkid.simplemp3player.Activity.ChartSongKorea;
import vn.techkid.simplemp3player.Activity.ChartSongUSUK;
import vn.techkid.simplemp3player.Activity.ChartSongVietNam;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentChartSong extends Fragment implements View.OnClickListener{
    ImageButton imgVietnam;
    ImageButton imgEurope;
    ImageButton imgKorea;

    public FragmentChartSong() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_chart_song, container, false);
        getActivity().setTitle("Chart Song");
        setupView(view);
        setupImage();
        return view;
    }

    private void setupImage() {
        Picasso.with(getActivity())
                .load("http://www.audiosparx.com/sa/zdbpath/catpix/eastern-european-music-licensing.jpg")
                .resize(160,160)
                .into(imgEurope);
        Picasso.with(getActivity())
                .load("http://www.audiosparx.com/sa/zdbpath/catpix/vietnamese-music-licensing.jpg")
                .resize(160,160)
                .into(imgVietnam);
        Picasso.with(getActivity())
                .load("http://image.slidesharecdn.com/musicofkorea-140219035140-phpapp01/95/music-of-korea-1-638.jpg?cb=1392781944")
                .resize(160,160)
                .into(imgKorea);
    }

    private void setupView(View view) {
        imgVietnam = (ImageButton) view.findViewById(R.id.imgVietNam);
        imgEurope = (ImageButton) view.findViewById(R.id.imgEurope);
        imgKorea = (ImageButton) view.findViewById(R.id.imgKorea);
        imgVietnam.setOnClickListener(this);
        imgEurope.setOnClickListener(this);
        imgKorea.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgVietNam:
                Intent vnIntent = new Intent(getActivity(), ChartSongVietNam.class);
                startActivity(vnIntent);
                break;
            case R.id.imgEurope:
                Intent euIntent = new Intent(getActivity(), ChartSongUSUK.class);
                startActivity(euIntent);
                break;
            case R.id.imgKorea:
                Intent kIntent = new Intent(getActivity(), ChartSongKorea.class);
                startActivity(kIntent);
                break;
        }
    }
}
