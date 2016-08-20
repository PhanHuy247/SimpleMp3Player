package vn.techkid.simplemp3player.Fragment;


import android.support.v4.app.Fragment;

import java.util.ArrayList;

import vn.techkid.simplemp3player.Model.Song;

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
