package vn.techkid.simplemp3player.Fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Activity.SplashScreenActivity;
import vn.techkid.simplemp3player.Adapter.AdapterPlayBack;
import vn.techkid.simplemp3player.Model.Song;
import vn.techkid.simplemp3player.R;
import vn.techkid.simplemp3player.Service.FloatingControlWindow;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayBackCountryFragment extends ListSongsFragment {


    public PlayBackCountryFragment() {
        intentKey = "playback";
        title = "Playback";
        isHot = true;
    }

    public void setListPlayBack(ArrayList<Song> listPlayBack) {
        this.songs = listPlayBack;
    }


    @Override
    public void setupAsynTask() {

    }


}
