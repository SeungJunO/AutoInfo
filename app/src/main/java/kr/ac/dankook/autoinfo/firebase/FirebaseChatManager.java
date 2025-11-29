package kr.ac.dankook.autoinfo.firebase;

import java.util.List;
import kr.ac.dankook.autoinfo.models.Chat;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseChatManager {

    private static FirebaseChatManager instance;
    private final FirebaseFirestore db;
    private final CollectionReference chatRoomsRef;

    private FirebaseChatManager() {
        db = FirebaseFirestore.getInstance();
        chatRoomsRef = db.collection("chatRooms");
    }

    public static FirebaseChatManager getInstance() {
        if (instance == null) {
            instance = new FirebaseChatManager();
        }
        return instance;
    }

    // 특정 채팅방의 messages 서브컬렉션 참조 얻기
    public CollectionReference getMessagesRef(String roomId) {
        return chatRoomsRef.document(roomId).collection("messages");
    }

    // 메시지 전송, 채팅방 생성/조회 등은 나중에 여기 추가
}
// === 여기까지 복붙 ===
