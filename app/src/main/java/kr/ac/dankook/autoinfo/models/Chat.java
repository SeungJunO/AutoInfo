// app/src/main/java/kr/ac/dankook/autoinfo/models/Chat.java
package kr.ac.dankook.autoinfo.models;

public class Chat {
    private String id;
    private String senderId;
    private String message;
    private long timestamp;
    public Chat() {}
    public Chat(String id, String senderId, String message){
        this.id = id; this.senderId = senderId; this.message = message;
    }
    public String getId(){ return id; }
    public void setId(String id){ this.id = id; }
    public String getSenderId(){ return senderId; }
    public void setSenderId(String s){ this.senderId = s; }
    public String getMessage(){ return message; }
    public void setMessage(String m){ this.message = m; }
    public long getTimestamp(){ return timestamp; }
    public void setTimestamp(long t){ this.timestamp = t; }
}