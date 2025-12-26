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
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        etTitle = findViewById(R.id.et_info_title);
        etDesc = findViewById(R.id.et_info_description);
        etCategory = findViewById(R.id.et_info_category);
        etPrice = findViewById(R.id.et_info_price);

        etLink = findViewById(R.id.et_info_link);

        btnSubmit = findViewById(R.id.btn_save_info);
        tvError = findViewById(R.id.tv_newinfo_error);

        btnSubmit.setOnClickListener(v -> saveInfoPost());
    }

    private void saveInfoPost() {

        String title = etTitle.getText().toString().trim();
        String desc = etDesc.getText().toString().trim();
        String category = etCategory.getText().toString().trim();
        String priceText = etPrice.getText().toString().trim();

        String variantId = etLink.getText().toString().trim();

        tvError.setText("");
        tvError.setVisibility(TextView.GONE);

        if (TextUtils.isEmpty(title)) { showError("제목을 입력해주세요."); return; }
        if (TextUtils.isEmpty(priceText)) { showError("가격을 입력해주세요."); return; }

        long price;
        try {
            price = Long.parseLong(priceText);
        } catch (NumberFormatException e) {
            showError("가격은 숫자로 입력해주세요.");
            return;
        }

        if (TextUtils.isEmpty(variantId)) {
            showError("Shopify Variant ID를 입력해주세요.");
            return;
        }
        if (!variantId.startsWith("gid://shopify/ProductVariant/")) {
            showError("Variant ID 형식이 아닙니다. 예: gid://shopify/ProductVariant/123...");
            return;
        }

        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();
        if (user == null) { showError("로그인 후 인포를 등록할 수 있습니다."); return; }

        Post post = new Post();
        post.setId(null);
        post.setTitle(title);
        post.setDescription(desc);
        post.setCategory(category);
        post.setPrice(price);

        post.setLink(variantId);

        post.setAuthorId(user.getUid());

        btnSubmit.setEnabled(false);

        FirebasePostManager.getInstance().createPost(post, new FirebasePostManager.OnPostResultListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(NewInfoActivity.this, "인포 등록 완료!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(Exception e) {
                btnSubmit.setEnabled(true);
                showError("등록 실패: " + e.getMessage());
            }
        });
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(TextView.VISIBLE);
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
