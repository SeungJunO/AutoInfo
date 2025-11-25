package kr.ac.dankook.autoinfo;
// === 여기부터 복붙: LoginActivity.java ===
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvError;
    private TextView tvGoSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // 우리가 만든 xml

        // 1) 뷰 연결
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvError = findViewById(R.id.tv_error);
        tvGoSignup = findViewById(R.id.tv_go_signup);

        // 2) 로그인 버튼 클릭 시
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // 간단한 유효성 체크 (나중에 Firebase Auth로 바꿀 예정)
            if (TextUtils.isEmpty(email)) {
                showError("이메일을 입력해주세요.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                showError("비밀번호를 입력해주세요.");
                return;
            }

            // TODO: 여기 나중에 Firebase Auth 로그인 코드 들어갈 자리
            // 지금은 테스트용으로 그냥 MainActivity로 넘어가 보기
            goToMain();
        });

        // 3) 회원가입 텍스트 클릭 시
        tvGoSignup.setOnClickListener(v -> {
            // 나중에 SignupActivity 만들면 여기로 이동
            Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(View.VISIBLE);
    }

    private void goToMain() {
        // 에러 숨기기
        tvError.setVisibility(View.GONE);

        // MainActivity로 이동
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        // 로그인 화면을 스택에서 제거 (뒤로가기 눌러도 다시 로그인으로 안 돌아오게)
        finish();
    }
}
// === 여기까지 복붙: LoginActivity.java ===
