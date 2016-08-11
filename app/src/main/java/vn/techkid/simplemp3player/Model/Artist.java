package vn.techkid.simplemp3player.Model;

/**
 * Created by HP on 8/4/2016.
 */
public class Artist {
    String charNumber;
    String nameArtist;
    String link;

    public Artist(String number, String artist, String charNumber, String nameArtist, String link) {
        this.charNumber = charNumber;
        this.nameArtist = nameArtist;
        this.link = link;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCharNumber() {
        return charNumber;
    }

    public void setCharNumber(String charNumber) {
        this.charNumber = charNumber;
    }

    public String getNameArtist() {
        return nameArtist;
    }

    public void setNameArtist(String nameArtist) {
        this.nameArtist = nameArtist;
    }
}
