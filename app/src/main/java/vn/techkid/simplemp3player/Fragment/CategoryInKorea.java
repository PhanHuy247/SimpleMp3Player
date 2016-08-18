package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryInKorea extends Fragment implements View.OnClickListener{
    public static String URLPOP_ROCK = "http://chiasenhac.vn/mp3/korea/k-pop/";
    public static String URLRAP_HIPHOP = "http://chiasenhac.vn/mp3/korea/k-rap-hiphop/";
    public static String URLDANCE_REMIX = "http://chiasenhac.vn/mp3/korea/k-dance-remix/";
    Button btnpop_rock,btnrap_hiphop,btndance_remix;
    CategoryKorea categoryKorea;
    static View view;


    public CategoryInKorea() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null){
            Log.d("categorykorea","phanhuy");
             view = inflater.inflate(R.layout.fragment_category_in_korea, container, false);
        }
        setupView(view);
        return view;
    }
    private void setupView(View view) {
        btnpop_rock = (Button) view.findViewById(R.id.btncategorykoreapoprock);
        btnrap_hiphop = (Button) view.findViewById(R.id.btncategorykorearaphiphop);
        btndance_remix = (Button) view.findViewById(R.id.btncategorykoreadanceremix);

        btnpop_rock.setOnClickListener(this);
        btnrap_hiphop.setOnClickListener(this);
        btndance_remix.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btncategorykoreadanceremix:
              setupFragment(URLDANCE_REMIX);
                break;
            case R.id.btncategorykoreapoprock:
                setupFragment(URLPOP_ROCK);
                break;
            case R.id.btncategorykorearaphiphop:
                setupFragment(URLRAP_HIPHOP);
                break;
            default:break;
        }
    }
    public void setupFragment(String url){
        categoryKorea =  new CategoryKorea();
        categoryKorea.setUrl(url);
        getFragmentManager().beginTransaction().replace(R.id.frame_container,categoryKorea)
                .addToBackStack(null)
                .commit();

    }


}
