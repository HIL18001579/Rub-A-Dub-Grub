package Model;

public class SelectionListItem {

    private String name;
    private String imageSelection;
    private String text;


    public SelectionListItem() {
    }

    public SelectionListItem(String name, String imageSelection, String text) {
        this.name = name;
        this.imageSelection = imageSelection;
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageSelection() {
        return imageSelection;
    }

    public void setImageSelection(String imageSelection) {
        this.imageSelection = imageSelection;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
