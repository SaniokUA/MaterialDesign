package androidhive.info.materialdesign.model;

/**
 * Created by Alex on 27.05.2015.
 */
public class ListItems {
    private int image;
    private String title;


    public ListItems(String title, int image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}
