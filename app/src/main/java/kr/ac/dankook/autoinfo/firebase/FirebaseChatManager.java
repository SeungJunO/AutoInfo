// app/src/main/java/kr/ac/dankook/autoinfo/firebase/FirebaseChatManager.java
package kr.ac.dankook.autoinfo.firebase;

import java.util.List;
import kr.ac.dankook.autoinfo.models.Chat;

public class FirebaseChatManager {
    private static FirebaseChatManager instance;
    private FirebaseChatManager(){}
    public static FirebaseChatManager getInstance(){
        if(instance==null) instance = new FirebaseChatManager();
        return instance;
    }
    public void sendMessage(Chat c, Runnable onComplete){}
    public void getChatRoomMessages(String roomId, Callback<List<Chat>> callback){}
    public interface Callback<T>{ void onResult(T result); }
}