package vn.techkid.simplemp3player.Fragment;


import android.support.v4.app.Fragment;

import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Getter.ArtistsSongGetter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistSongCountryFragment extends ListSongsFragment {


    public ArtistSongCountryFragment() {
        intentKey = "artist";
        title = "Danh sách bài hát";
        isHot = false;
    }

    @Override
    public void setupAsynTask() {
        ArtistsSongGetter getter = new ArtistsSongGetter(URL);
        try {
            getter.execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        songs = getter.songs;
    }
}
