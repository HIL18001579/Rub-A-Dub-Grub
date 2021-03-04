package Model;

public class DinnerMenuListItem {

    private String name;
    private String description;
    private String imageDinner;
    private String price;

    public DinnerMenuListItem() {
    }

    public DinnerMenuListItem(String name, String description, String imageDinner, String price) {
        this.name = name;
        this.description = description;
        this.imageDinner = imageDinner;
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

    public String getImageDinner() {
        return imageDinner;
    }

    public void setImageDinner(String imageDinner) {
        this.imageDinner = imageDinner;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
