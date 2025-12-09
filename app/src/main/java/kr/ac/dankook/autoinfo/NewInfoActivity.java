// ==== NewInfoActivity.java (수정본 전체 복붙) ====
package kr.ac.dankook.autoinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import kr.ac.dankook.autoinfo.firebase.FirebaseAuthManager;
import kr.ac.dankook.autoinfo.firebase.FirebasePostManager;
import kr.ac.dankook.autoinfo.models.Post;

public class NewInfoActivity extends AppCompatActivity {

    private EditText etTitle, etDesc, etCategory, etPrice, etLink;
    private Button btnSubmit;
    private TextView tvError; // XML에 이미 있는 에러 TextView 사용

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info); // 기존 xml 그대로 유지

        // XML ID 연결
        etTitle = findViewById(R.id.et_info_title);
        etDesc = findViewById(R.id.et_info_description);
        etCategory = findViewById(R.id.et_info_category);
        etPrice = findViewById(R.id.et_info_price);
        etLink = findViewById(R.id.et_info_link);
        btnSubmit = findViewById(R.id.btn_save_info);
        tvError = findViewById(R.id.tv_newinfo_error);

        btnSubmit.setOnClickListener(v -> saveInfoPost());
    }

    /**
     * 인포 게시글 저장 로직
     */
    private void saveInfoPost() {

        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String priceText = etPrice.getText().toString().trim();
        String link = etLink.getText().toString().trim();

        // 에러 메시지 초기화
        tvError.setText("");
        tvError.setVisibility(TextView.GONE);

        // 기본 유효성 검사
        if (TextUtils.isEmpty(title)) {
            showError("제목을 입력해주세요.");
            return;
        }

        if (TextUtils.isEmpty(priceText)) {
            showError("가격을 입력해주세요.");
            return;
        }

        int price;
        try {
            price = Integer.parseInt(priceText);
        } catch (NumberFormatException e) {
            showError("가격은 숫자로 입력해주세요.");
            return;
        }

        // 로그인된 사용자 가져오기
        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();
        if (user == null) {
            showError("로그인 후 인포를 등록할 수 있습니다.");
            return;
        }

        // 판매자 이름: 이메일 or displayName 사용 (없으면 UID 일부)
        String sellerName;
        if (user.getDisplayName() != null && !user.getDisplayName().isEmpty()) {
            sellerName = user.getDisplayName();
        } else if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            sellerName = user.getEmail();
        } else {
            // 이메일도 없으면 UID 앞 6자리 정도 써주기
            String uid = user.getUid();
            sellerName = "user-" + uid.substring(0, 6);
        }

        Post post = new Post();
        post.setId(null);
        post.setTitle(title);
        post.setDescription(desc);
        post.setCategory(category);
        post.setPrice(price);
        post.setLink(link);
        post.setAuthorId(sellerName);  // 또는 sellerUid — 너 프로젝트에 맞게


        // 버튼 중복 클릭 방지
        btnSubmit.setEnabled(false);

        // Firebase에 저장
        FirebasePostManager.getInstance()
                .createPost(post, new FirebasePostManager.OnPostResultListener() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(NewInfoActivity.this,
                                "인포 등록 완료!", Toast.LENGTH_SHORT).show();
                        finish(); // 현재 화면 종료 (이전 화면으로)
                    }

                    @Override
                    public void onError(Exception e) {
                        btnSubmit.setEnabled(true);
                        showError("등록 실패: " + e.getMessage());
                    }
                });
    }

    /**
     * 공통 에러 표시 함수
     */
    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(TextView.VISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
// ===========================================
