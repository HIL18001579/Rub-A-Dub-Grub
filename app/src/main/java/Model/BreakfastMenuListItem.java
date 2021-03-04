package Model;

import info.hoang8f.widget.FButton;

public class BreakfastMenuListItem {

    private String name;
    private String description;
    private String imageBreakfast;
    private String price;

    public BreakfastMenuListItem() {
    }

    public BreakfastMenuListItem(String name, String description, String imageBreakfast, String price) {
        this.name = name;
        this.description = description;
        this.imageBreakfast = imageBreakfast;
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

    public String getImageBreakfast() {
        return imageBreakfast;
    }

    public void setImageBreakfast(String imageBreakfast) {
        this.imageBreakfast = imageBreakfast;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
