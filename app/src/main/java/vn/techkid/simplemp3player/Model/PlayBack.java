package vn.techkid.simplemp3player.Model;

/**
 * Created by HP on 8/5/2016.
 */
public class PlayBack {
    String imgPlayBack;
    String nameArtistPlayBack;
    String nameSongPlayBack;
    String nameCategoryPlayBack;
    String ranking;

    public PlayBack(String raking,String imgPlayBack, String nameSongPlayBack, String nameCategoryPlayBack,String nameArtistPlayBack) {
        this.ranking = raking;
        this.imgPlayBack = imgPlayBack;
        this.nameArtistPlayBack = nameArtistPlayBack;
        this.nameSongPlayBack = nameSongPlayBack;
        this.nameCategoryPlayBack = nameCategoryPlayBack;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getNameArtistPlayBack() {
        return nameArtistPlayBack;
    }

    public void setNameArtistPlayBack(String nameArtistPlayBack) {
        this.nameArtistPlayBack = nameArtistPlayBack;
    }

    public String getNameSongPlayBack() {
        return nameSongPlayBack;
    }

    public void setNameSongPlayBack(String nameSongPlayBack) {
        this.nameSongPlayBack = nameSongPlayBack;
    }

    public String getImgPlayBack() {
        return imgPlayBack;
    }

    public void setImgPlayBack(String imgPlayBack) {
        this.imgPlayBack = imgPlayBack;
    }

    public String getNameCategoryPlayBack() {
        return nameCategoryPlayBack;
    }

    public void setNameCategoryPlayBack(String nameCategoryPlayBack) {
        this.nameCategoryPlayBack = nameCategoryPlayBack;
    }
}
