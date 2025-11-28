package kr.ac.dankook.autoinfo.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import kr.ac.dankook.autoinfo.R;

/**
 * 새 인포 등록 화면 Fragment
 * 나중에 여기서 Firebase에 실제로 업로드 로직만 붙이면 됨.
 */
public class NewInfoFragment extends Fragment {

    private EditText etInfoTitle;
    private Spinner spinnerCategory;
    private EditText etPrice;
    private EditText etShortDesc;
    private Button btnSelectFile;
    private TextView tvSelectedFileName;
    private Button btnSubmitInfo;

    public NewInfoFragment() {
        // 빈 생성자 필수
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // fragment_new_info.xml을 이 Fragment의 화면으로 사용
        View view = inflater.inflate(R.layout.fragment_new_info, container, false);

        // XML에서 뷰 찾아오기
        etInfoTitle = view.findViewById(R.id.etInfoTitle);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        etPrice = view.findViewById(R.id.etPrice);
        etShortDesc = view.findViewById(R.id.etShortDesc);
        btnSelectFile = view.findViewById(R.id.btnSelectFile);
        tvSelectedFileName = view.findViewById(R.id.tvSelectedFileName);
        btnSubmitInfo = view.findViewById(R.id.btnSubmitInfo);

        // 카테고리 스피너 더미 데이터 (나중에 서버에서 가져오거나 상수로 관리)
        String[] categories = {"선택하세요", "마케팅", "쇼핑몰 운영", "코딩", "자기계발", "기타"};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                categories
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(spinnerAdapter);

        // 파일 선택 버튼 (지금은 더미 동작: Toast만)
        btnSelectFile.setOnClickListener(v -> {
            // 나중에 파일 선택 Intent 붙일 곳
            Toast.makeText(requireContext(),
                    "파일 선택 기능은 백엔드/스토리지 붙일 때 구현할 예정입니다.",
                    Toast.LENGTH_SHORT).show();
        });

        // 인포 등록 버튼 클릭
        btnSubmitInfo.setOnClickListener(v -> submitInfo());

        return view;
    }

    /**
     * 인포 등록 버튼 클릭 시 호출되는 함수
     * 지금은 단순히 입력값을 검사하고 Toast로 확인만 보여줌.
     * 나중에 Firebase / 백엔드에 실제로 저장하는 로직만 여기에 추가하면 됨.
     */
    private void submitInfo() {
        String title = etInfoTitle.getText().toString().trim();
        String category = (String) spinnerCategory.getSelectedItem();
        String priceStr = etPrice.getText().toString().trim();
        String desc = etShortDesc.getText().toString().trim();

        // 간단한 유효성 검사
        if (TextUtils.isEmpty(title)) {
            Toast.makeText(requireContext(), "제목을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (spinnerCategory.getSelectedItemPosition() == 0) {
            Toast.makeText(requireContext(), "카테고리를 선택해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(priceStr)) {
            Toast.makeText(requireContext(), "가격을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        int price = 0;
        try {
            price = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(requireContext(), "가격은 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(desc)) {
            Toast.makeText(requireContext(), "설명을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 여기까지 오면 입력값은 OK
        // 나중에 여기서 Firebase에 저장 로직만 추가하면 됨.
        String message = "인포 등록 준비 완료!\n"
                + "제목: " + title + "\n"
                + "카테고리: " + category + "\n"
                + "가격: " + price + "원";

        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show();
    }
}
