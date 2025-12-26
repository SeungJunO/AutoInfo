package kr.ac.dankook.autoinfo.models;

public class InfoPost {

    private String id;
    private String title;
    private String description;
    private String category;
    private int price;
    private String link;
    private String sellerName;

    public InfoPost() {

    }

    public InfoPost(String id, String title, String description,
                    String category, int price, String link, String sellerName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.link = link;
        this.sellerName = sellerName;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public String getSellerName() {
        return sellerName;
    }
}
