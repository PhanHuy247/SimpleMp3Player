package vn.techkid.simplemp3player.Model;

/**
 * Created by HP on 8/4/2016.
 */
public class Artist {
    String charNumber;
    String nameArtist;

    public Artist(String charNumber, String nameArtist) {
        this.charNumber = charNumber;
        this.nameArtist = nameArtist;
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
