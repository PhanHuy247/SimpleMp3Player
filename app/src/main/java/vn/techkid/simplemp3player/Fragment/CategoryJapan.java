package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryJapan extends Fragment {


    public CategoryJapan() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category_japan, container, false);
    }

}