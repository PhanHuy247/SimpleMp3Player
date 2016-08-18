package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int position;

    public OfflineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_offline, container, false);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        chooseTabLayout(tabLayout);

    }

    public void setPosition(int position) {
        this.position = position;
    }

    private void chooseTabLayout(TabLayout tabLayout) {
        TabLayout.Tab tab = tabLayout.getTabAt(position);
        setUpChangeViewPage(viewPager);
        tab.select();
    }

    private void setUpChangeViewPage(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                if(position == 0)
                    changeColorTabLayout(R.color.colorPlayList);
                if(position == 1){
                    changeColorTabLayout(R.color.colorSong);
                }
                if(position == 2)
                    changeColorTabLayout(R.color.colorAlbum);
                if(position == 3)
                    changeColorTabLayout(R.color.colorArTist);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void changeColorTabLayout(int color) {
        tabLayout.setBackgroundColor(getResources().getColor(color));
        Window window = getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(color));
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFrag(new OfflinePlayListFragment(),"PLAYLIST");
        adapter.addFrag(new OfflineSongFragment(),"SONG");
        adapter.addFrag(new OfflineAlbumFragment(),"ALBUM");
        adapter.addFrag(new OfflineArtistFragment(),"ARTIST");
        viewPager.setAdapter(adapter);

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
