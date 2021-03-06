package vn.techkid.simplemp3player.Activity;





import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import vn.techkid.simplemp3player.Fragment.DisplayFragment;
import vn.techkid.simplemp3player.Fragment.NaviFragment;
import vn.techkid.simplemp3player.Fragment.SearchSong;
import vn.techkid.simplemp3player.HelperClass.MusicRetriever;
import vn.techkid.simplemp3player.HelperClass.PrepareMusicRetrieverTask;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;
import vn.techkid.simplemp3player.Service.PlayingMusicService;
import vn.techkid.simplemp3player.Singleton.countSplash;

import android.support.v7.widget.SearchView;

import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    NaviFragment fragmentNavi ;
    DrawerLayout drawer;
    View view;
    SearchSong searchSong;
    DisplayFragment fragmentDisplay = new DisplayFragment();
    FinishSignalReceiver receiver;
    public static boolean isAlive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        setUpToolbar();
        setupView();
        CloseNavigation();
        setupFinsishSignalReceiver();
    }

    private void setupFinsishSignalReceiver() {
        receiver = new FinishSignalReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        isAlive = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAlive = true;
    }

    private void CloseNavigation() {
        fragmentNavi = (NaviFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        fragmentNavi.setUp(view,drawer);
    }

    private void setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        view = findViewById(R.id.fragment_navigation_drawer);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupView() {;
        getSupportFragmentManager().beginTransaction()
        .add(R.id.frame_container, fragmentDisplay)
        .commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem myActionMenuItem = menu.findItem( R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(myActionMenuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String word) {
                //search
                searchSong = new SearchSong();
                searchSong.setQuery(word);
                searchView.clearFocus();
                isAlive = true;


                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_container,searchSong)
                        .addToBackStack(null)
                        .commit();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String arg0) {
                isAlive = false;
                return false;
            }
        });
//        searchView.setOnSearchClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                isAlive = false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_search){
            startSearch("", false, null, false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isAlive = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiver);
        super.onDestroy();

    }
    private class FinishSignalReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("finish")){
                finish();
            }
        }
    }

}
