package kr.ac.dankook.autoinfo.models;

public class Order {
    private String id;
    private String postId;
    private String buyerId;
    private long amount;
    public Order() {}
    public Order(String id, String postId){ this.id = id; this.postId = postId; }
    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }
    public String getPostId(){ return postId; }
    public void setPostId(String p){ this.postId = p; }
    public String getBuyerId(){ return buyerId; }
    public void setBuyerId(String b){ this.buyerId = b; }
    public long getAmount(){ return amount; }
    public void setAmount(long a){ this.amount = a; }
}