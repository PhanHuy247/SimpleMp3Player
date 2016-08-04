package vn.techkid.simplemp3player.Model;

import android.widget.ImageView;

/**
 * Created by Laptop88 on 7/27/2016.
 */
public class Song {
    private String title;
    private String path;
    private String artist;
    private String imageSrc;
    private String album;
    private String accessLink;
    private String downloadLink;
    private String detailDownloadLink;

    public Song(){

    }
    public Song(String title, String artist, String linkSong) {
        this.title = title;
        this.artist = artist;
        this.accessLink = linkSong;
    }
    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }
    public String getAccessLink() {
        return accessLink;
    }

    public void setAccessLink(String accessLink) {
        this.accessLink = accessLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getDetailDownloadLink() {
        return detailDownloadLink;
    }

    public void setDetailDownloadLink(String detailDownloadLink) {
        this.detailDownloadLink = detailDownloadLink;
    }



    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
