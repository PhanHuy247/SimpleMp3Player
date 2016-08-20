package vn.techkid.simplemp3player.Fragment;


import android.support.v4.app.Fragment;
import android.util.Log;

import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Getter.ChartSongListGetter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChartSong extends ListSongsFragment {

    public ChartSong(){
        intentKey = "chartSong";
        title = "Bảng xếp hạng";
        isHot = true;
    }

    @Override
    public void setupAsynTask() {
        ChartSongListGetter getter = new ChartSongListGetter(URL);
        try {
            getter.execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        songs = getter.songs;
        Log.d ("dmm", songs.size()+"");
    }


}
