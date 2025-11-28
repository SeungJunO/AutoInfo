package kr.ac.dankook.autoinfo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.ac.dankook.autoinfo.NewInfoActivity;
import kr.ac.dankook.autoinfo.R;

public class MyInfoFragment extends Fragment {

    public MyInfoFragment() {
        // 기본 생성자
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // fragment_my_info.xml 화면 inflate
        View rootView = inflater.inflate(R.layout.fragment_my_info, container, false);

        // 1) 버튼 가져오기
        Button btnAddNewInfo = rootView.findViewById(R.id.btn_add_new_info);

        // 2) 버튼 클릭 → NewInfoActivity로 이동
        btnAddNewInfo.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NewInfoActivity.class);
            startActivity(intent);
        });

        return rootView;
    }
}
