package vn.techkid.simplemp3player.Activity;





import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import vn.techkid.simplemp3player.Fragment.DisplayFragment;
import vn.techkid.simplemp3player.Fragment.NaviFragment;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;
import vn.techkid.simplemp3player.Service.PlayingMusicService;

public class MainActivity extends AppCompatActivity {
    NaviFragment fragmentNavi ;
    DrawerLayout drawer;
    View view;
    DisplayFragment fragmentDisplay = new DisplayFragment();
    public static boolean isForceClose;
    public static boolean isAlive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        isForceClose = false;
        isAlive = true;
        setContentView(R.layout.activity_main);
        setUpToolbar();
        setupView();
        CloseNavigation();

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
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive = false;

    }
}
