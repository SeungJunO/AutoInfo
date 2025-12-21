package kr.ac.dankook.autoinfo.models;

public class Post {
    private String id;
    private String title;
    private String category;
    private long price;
    private String link;
    private String description;
    private String authorId;
    public Post() {}
    public Post(String id, String title){
        this.id = id; this.title = title;
    }
    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }
    public String getTitle(){ return title; }
    public void setTitle(String t){ this.title = t; }
    public String getCategory(){ return category; }
    public void setCategory(String c){ this.category = c; }
    public long getPrice(){ return price; }
    public void setPrice(long p){ this.price = p; }
    public String getLink(){ return link; }
    public void setLink(String l){ this.link = l; }
    public String getDescription(){ return description; }
    public void setDescription(String d){ this.description = d; }
    public String getAuthorId(){ return authorId; }
    public void setAuthorId(String a){ this.authorId = a; }
}