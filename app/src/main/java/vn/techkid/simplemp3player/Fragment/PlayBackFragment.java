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
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayBackFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    public static String URLVIETNAM = "http://chiasenhac.vn/mp3/beat-playback/v-instrumental/";
    public static String URLUSUK = "http://chiasenhac.vn/mp3/beat-playback/u-instrumental/";
    public static String URLKOREA = "http://chiasenhac.vn/mp3/beat-playback/k-instrumental/";

    PlayBackCountryFragment fragment1;;
    PlayBackCountryFragment fragment2;
    PlayBackCountryFragment fragment3;
    static View view;

    public PlayBackFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("PlayBack");
        if(view == null){
            view =  inflater.inflate(R.layout.fragment_play_back, container, false);
            setupView(view);
        }
        return view;
    }
    private ViewPagerAdapter setUpViewPage() {
       setupFragment(URLVIETNAM,fragment1);
       setupFragment(URLKOREA,fragment2);
       setupFragment(URLUSUK,fragment3);
        adapter.addFrag(fragment1,"VietNam");
        adapter.addFrag(fragment2," Korea ");
        adapter.addFrag(fragment3," US-UK ");

        return adapter;
    }

    private void setupView(View view) {
        fragment1  =  new PlayBackCountryFragment();
        fragment2  =  new PlayBackCountryFragment();
        fragment3  =  new PlayBackCountryFragment();
        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerplayback);
        viewPager.setAdapter(setUpViewPage());
        tabLayout = (TabLayout) view.findViewById(R.id.tabsPlayback);
        tabLayout.setupWithViewPager(viewPager);
    }


    private void setupFragment(String url,PlayBackCountryFragment fragment){
        fragment.getURL(url);
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
