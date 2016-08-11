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
public class CategoryInVietNam extends Fragment implements View.OnClickListener{
    public static String URLPOP_ROCK = "http://chiasenhac.vn/mp3/vietnam/v-pop/";
    public static String URLRAP_HIPHOP = "http://chiasenhac.vn/mp3/vietnam/v-rap-hiphop/";
    public static String URLDANCE_REMIX = "http://chiasenhac.vn/mp3/vietnam/v-dance-remix/";
    public static String URLTRADITIONAL = "http://chiasenhac.vn/mp3/vietnam/v-truyen-thong/";
    Button btnpop_rock,btnrap_hiphop,btndance_remix,btntraditional;
    CategoryVietNam fragment;

    public CategoryInVietNam(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category_in_viet_nam, container, false);
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
                setupFragment(URLDANCE_REMIX);
                break;
            case R.id.btncategoryvietnampoprock:
                setupFragment(URLPOP_ROCK);
                break;
            case R.id.btncategoryvietnamraphiphop:
                setupFragment(URLRAP_HIPHOP);
                break;
            case R.id.btncategoryvietnamtruyenthong:
                setupFragment(URLTRADITIONAL);
                break;
            default:break;
        }
    }
    private void setupFragment(String url){
        fragment = new CategoryVietNam();
        fragment.getURL(url);
        getFragmentManager().beginTransaction().replace(R.id.frame_container,fragment )
                .addToBackStack(null)
                .commit();
    }
}
