package kr.ac.dankook.autoinfo.model;

// 채팅방 1개에 대한 정보를 담는 클래스
public class ChatRoom {

    private String roomId;          // 채팅방 ID (나중에 Firestore 문서 ID 등)
    private String otherUserName;   // 상대방 이름 또는 채팅방 이름
    private String lastMessage;     // 마지막 메시지 내용
    private String lastMessageTime; // 마지막 메시지 시간 (문자열로 간단히)

    public ChatRoom() {
        // Firebase에서 쓰려면 기본 생성자 필요
    }

    public ChatRoom(String roomId, String otherUserName,
                    String lastMessage, String lastMessageTime) {
        this.roomId = roomId;
        this.otherUserName = otherUserName;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getOtherUserName() {
        return otherUserName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getLastMessageTime() {
        return lastMessageTime;
    }
}
// === 여기까지 복붙: ChatRoom.java ===
