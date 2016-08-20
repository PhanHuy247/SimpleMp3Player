package vn.techkid.simplemp3player.Fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.Activity.SplashScreenActivity;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayBackFragment extends Fragment {
    public  TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


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

        adapter.addFrag(fragment1,"VietNam");
        adapter.addFrag(fragment2," US-UK ");
        adapter.addFrag(fragment3," Korea ");

        return adapter;
    }

    private void setupView(View view) {
        fragment1  =  new PlayBackCountryFragment();
        fragment2  =  new PlayBackCountryFragment();
        fragment3  =  new PlayBackCountryFragment();

        fragment1.setListPlayBack(SplashScreenActivity.task1.listNews);
        fragment2.setListPlayBack(SplashScreenActivity.task2.listNews);
        fragment3.setListPlayBack(SplashScreenActivity.task3.listNews);

        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerplayback);
        viewPager.setAdapter(setUpViewPage());
        tabLayout = (TabLayout) view.findViewById(R.id.tabsPlayback);
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
