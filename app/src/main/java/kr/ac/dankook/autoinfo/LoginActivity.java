package kr.ac.dankook.autoinfo;
// === 여기부터 복붙: LoginActivity.java ===
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

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvGoSignup;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);  // 우리가 만든 xml

        // 1) 뷰 연결
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvGoSignup = findViewById(R.id.tv_go_signup);
        tvError = findViewById(R.id.tv_error);


        // 2) 로그인 버튼 클릭 시
        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPassword.getText().toString().trim();

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                return;
            }

            FirebaseAuthManager.getInstance()
                    .signInWithEmail(email, pass, this, () -> {
                        // 로그인 성공 후 실행되는 코드 (onSuccess)
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish(); // 로그인 화면은 종료
                    });
        });

        // 3) 회원가입 텍스트 클릭 시
        tvGoSignup.setOnClickListener(v -> {
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
