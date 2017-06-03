package Model;

import java.util.List;

/**
 * Created by sidd on 02/06/17.
 */

public class Comic {
    private int id;
    private String title;
    private int pageCount;
    private String description;
    private Creators creators;
    private ComicPrice[] prices;
    private List<Image> images;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public ComicPrice[] getPrices() {
        return prices;
    }

    public void setPrices(ComicPrice[] prices) {
        this.prices = prices;
    }

    public Creators getCreators() {
        return creators;
    }

    public void setCreators(Creators creators) {
        this.creators = creators;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
