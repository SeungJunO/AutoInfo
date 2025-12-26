package kr.ac.dankook.autoinfo.models;

public class User {
    private String id;
    private String displayName;
    private String email;
    public User() {}
    public User(String id, String displayName, String email){
        this.id = id; this.displayName = displayName; this.email = email;
    }
    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }
    public String getDisplayName(){ return displayName; }
    public void setDisplayName(String n){ this.displayName = n; }
    public String getEmail(){ return email; }
    public void setEmail(String e){ this.email = e; }
}