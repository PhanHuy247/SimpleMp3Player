package vn.techkid.simplemp3player.Model;

/**
 * Created by HP on 8/4/2016.
 */
public class Album  {
    String imageAlbum;
    String NameAlbum;
    String NameArtist;
    String category;

    public Album(String imageAlbum, String nameAlbum, String category, String nameArtist) {
        this.imageAlbum = imageAlbum;
        NameAlbum = nameAlbum;
        this.category = category;
        NameArtist = nameArtist;
    }

    public String getImageAlbum() {
        return imageAlbum;
    }

    public void setImageAlbum(String imageAlbum) {
        this.imageAlbum = imageAlbum;
    }

    public String getNameAlbum() {
        return NameAlbum;
    }

    public void setNameAlbum(String nameAlbum) {
        NameAlbum = nameAlbum;
    }

    public String getNameArtist() {
        return NameArtist;
    }

    public void setNameArtist(String nameArtist) {
        NameArtist = nameArtist;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
