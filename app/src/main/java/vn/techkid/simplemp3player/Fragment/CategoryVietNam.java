package vn.techkid.simplemp3player.Fragment;


import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
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
public class CategoryVietNam extends Fragment implements View.OnClickListener{
    Button btnpop_rock,btnrap_hiphop,btndance_remix,btntraditional;

    public CategoryVietNam(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_viet_nam, container, false);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        btnpop_rock = (Button) view.findViewById(R.id.btncategoryvietnampoprock);
        btnrap_hiphop = (Button) view.findViewById(R.id.btncategoryvietnamraphiphop);
        btndance_remix = (Button) view.findViewById(R.id.btncategoryvietnamdanceremix);
        btntraditional = (Button) view.findViewById(R.id.btncategoryvietnamtruyenthong);
        btnpop_rock.setOnClickListener(this);
        btnrap_hiphop.setOnClickListener(this);
        btndance_remix.setOnClickListener(this);
        btntraditional.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btncategoryvietnamdanceremix:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryDanceRemixVietNamFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btncategoryvietnampoprock:
                Log.d("phanhuy","aaa");
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryPopRockVietNamFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.btncategoryvietnamraphiphop:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryRapHipHopVietNamFragment())
                        .addToBackStack(null)
                        .commit();

                break;
            case R.id.btncategoryvietnamtruyenthong:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryTraditionalVietNamFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            default:break;
        }
    }

}
