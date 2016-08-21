package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.Activity.SplashScreenActivity;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartSongFragment extends Fragment {
    public TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    public static String URLVIETNAM = "http://chiasenhac.vn/mp3/vietnam/";
    public static String URLUSUK = "http://chiasenhac.vn/mp3/us-uk/";
    public static String URLKOREA = "http://chiasenhac.vn/mp3/korea/";

    ChartSong chartSong1;
    ChartSong chartSong2;
    ChartSong chartSong3;
    View view;
    boolean ischeck = true;
    public ChartSongFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Song");
        view =  inflater.inflate(R.layout.fragment_chart_song, container, false);
        setupView(view);
        return view;
    }
    private ViewPagerAdapter setUpViewPage() {

        adapter.addFrag(chartSong1,"VietNam");
        adapter.addFrag(chartSong2," US-UK ");
        adapter.addFrag(chartSong3," Korea ");

        return adapter;
    }
    private void setupView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpagercharsong);
        tabLayout = (TabLayout) view.findViewById(R.id.tabsCharSong);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(ischeck){
            CheckConnection checkConnection = new CheckConnection(getContext());
            chartSong1  =  new ChartSong();
            chartSong2  =  new ChartSong();
            chartSong3  =  new ChartSong();

            if(checkConnection.checkMobileInternetConn()){
                chartSong1.setupUrl(URLVIETNAM);
                chartSong2.setupUrl(URLUSUK);
                chartSong3.setupUrl(URLKOREA);
            }
            ischeck = false;
        }
        adapter = new ViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(setUpViewPage());
        tabLayout.setupWithViewPager(viewPager);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
