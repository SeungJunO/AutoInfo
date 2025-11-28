package kr.ac.dankook.autoinfo.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kr.ac.dankook.autoinfo.R;
import kr.ac.dankook.autoinfo.model.ChatRoom;

public class ChatFragment extends Fragment {

    private RecyclerView rvChatRooms;
    private TextView tvChatEmpty;
    private ChatListAdapter adapter;
    private List<ChatRoom> chatRoomList = new ArrayList<>();

    public ChatFragment() {
        // 기본 생성자
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1) 뷰 연결
        rvChatRooms = view.findViewById(R.id.rv_chat_rooms);
        tvChatEmpty = view.findViewById(R.id.tv_chat_empty);

        // 2) RecyclerView 기본 설정
        rvChatRooms.setLayoutManager(new LinearLayoutManager(getContext()));

        // 3) 더미 데이터 넣어보기 (나중에 Firestore로 교체)
        loadDummyChatRooms();

        // 4) 어댑터 생성 + 리사이클러뷰에 연결
        adapter = new ChatListAdapter(chatRoomList, room -> {
            // TODO: 나중에 여기서 채팅 상세 화면으로 이동
            // 예: ChatDetailActivity로 Intent 날리기
            // 지금은 테스트용으로 로그 정도만 남겨도 됨
        });
        rvChatRooms.setAdapter(adapter);

        updateEmptyView();
    }

    // 채팅방이 없으면 "채팅방 없음" 텍스트를 보여주고, 있으면 숨김
    private void updateEmptyView() {
        if (chatRoomList == null || chatRoomList.isEmpty()) {
            tvChatEmpty.setVisibility(View.VISIBLE);
            rvChatRooms.setVisibility(View.GONE);
        } else {
            tvChatEmpty.setVisibility(View.GONE);
            rvChatRooms.setVisibility(View.VISIBLE);
        }
    }

    // 임시 더미 데이터 (나중에 Firestore 연동으로 교체)
    private void loadDummyChatRooms() {
        chatRoomList.clear();
        chatRoomList.add(new ChatRoom("room1", "마케팅 인포 판매자", "네 구매 완료되었습니다 :)", "어제"));
        chatRoomList.add(new ChatRoom("room2", "N잡 부업 인포 구매자", "자료 잘 받으셨나요?", "오전 10:12"));
        chatRoomList.add(new ChatRoom("room3", "인스타 리셀링 문의", "혹시 환불 규정이 어떻게 되나요?", "3분 전"));
    }
}

// === 여기까지 복붙: ChatFragment.java ===
