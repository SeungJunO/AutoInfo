package kr.ac.dankook.autoinfo.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.dankook.autoinfo.R;

public class MyPageFragment extends Fragment {

    public MyPageFragment() {
        // 기본 생성자
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // 방금 만든 fragment_my_page.xml을 화면으로 사용
        return inflater.inflate(R.layout.fragment_my_page, container, false);
    }

    // 나중에 버튼 클릭, Firebase 유저 정보 표시 등은 여기서 처리할 예정
    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 예시: 나중에 이런 식으로 뷰를 잡고 값 넣을 수 있음
        // TextView tvNickname = view.findViewById(R.id.tv_profile_nickname);
        // tvNickname.setText("오승준 님");
    }
}
