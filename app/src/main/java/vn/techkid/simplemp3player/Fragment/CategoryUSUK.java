package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryUSUK extends Fragment implements View.OnClickListener{
    Button btnpop_rock,btnrap_hiphop,btndance_remix,btntraditional;

    public CategoryUSUK() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_usuk, container, false);
        setupView(view);
        return view;
    }
    private void setupView(View view) {
        btnpop_rock = (Button) view.findViewById(R.id.btncategoryusukpoprock);
        btnrap_hiphop = (Button) view.findViewById(R.id.btncategoryusukraphiphop);
        btndance_remix = (Button) view.findViewById(R.id.btncategoryusukdanceremix);

        btnpop_rock.setOnClickListener(this);
        btnrap_hiphop.setOnClickListener(this);
        btndance_remix.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btncategoryusukdanceremix:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryDanceRemixUsUk())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btncategoryusukpoprock:
                Log.d("phanhuy","aaa");
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryPopRockUsUk())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btncategoryusukraphiphop:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryRapHipHopUsUk())
                        .addToBackStack(null)
                        .commit();

                break;
            default:break;
        }
    }
}
