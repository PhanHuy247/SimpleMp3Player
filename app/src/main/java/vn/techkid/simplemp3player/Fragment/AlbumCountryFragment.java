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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.Activity.SplashScreenActivity;
import vn.techkid.simplemp3player.Model.Album;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumCountryFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private AlbumCountryAdapter adapter;
     View view;

    AlbumCountry albumvietnam,albumkorea,albumusuk;
    CheckConnection checkConnection;
    public AlbumCountryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Album");
            view = inflater.inflate(R.layout.fragment_album_country, container, false);
            settupView(view);

        return view;
    }
    private AlbumCountryAdapter setUpViewPage() {


        adapter.addFrag(albumvietnam,"VietNam");
        adapter.addFrag(albumusuk," US-UK ");
        adapter.addFrag(albumkorea," Korea ");

        return adapter;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkConnection = new CheckConnection(getContext());
        albumvietnam = new AlbumCountry();
        albumusuk = new AlbumCountry();
        albumkorea = new AlbumCountry();

        if(checkConnection.checkMobileInternetConn()){
            setUpList(SplashScreenActivity.taskAlbum1.listAlbum);
            setUpList(SplashScreenActivity.taskAlbum2.listAlbum);
            setUpList(SplashScreenActivity.taskAlbum3.listAlbum);
            albumvietnam.setListAlbum(SplashScreenActivity.taskAlbum1.listAlbum);
            albumusuk.setListAlbum(SplashScreenActivity.taskAlbum2.listAlbum);
            albumkorea.setListAlbum(SplashScreenActivity.taskAlbum3.listAlbum);
        }
        adapter = new AlbumCountryAdapter(getChildFragmentManager());
        viewPager.setAdapter(setUpViewPage());
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpList(ArrayList<Album> sizeList){
        if(sizeList == null){
            SplashScreenActivity.setupAsyntaskAlbum();
        }
    }
    private void settupView(View view) {

        viewPager = (ViewPager) view.findViewById(R.id.viewpageralbum);

        tabLayout = (TabLayout) view.findViewById(R.id.tabsAlbum);


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


