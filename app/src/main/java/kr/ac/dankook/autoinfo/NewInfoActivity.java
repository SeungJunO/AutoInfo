package kr.ac.dankook.autoinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import kr.ac.dankook.autoinfo.firebase.FirebasePostManager;
import kr.ac.dankook.autoinfo.models.InfoPost;
import kr.ac.dankook.autoinfo.models.InfoRepository;

public class NewInfoActivity extends AppCompatActivity {

    private EditText etTitle, etDesc, etCategory, etPrice, etLink;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info); // 기존 xml 그대로 유지

        // ↓↓ 이 부분만 네 XML ID 맞추기 ↓↓
        etTitle = findViewById(R.id.et_info_title);
        etDesc = findViewById(R.id.et_info_description);
        etCategory = findViewById(R.id.et_info_category); // 없으면 하드코딩 가능
        etPrice = findViewById(R.id.et_info_price);
        etLink = findViewById(R.id.et_info_link);
        btnSubmit = findViewById(R.id.btn_save_info);
        // ↑↑ 네 레이아웃 ID에 맞는지 확인 ↑↑

        btnSubmit.setOnClickListener(v -> {

            String title = etTitle.getText().toString().trim();
            String desc = etDesc.getText().toString().trim();
            String category = etCategory.getText().toString().trim();
            String priceText = etPrice.getText().toString().trim();
            String link = etLink.getText().toString().trim();

            if (title.isEmpty() || priceText.isEmpty()) {
                Toast.makeText(this, "제목과 가격은 필수입니다.", Toast.LENGTH_SHORT).show();
                return;
            }

            int price;
            try {
                price = Integer.parseInt(priceText);
            } catch (NumberFormatException e) {
                Toast.makeText(this, "가격은 숫자로 입력해주세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            // 일단 sellerName은 임시로 고정값
            String sellerName = "테스트판매자";

            InfoPost post = new InfoPost(
                    null,  // Firestore가 ID 자동 생성함
                    title, desc, category, price, link, sellerName
            );

            FirebasePostManager.getInstance()
                    .createPost(post, new FirebasePostManager.OnPostResultListener() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(NewInfoActivity.this, "인포 등록 완료!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(NewInfoActivity.this, "등록 실패: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }
}
