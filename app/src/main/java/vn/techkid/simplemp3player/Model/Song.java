package vn.techkid.simplemp3player.Model;

import android.widget.ImageView;

import java.io.Serializable;

/**
 * Created by Laptop88 on 7/27/2016.
 */
public class Song implements Serializable {
    private String title;
    private String path;
    private String artist;
    private String imageSrc;
    private String album;
    private String accessLink;
    private int position;
    private String  category;


    public Song(){

    }

    public Song(int position, String title, String accessLink) {
        this.position = position;
        this.title = title;
        this.accessLink = accessLink;
    }

    public Song(String title, String artist, String linkSong, int position) {
        this.title = title;
        this.artist = artist;
        this.accessLink = linkSong;
        this.position = position;
    }

    public Song(String title, String artist, String album, String accessLink, int position) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.accessLink = accessLink;
        this.position = position;
    }

    public Song( int position, String accessLink,String title , String category,String artist) {
        this.title = title;
        this.artist = artist;
        this.accessLink = accessLink;
        this.position = position;
        this.category = category;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
