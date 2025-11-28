package kr.ac.dankook.autoinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kr.ac.dankook.autoinfo.model.InfoRepository;

public class NewInfoActivity extends AppCompatActivity {

    private EditText etTitle, etCategory, etPrice, etLink, etDescription;
    private TextView tvError;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        etTitle = findViewById(R.id.et_info_title);
        etCategory = findViewById(R.id.et_info_category);
        etPrice = findViewById(R.id.et_info_price);
        etLink = findViewById(R.id.et_info_link);
        etDescription = findViewById(R.id.et_info_description);
        tvError = findViewById(R.id.tv_newinfo_error);
        btnSave = findViewById(R.id.btn_save_info);

        btnSave.setOnClickListener(v -> saveInfo());
    }

    private void saveInfo() {
        String title = etTitle.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String priceStr = etPrice.getText().toString().trim();
        String link = etLink.getText().toString().trim();
        String desc = etDescription.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            showError("인포 제목을 입력해주세요.");
            return;
        }
        if (TextUtils.isEmpty(category)) {
            showError("카테고리를 입력해주세요.");
            return;
        }
        if (TextUtils.isEmpty(priceStr)) {
            showError("가격을 입력해주세요.");
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceStr);
        } catch (NumberFormatException e) {
            showError("가격은 숫자로 입력해주세요.");
            return;
        }

        // TODO: 로그인 기능 붙인 후에는 실제 로그인된 사용자 닉네임/UID를 넣으면 됨
        String sellerName = "테스트판매자";

        // 저장소에 인포 추가
        InfoRepository.getInstance()
                .addPost(title, desc, category, price, link, sellerName);

        Toast.makeText(this, "인포가 등록되었습니다.", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish(); // 화면 닫고 이전 화면으로 복귀
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(View.VISIBLE);
    }
}
// === 여기까지 복붙 ===
