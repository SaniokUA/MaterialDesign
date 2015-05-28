package androidhive.info.materialdesign.model;


import com.mikepenz.google_material_typeface_library.GoogleMaterial;

public class NavDrawerItem {
    private boolean showNotify;
    private Integer title;
    private GoogleMaterial.Icon image;


    public NavDrawerItem( Integer title, GoogleMaterial.Icon image) {
        this.title = title;
        this.image = image;
    }

    public NavDrawerItem(boolean showNotify, Integer title, GoogleMaterial.Icon image ) {
        this.showNotify = showNotify;
        this.title = title;
        this.image = image;
    }

    public GoogleMaterial.Icon getImage() {
        return image;
    }

    public void setImage(GoogleMaterial.Icon image) {
        this.image = image;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public Integer getTitle() {
        return title;
    }

    public void setTitle(Integer title) {
        this.title = title;
    }
}
