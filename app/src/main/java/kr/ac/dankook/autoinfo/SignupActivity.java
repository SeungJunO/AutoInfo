package kr.ac.dankook.autoinfo;

import androidx.appcompat.app.AppCompatActivity;
import kr.ac.dankook.autoinfo.firebase.FirebaseAuthManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {

    private EditText etNickname;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPasswordConfirm;
    private Button btnSignup;
    private TextView tvError;
    private TextView tvGoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);  // 방금 만든 XML 사용

        // 1) XML과 코드 연결 (findViewById)
        etNickname = findViewById(R.id.et_nickname);
        etEmail = findViewById(R.id.et_signup_email);
        etPassword = findViewById(R.id.et_signup_password);
        etPasswordConfirm = findViewById(R.id.et_signup_password_confirm);
        btnSignup = findViewById(R.id.btn_signup);
        tvError = findViewById(R.id.tv_signup_error);
        tvGoLogin = findViewById(R.id.tv_go_login);

        // 2) 회원가입 버튼 클릭 시
        btnSignup.setOnClickListener(v -> {
            String nickname = etNickname.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String passwordConfirm = etPasswordConfirm.getText().toString().trim();

            // 기본 유효성 검사
            if (TextUtils.isEmpty(nickname)) {
                showError("닉네임을 입력해주세요.");
                return;
            }
            if (TextUtils.isEmpty(email)) {
                showError("이메일을 입력해주세요.");
                return;
            }
            if (TextUtils.isEmpty(password)) {
                showError("비밀번호를 입력해주세요.");
                return;
            }
            if (password.length() < 6) {
                showError("비밀번호는 6자 이상이어야 합니다.");
                return;
            }
            if (!password.equals(passwordConfirm)) {
                showError("비밀번호가 일치하지 않습니다.");
                return;
            }

            // 🔥 여기서 실제 Firebase 회원가입 호출
            FirebaseAuthManager.getInstance()
                    .signUpWithEmail(email, password, this, () -> {
                        // 회원가입 성공 시 실행되는 onSuccess 콜백
                        Toast.makeText(
                                SignupActivity.this,
                                "회원가입 완료! 이제 로그인해주세요.",
                                Toast.LENGTH_SHORT
                        ).show();

                        goToLogin();
                    });
        });

        // 3) "로그인" 텍스트를 눌렀을 때 → 로그인 화면으로 이동
        tvGoLogin.setOnClickListener(v -> {
            goToLogin();
        });
    }

    private void showError(String msg) {
        tvError.setText(msg);
        tvError.setVisibility(View.VISIBLE);
    }

    private void goToLogin() {
        tvError.setVisibility(View.GONE);
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // 회원가입 화면 종료
    }
}
// === 여기까지 복붙: SignupActivity.java ===
