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
public class ArtistCountryFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    ArtistFragment artistFragment1;
    ArtistFragment artistFragment2;
    ArtistFragment artistFragment3;
    static View view;
    public ArtistCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Artist");
        if(view == null){
            view = inflater.inflate(R.layout.fragment_artist_country, container, false);
            setupView(view);
        }
        return view;
    }
    private ViewPagerAdapter setUpViewPage() {
        artistFragment1.setupPosForChart(0,22);
        artistFragment2.setupPosForChart(22,32);
        artistFragment3.setupPosForChart(32,54);
        adapter.addFrag(artistFragment1,"VietNam");
        adapter.addFrag(artistFragment2," Korea ");
        adapter.addFrag(artistFragment3," US-UK ");

        return adapter;
    }
    private void setupView(View view) {
        artistFragment1 = new ArtistFragment();
        artistFragment2 = new ArtistFragment();
        artistFragment3 = new ArtistFragment();
        adapter = new ViewPagerAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerartist);
        viewPager.setAdapter(setUpViewPage());
        tabLayout = (TabLayout) view.findViewById(R.id.tabsartist);
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
