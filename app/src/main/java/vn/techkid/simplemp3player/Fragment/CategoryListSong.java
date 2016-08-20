package vn.techkid.simplemp3player.Fragment;

import java.util.concurrent.ExecutionException;

import vn.techkid.simplemp3player.Getter.AlbumSongListGetter;
import vn.techkid.simplemp3player.Getter.CategoryListGetter;

/**
 * Created by Laptop88 on 8/20/2016.
 */
public class CategoryListSong extends ListSongsFragment {
    public CategoryListSong(){
        intentKey = "category";
        title = "Thể loại";
        isHot = true;
    }
    @Override
    public void setupAsynTask() {
        CategoryListGetter getter = new CategoryListGetter(URL);
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

