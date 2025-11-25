package kr.ac.dankook.autoinfo.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kr.ac.dankook.autoinfo.R;
import kr.ac.dankook.autoinfo.model.ChatRoom;

// 채팅방 목록을 RecyclerView에 표시해주는 어댑터
public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ChatViewHolder> {

    private List<ChatRoom> chatRooms;
    private OnChatRoomClickListener listener;

    // 채팅방 클릭 이벤트를 전달하기 위한 인터페이스
    public interface OnChatRoomClickListener {
        void onChatRoomClick(ChatRoom room);
    }

    public ChatListAdapter(List<ChatRoom> chatRooms, OnChatRoomClickListener listener) {
        this.chatRooms = chatRooms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat_room, parent, false);
        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        ChatRoom room = chatRooms.get(position);
        holder.bind(room);
    }

    @Override
    public int getItemCount() {
        return chatRooms != null ? chatRooms.size() : 0;
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;
        TextView tvLastMessage;
        TextView tvTime;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_chat_title);
            tvLastMessage = itemView.findViewById(R.id.tv_chat_last_message);
            tvTime = itemView.findViewById(R.id.tv_chat_time);

            // 한 줄 전체 클릭 시 이벤트
            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onChatRoomClick(chatRooms.get(pos));
                }
            });
        }

        public void bind(ChatRoom room) {
            tvTitle.setText(room.getOtherUserName());
            tvLastMessage.setText(room.getLastMessage());
            tvTime.setText(room.getLastMessageTime());
        }
    }
}
// === 여기까지 복붙: ChatListAdapter.java ===
