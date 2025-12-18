package kr.ac.dankook.autoinfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;   // ★ 추가
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;
import kr.ac.dankook.autoinfo.firebase.FirebaseAuthManager;

import kr.ac.dankook.autoinfo.ui.ProductsFragment;
import kr.ac.dankook.autoinfo.ui.OrdersFragment;
import kr.ac.dankook.autoinfo.ui.MyPageFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // ✅ 앱 실행 시 기본 화면: 제품 탭
        replaceFragment(new ProductsFragment());

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selected = null;
            int id = item.getItemId();

            if (id == R.id.nav_products) {
                selected = new ProductsFragment();
            } else if (id == R.id.nav_orders) {
                selected = new OrdersFragment();
            } else if (id == R.id.nav_mypage) {
                selected = new MyPageFragment();
            }

            replaceFragment(selected);
            return true;
        });

    }

    // ★★★ 로그인 유무 확인 — 추가된 부분 ★★★
    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();

        if (user == null) {
            // 로그인 안 되어 있으면 LoginActivity로 이동
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish(); // MainActivity 종료 (뒤로가기로 못 돌아오게)
        }
    }

    // Fragment 교체 함수
    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }
}
