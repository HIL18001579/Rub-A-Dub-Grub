package Model;

public class DrinksMenuListItem {
    private String name;
    private String description;
    private String imageDrinks;
    private String price;


    public DrinksMenuListItem() {
    }

    public DrinksMenuListItem(String name, String description, String imageDrinks, String price) {
        this.name = name;
        this.description = description;
        this.imageDrinks = imageDrinks;
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

    public String getImageDrinks() {
        return imageDrinks;
    }

    public void setImageDrinks(String imageDrinks) {
        this.imageDrinks = imageDrinks;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
