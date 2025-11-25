package kr.ac.dankook.autoinfo.ui;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.dankook.autoinfo.R;

// "내 인포" 탭에서 보여줄 화면을 담당하는 Fragment
public class MyInfoFragment extends Fragment {

    // 빈 생성자 (Fragment는 기본 생성자 필수)
    public MyInfoFragment() {
    }

    // Fragment가 실제로 화면에 어떤 레이아웃을 쓸지 정하는 부분
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // fragment_my_info.xml 레이아웃을 inflate 해서 화면으로 사용
        return inflater.inflate(R.layout.fragment_my_info, container, false);
    }
}
// === 여기까지 복붙 ===

