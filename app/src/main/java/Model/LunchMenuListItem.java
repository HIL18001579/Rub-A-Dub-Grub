package Model;

public class LunchMenuListItem {

    private String name;
    private String description;
    private String imageLunch;
    private String price;


    public LunchMenuListItem() {
    }

    public LunchMenuListItem(String name, String description, String imageLunch, String price) {
        this.name = name;
        this.description = description;
        this.imageLunch = imageLunch;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLunch() {
        return imageLunch;
    }

    public void setImageLunch(String imageLunch) {
        this.imageLunch = imageLunch;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
