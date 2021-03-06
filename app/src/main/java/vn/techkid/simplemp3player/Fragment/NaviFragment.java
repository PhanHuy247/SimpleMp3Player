package vn.techkid.simplemp3player.Fragment;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Adapter.AdapterNavigation;
import vn.techkid.simplemp3player.Model.Navigation;
import vn.techkid.simplemp3player.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NaviFragment extends Fragment {

    private ListView mDrawerList;
    ImageView imgHeader;

    // slide menu items
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    private ArrayList<Navigation> navDrawerItems;
    private AdapterNavigation adapter;
    private DrawerLayout mDrawerLayout;
    private View containerView;
    public NaviFragment() {
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

        navDrawerItems = new ArrayList<Navigation>();
        // adding nav drawer items to array
        // Home
        navDrawerItems.add(new Navigation(navMenuTitles[0], navMenuIcons.getResourceId(0, -1)));
        // Find People
       navDrawerItems.add(new Navigation(navMenuTitles[1], navMenuIcons.getResourceId(1, -1)));
        // Photos
        navDrawerItems.add(new Navigation(navMenuTitles[2], navMenuIcons.getResourceId(2, -1)));
        // Communities, Will add a counter here
        navDrawerItems.add(new Navigation(navMenuTitles[3], navMenuIcons.getResourceId(3, -1)));
        // Pages
        navDrawerItems.add(new Navigation(navMenuTitles[4], navMenuIcons.getResourceId(4, -1)));
        // What's hot, We  will add a counter here
        navDrawerItems.add(new Navigation(navMenuTitles[5], navMenuIcons.getResourceId(5, -1)));
        navDrawerItems.add(new Navigation(navMenuTitles[6], navMenuIcons.getResourceId(6, -1)));


        // Recycle the typed array
        navMenuIcons.recycle();

        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        // setting the nav drawer list adapter
        adapter = new AdapterNavigation(getActivity(),
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
    public void setUp(View fragmentId,DrawerLayout drawerLayout){
        containerView = fragmentId;
        mDrawerLayout = drawerLayout;
    }

    private void displayView(int position) {
        switch (position){
            case 0:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new PlayBackFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 1:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new AlbumCountryFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 2:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new ArtistCountryFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 3:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new CategoryFragment())
                        .addToBackStack(null)
                        .commit();
                break;
            case 4:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new ChartSongFragment())
                .addToBackStack(null)
                .commit();
                break;
            case 6:
                getFragmentManager().beginTransaction().replace(R.id.frame_container, new AboutFragment())
                        .addToBackStack(null)
                        .commit();
                break;
        }
    }
}
