package vn.techkid.simplemp3player.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import vn.techkid.simplemp3player.Fragment.FragmentAlbum;
import vn.techkid.simplemp3player.Fragment.FragmentArtist;
import vn.techkid.simplemp3player.Fragment.FragmentPlayList;
import vn.techkid.simplemp3player.Fragment.FragmentSong;
import vn.techkid.simplemp3player.R;

public class TabLayoutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        setUpView();
    }

    private void setUpView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        chooseTabLayout(tabLayout);
        setUpChangeViewPage(viewPager);
    }

    private void chooseTabLayout(TabLayout tabLayout) {
        Intent intent = getIntent();
        int position = intent.getIntExtra("Position",0);
        Log.d("phanhuy", String.valueOf(position));
        TabLayout.Tab tab = tabLayout.getTabAt(position);
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
        toolbar.setBackgroundColor(getResources().getColor(color));
        tabLayout.setBackgroundColor(getResources().getColor(color));
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(this.getResources().getColor(color));
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this.getSupportFragmentManager());
        adapter.addFrag(new FragmentPlayList(),"PLAYLIST");
        adapter.addFrag(new FragmentSong(),"SONG");
        adapter.addFrag(new FragmentAlbum(),"ALBUM");
        adapter.addFrag(new FragmentArtist(),"ARTIST");
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

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  inflater.inflate(R.menu.menu_nav, menu);
        getMenuInflater().inflate(R.menu.menu_tab_layout, menu);
        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_ATOP);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id){
            case R.id.action_search:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
