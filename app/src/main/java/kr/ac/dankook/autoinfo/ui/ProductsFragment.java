package kr.ac.dankook.autoinfo.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import kr.ac.dankook.autoinfo.NewInfoActivity; // 지금은 상품 등록 화면을 NewInfoActivity로 임시 재활용
import kr.ac.dankook.autoinfo.R;

public class ProductsFragment extends Fragment {

    public ProductsFragment() { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_products, container, false);

        // "제품 추가" 버튼 클릭 -> (임시) NewInfoActivity로 이동
        Button btnAdd = root.findViewById(R.id.btn_add_product);
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), NewInfoActivity.class);
            startActivity(intent);
        });

        return root;
    }
}
