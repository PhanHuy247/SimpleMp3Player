package vn.techkid.simplemp3player.Fragment;


import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import android.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.NavDrawerListAdapter;
import vn.techkid.simplemp3player.Model.NavDrawerItem;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentNavi extends Fragment {

    private ListView mDrawerList;
    ImageView imgHeader;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    private FragmentManager fragmentManager;
    public FragmentNavi() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_navi, container, false);
        setupView(view);
        return view;
    }

    private void setupView(View view) {
        imgHeader = (ImageView) view.findViewById(R.id.imageView);
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        // nav drawer icons from resources
        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);
        mDrawerList = (ListView) view.findViewById(R.id.list_slidermenu);

        navDrawerItems = new ArrayList<NavDrawerItem>();
        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getActivity(),
                navDrawerItems);
        mDrawerList.setAdapter(adapter);

        Picasso.with(getActivity())
                .load("http://antonioleiva.com/wp-content/uploads/2014/10/WallpaperAndroid50.jpg")
                .resize(480,200)
                .into(imgHeader);
    }
    private class SlideMenuClickListener implements
            ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                                long id) {
            // display view for selected nav drawer item
            displayView(position);
            mDrawerLayout.closeDrawer(containerView);
        }
    }
    public void setUp(View fragmentId,DrawerLayout drawerLayout,FragmentManager fragmentManager){
        containerView = fragmentId;
        mDrawerLayout = drawerLayout;
        this.fragmentManager = fragmentManager;
    }

    private void displayView(int position) {
        switch (position){
            case 4:
                fragmentManager.beginTransaction().replace(R.id.frame_container, new FragmentChartSong())
                .addToBackStack(null)
                .commit();
                break;
        }
    }
}
