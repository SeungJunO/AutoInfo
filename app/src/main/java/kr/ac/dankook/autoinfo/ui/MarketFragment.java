package kr.ac.dankook.autoinfo.ui;

// 이거 두 개가 꼭 있어야 함
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import kr.ac.dankook.autoinfo.R;

// 마켓 화면 Fragment
public class MarketFragment extends Fragment {

    public MarketFragment() {
        // 기본 생성자
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // fragment_market.xml 사용
        return inflater.inflate(R.layout.fragment_market, container, false);
    }
}
