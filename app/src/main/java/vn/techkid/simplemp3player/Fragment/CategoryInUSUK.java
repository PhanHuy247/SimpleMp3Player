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
public class CategoryInUSUK extends Fragment implements View.OnClickListener{
    public static String URLPOP_ROCK = "http://chiasenhac.vn/mp3/us-uk/us-pop/";
    public static String URLRAP_HIPHOP = "http://chiasenhac.vn/mp3/us-uk/us-rap-hiphop/";
    public static String URLDANCE_REMIX = "http://chiasenhac.vn/mp3/us-uk/us-dance-remix/";
    Button btnpop_rock,btnrap_hiphop,btndance_remix;
    CategoryListSong categoryListSong;
    static View view;
    public CategoryInUSUK() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null){
            view = inflater.inflate(R.layout.fragment_category_in_usuk, container, false);
        }
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
                setupFragment(URLDANCE_REMIX);
                break;
            case R.id.btncategoryusukpoprock:
                setupFragment(URLPOP_ROCK);
                break;
            case R.id.btncategoryusukraphiphop:
                setupFragment(URLRAP_HIPHOP);
                break;
            default:break;
        }
    }
    public void setupFragment(String url){
        categoryListSong =  new CategoryListSong();
        categoryListSong.setupUrl(url);
        getFragmentManager().beginTransaction().replace(R.id.frame_container,categoryListSong)
                .addToBackStack(null)
                .commit();

    }

}
