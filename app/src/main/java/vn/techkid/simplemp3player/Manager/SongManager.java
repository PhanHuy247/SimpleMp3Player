package vn.techkid.simplemp3player.Manager;

import android.media.MediaMetadataRetriever;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import vn.techkid.simplemp3player.Model.Song;

/**
 * Created by Laptop88 on 7/27/2016.
 */
public class SongManager {
    static int i=0;


    MediaMetadataRetriever metadataRetriever = new MediaMetadataRetriever();

    public ArrayList<Song> getPlayList(File file) {
        ArrayList<Song> songList = new ArrayList<>();
        if (!file.isDirectory()){
            if (file.getName().endsWith(".mp3")||file.getName().endsWith(".wav")){
                Song song = new Song();
                getSongInfo(song, file);
                songList.add(song);
                i++;

                Log.d("song",song.getPath()+" "+ song.getTitle()+" Album " + song.getAlbum() +" Artist "+ song.getArtist());
            }
        }
        else {
            for (File singleFile : file.listFiles()) {
                songList.addAll(getPlayList(singleFile));
            }
        }
        return songList;
    }

    private void getSongInfo(Song song, File file) {
        song.setTitle(file.getName().substring(0, (file.getName().length() - 4)));
        song.setPath(file.getPath());

            metadataRetriever.setDataSource(file.getPath());
            String artist = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
            String album = metadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
            if (artist == null) {
                song.setArtist("Unknown");
            } else {
                song.setArtist(artist);
            }
            if (album == null) {
                song.setAlbum("Unknown");
            } else {
                song.setAlbum(album);
            }
    }


}

