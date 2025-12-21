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

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuthManager.getInstance().getCurrentUser();

        if (user == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }
}
