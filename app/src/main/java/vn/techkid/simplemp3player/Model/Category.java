package vn.techkid.simplemp3player.Model;

/**
 * Created by HP on 8/8/2016.
 */
public class Category {
    String imgCategory;
    String nameArtistCategory;
    String nameSongCategory;
    String nameCategoryQuality;
    String ranking;

    public Category(String ranking,String imgCategory , String nameSongCategory , String nameCategoryQuality, String nameArtistCategory) {
        this.ranking = ranking;
        this.nameSongCategory = nameSongCategory;
        this.imgCategory = imgCategory;
        this.nameArtistCategory = nameArtistCategory;
        this.nameCategoryQuality = nameCategoryQuality;
    }

    public Category(String nameCategoryQuality, String nameSongCategory, String imgCategory, String nameArtistCategory) {
        this.nameCategoryQuality = nameCategoryQuality;
        this.nameSongCategory = nameSongCategory;
        this.imgCategory = imgCategory;
        this.nameArtistCategory = nameArtistCategory;
    }

    public String getImgCategory() {
        return imgCategory;
    }

    public void setImgCategory(String imgCategory) {
        this.imgCategory = imgCategory;
    }

    public String getNameSongCategory() {
        return nameSongCategory;
    }

    public void setNameSongCategory(String nameSongCategory) {
        this.nameSongCategory = nameSongCategory;
    }

    public String getNameArtistCategory() {
        return nameArtistCategory;
    }

    public void setNameArtistCategory(String nameArtistCategory) {
        this.nameArtistCategory = nameArtistCategory;
    }

    public String getNameCategoryQuality() {
        return nameCategoryQuality;
    }

    public void setNameCategoryQuality(String nameCategoryQuality) {
        this.nameCategoryQuality = nameCategoryQuality;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
