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

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.Activity.SplashScreenActivity;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumCountryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AlbumCountryAdapter adapter;
    static View view;
    AlbumCountry albumvietnam,albumkorea,albumusuk;
    public AlbumCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Album");
        if(view == null){
            view = inflater.inflate(R.layout.fragment_album_country, container, false);
            settupView(view);
        }
        return view;
    }
    private AlbumCountryAdapter setUpViewPage() {
        albumvietnam = new AlbumCountry();
        albumvietnam.setListAlbum(SplashScreenActivity.taskAlbum1.listAlbum);
        albumusuk = new AlbumCountry();
        albumusuk.setListAlbum(SplashScreenActivity.taskAlbum2.listAlbum);
        albumkorea = new AlbumCountry();
        albumkorea.setListAlbum(SplashScreenActivity.taskAlbum3.listAlbum);
        adapter.addFrag(albumvietnam,"VietNam");
        adapter.addFrag(albumusuk," US-UK ");
        adapter.addFrag(albumkorea," Korea ");

        return adapter;
    }
    private void settupView(View view) {
        adapter = new AlbumCountryAdapter(getFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.viewpageralbum);
        viewPager.setAdapter(setUpViewPage());
        tabLayout = (TabLayout) view.findViewById(R.id.tabsAlbum);
        tabLayout.setupWithViewPager(viewPager);
    }
    class AlbumCountryAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public AlbumCountryAdapter(FragmentManager manager) {
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


