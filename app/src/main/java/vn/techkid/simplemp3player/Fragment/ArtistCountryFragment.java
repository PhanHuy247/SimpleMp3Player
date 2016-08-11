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
public class ArtistCountryFragment extends Fragment {
    Button btnvietnam;
    Button btnkorea;
    Button btnusuk;
    ArtistFragment artistFragment;
    public ArtistCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Artist");
        View view = inflater.inflate(R.layout.fragment_artist_country, container, false);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        artistFragment = new ArtistFragment();

        btnvietnam = (Button) view.findViewById(R.id.btnartistcountryvietnam);
        btnkorea = (Button) view.findViewById(R.id.btnartistcountrykorea);
        btnusuk = (Button) view.findViewById(R.id.btnartistcountryusuk);

        buttonsetonclick(btnvietnam,0,22);
        buttonsetonclick(btnkorea,22,32);
        buttonsetonclick(btnusuk,32,54);

    }

    private void buttonsetonclick(Button button, final int posFirst, final int posLast){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                artistFragment.setupPosForChart(posFirst,posLast);
                getFragmentManager().beginTransaction().replace(R.id.frame_container, artistFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
